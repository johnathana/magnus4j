package magnus4j.engine.uci.state;

import magnus4j.chess.position.Position;
import magnus4j.engine.uci.AbstractUCIEngine;
import magnus4j.engine.uci.analysis.AnalysisContext;
import magnus4j.engine.uci.analysis.AnalysisType;

/**
 * Idle engine state.
 */
public class EngineIdle implements EngineState {

    private final AbstractUCIEngine _engine;

    private final AnalysisContext _analysisContext;

    public EngineIdle(AbstractUCIEngine engine, AnalysisContext analysisContext) {
        _engine = engine;
        _analysisContext = analysisContext;
    }

    @Override
    public void start() {
        _engine.startAnalyzing(_analysisContext.getAnalysisType(), _analysisContext.getParameter());
    }

    @Override
    public void stop() {
        // Engine is idle.
    }

    @Override
    public void setPosition(Position position) {
        _engine.setPosition(position.getFEN(), null);
    }

    @Override
    public void setAnalysisParameters(AnalysisType type, int parameter) {
        // Engine is idle.
    }
}
