package magnus4j.engine.uci;

import magnus4j.chess.move.Move;
import magnus4j.chess.position.Position;
import magnus4j.engine.uci.analysis.AnalysisContext;
import magnus4j.engine.uci.analysis.AnalysisType;
import magnus4j.engine.uci.parser.UCIParser;
import magnus4j.engine.uci.pv.PVInfo;
import magnus4j.engine.uci.state.EngineAnalyzing;
import magnus4j.engine.uci.state.EngineIdle;
import magnus4j.engine.uci.state.EngineState;

import java.util.HashMap;
import java.util.Map;
import java.util.Observable;

/**
 * Engine wrapper for observing, parsing and state.
 */
public class ObservableEngine extends Observable implements EngineState, UCIParser {

    /**
     * The uci engine.
     */
    private AbstractUCIEngine _uciEngine;

    /**
     * The engine state.
     */
    private EngineState _engineState;

    private EngineState _engineIdle;

    private EngineState _engineAnalyzing;

    /**
     * The engine options.
     */
    private Map<String, Object> _options = new HashMap<>();

    /**
     * The current engine position.
     */
    private Position _position = new Position();

    /**
     * The analysis parameters.
     */
    private AnalysisContext _analysisContext = new AnalysisContext();

    /**
     * Observable engine constructor.
     *
     * @param uciEngine
     *            the uci engine.
     */
    public ObservableEngine(final AbstractUCIEngine uciEngine) {
        _uciEngine = uciEngine;
        _uciEngine.setUCIParser(this);
        _uciEngine.init();

        for (Map.Entry<String, Object> option : _options.entrySet()) {
            _uciEngine.setOption(option.getKey(), option.getValue());
        }

        _engineAnalyzing = new EngineAnalyzing(_uciEngine, _analysisContext);
        _engineIdle = new EngineIdle(_uciEngine, _analysisContext);

        setEngineState(_engineIdle);
    }

    /**
     * Return the uci engine.
     */
    public AbstractUCIEngine getEngine() {
        return _uciEngine;
    }

    /**
     * Return the engine's name.
     */
    public String getEngineName() {
        return _uciEngine.getEngineName();
    }

    /**
     * Get engine position.
     */
    public Position getPosition() {
        return _position;
    }

    /**
     * Set engine options.
     *
     * @param name
     *            the option's name.
     * @param value
     *            the option's value.
     */
    public void setOption(String name, Object value) {
        _options.put(name, value);
        _uciEngine.setOption(name, value);
    }

    /**
     * Start analysis.
     */
    public void start() {
        _engineState.start();
        setEngineState(_engineAnalyzing);
    }

    /**
     * Stop analysis.
     */
    public void stop() {
        _engineState.stop();
        setEngineState(_engineIdle);
    }

    /**
     * Set engine position.
     *
     * @param position
     *            the position to analyze
     */
    public void setPosition(final Position position) {
        _position = position;
        _engineState.setPosition(position);
    }

    /**
     * Set analysis parameters.
     *
     * @param type
     *            the analysis type
     * @param parameter
     *            the analysis parameter
     */
    public void setAnalysisParameters(final AnalysisType type, int parameter) {
        _analysisContext.set(type, parameter);
        _engineState.setAnalysisParameters(type, parameter);
    }

    /**
     * Parse engine output.
     *
     * @param line
     *            engine line
     */
    public void parseUCILine(String line) {

        String[] tokens = line.trim().split("\\s+");

        switch (tokens[0]) {
        case "info":
            PVInfo pvInfo = new PVInfo(_position, tokens);
            if (pvInfo.isNotBound())
                notify(pvInfo);
            break;

        case "bestmove":
            try {
                Move move = UCIUtils.moveFromUCI(tokens[1]);
                notify(move);
            } catch (IllegalArgumentException ignored) {
                System.err.println("moveFromUCI failed: " + tokens[1]);
            }
            break;

        default:
            break;
        }
    }

    /**
     * Notify observers.
     *
     * @param arg
     *            updated object.
     */
    private void notify(final Object arg) {
        setChanged();
        notifyObservers(arg);
    }

    /**
     * Update the current engine state.
     * 
     * @param engineState
     *            the current engine state.
     */
    private void setEngineState(final EngineState engineState) {
        _engineState = engineState;
    }
}
