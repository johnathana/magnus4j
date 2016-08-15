package magnus4j.engine.uci;

import magnus4j.engine.uci.analysis.AnalysisType;

import java.util.List;

/**
 * UCI engine protocol.
 */
public interface UCIProtocol {

    /**
     * Start the magnus4j.engine process.
     */
    void init();

    /**
     * Quit the magnus4j.engine process.
     */
    void quit();

    /**
     * Write a line to the magnus4j.engine.
     */
    void writeLine(String line);

    /**
     * Set magnus4j.engine option.
     * 
     * @param name
     *            the option's name.
     * @param value
     *            the option's value.
     */
    void setOption(String name, Object value);

    /**
     * Set position.
     * 
     * @param fen
     *            the fen string of the position.
     * @param moves
     *            the moves to play.
     */
    void setPosition(String fen, List<String> moves);

    /**
     * Start calculating on the current position.
     * 
     * @param type
     *            the type of analysis.
     * @param param
     *            the analysis parameter.
     */
    void startAnalyzing(AnalysisType type, int param);

    /**
     * Stop calculating as soon as possible.
     */
    void stopAnalyzing();
}
