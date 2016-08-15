package magnus4j.engine.uci.state;

import magnus4j.chess.position.Position;
import magnus4j.engine.uci.AbstractUCIEngine;
import magnus4j.engine.uci.analysis.AnalysisContext;
import magnus4j.engine.uci.analysis.AnalysisType;

/**
 * Analyzing engine state.
 */
public class EngineAnalyzing implements EngineState {

    private final AbstractUCIEngine _engine;

    private final AnalysisContext _analysisContext;

    public EngineAnalyzing(AbstractUCIEngine engine, AnalysisContext analysisContext) {
        _engine = engine;
        _analysisContext = analysisContext;
    }

    @Override
    public void start() {
        // Engine already analyzing.
    }

    @Override
    public void stop() {
        _engine.stopAnalyzing();
    }

    @Override
    public void setPosition(Position position) {
        _engine.stopAnalyzing();
        _engine.setPosition(position.getFEN(), null);
        _engine.startAnalyzing(_analysisContext.getAnalysisType(), _analysisContext.getParameter());
    }

    @Override
    public void setAnalysisParameters(AnalysisType type, int parameter) {
        _engine.stopAnalyzing();
        _engine.startAnalyzing(type, parameter);
    }
}
