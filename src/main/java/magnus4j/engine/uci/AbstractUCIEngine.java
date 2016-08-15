package magnus4j.engine.uci;

import magnus4j.engine.uci.analysis.AnalysisType;
import magnus4j.engine.uci.parser.DummyParser;
import magnus4j.engine.uci.parser.UCIParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

/**
 * This class provides a skeletal implementation of the {@link UCIProtocol}
 * interface.
 */
public abstract class AbstractUCIEngine implements UCIProtocol {

    /**
     * Get the engine name.
     */
    public abstract String getEngineName();

    /**
     * Get the engine executable.
     */
    public abstract String getEngineCommand();

    /**
     * The engine process.
     */
    private Process _engineProcess;

    /**
     * The uci parser.
     */
    private UCIParser _uciParser = new DummyParser();


    @Override
    public void init() {

        if (isRunning()) {
            System.err.println("Engine already initialized.");
            return;
        }

        startEngine();

        /**
         * The command to initialize the uci interface.
         */
        writeLine("uci");
    }

    @Override
    public void quit() {

        if (!isRunning())
            return;

        /**
         * UCI quit command.
         */
        writeLine("quit");

        /**
         * Wait for the engine to quit.
         */
        sleepWait(1000);

        /**
         * Kills the process.
         */
        _engineProcess.destroy();
    }

    @Override
    public void writeLine(String line) {

        validateEngineRunning();

        try {
            _engineProcess.getOutputStream().write((line + "\n").getBytes());
            _engineProcess.getOutputStream().flush();
        } catch (IOException ex) {
            throw new UCIEngineException(ex);
        }
    }

    @Override
    public void setOption(String name, Object value) {
        writeLine("setoption name " + name + " value " + value);
    }

    @Override
    public void setPosition(String fen, List<String> moves) {

        StringBuilder sb = new StringBuilder();

        sb.append("position ");

        if (fen == null)
            sb.append("startpos ");
        else
            sb.append("fen ").append(fen);

        if (moves != null) {
            sb.append("moves ");
            for (String move : moves)
                sb.append(move).append(" ");
        }

        writeLine(sb.toString());
    }

    @Override
    public void startAnalyzing(AnalysisType type, int param) {
        writeLine("go " + type.toString().toLowerCase() + " " + param);
    }

    @Override
    public void stopAnalyzing() {
        writeLine("stop");
    }

    /**
     * Set the uci line parser.
     *
     * @param uciParser
     *            the uci parser.
     */
    public void setUCIParser(final UCIParser uciParser) {
        _uciParser = uciParser;
    }

    /**
     * Start engine and corresponding threads.
     */
    private void startEngine() {

        try {
            _engineProcess = new ProcessBuilder().command(getEngineCommand()).start();

            new Thread(this::engineOutputProcess).start();
            new Thread(this::engineWatchdog).start();

        } catch (IOException e) {
            throw new UCIEngineException(e);
        }

    }

    private void engineOutputProcess() {

        try {
            InputStream is = _engineProcess.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);

            String line;

            while ((line = br.readLine()) != null) {
                _uciParser.parseUCILine(line);
            }
        }
         catch (IOException e) {
            throw new UCIEngineException(e);
        }
    }

    private void engineWatchdog() {
        try {
            int exitValue = _engineProcess.waitFor();
            if (exitValue != 0) {
                System.err.format("Abnormal engine termination: {%d}%n", exitValue);

                // Try to resurrect.
                init();
            } else {
                System.out.println("Engine terminated.");
            }

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    /**
     * Check the engine is up and running.
     */
    private boolean isRunning() {
        return _engineProcess != null && _engineProcess.isAlive();
    }

    /**
     * Validate engine up and running.
     */
    private void validateEngineRunning() {
        if (!isRunning()) {
            throw new UCIEngineException("Engine dead.");
        }
    }

    /**
     * Wait function.
     *
     * @param millis
     *            the milliseconds to sleep.
     */
    private void sleepWait(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ie) {
            System.err.format("Awakened prematurely: %s", ie.toString());
            Thread.currentThread().interrupt();
        }
    }
}
