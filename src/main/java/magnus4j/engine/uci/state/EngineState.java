package magnus4j.engine.uci.state;

import magnus4j.chess.position.Position;
import magnus4j.engine.uci.analysis.AnalysisType;

/**
 * Engine state interface.
 */
public interface EngineState {

    void start();

    void stop();

    void setPosition(Position position);

    void setAnalysisParameters(AnalysisType type, int parameter);
}
