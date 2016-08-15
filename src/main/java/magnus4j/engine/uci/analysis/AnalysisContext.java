package magnus4j.engine.uci.analysis;

/**
 * Engine analysis context.
 */
public class AnalysisContext {

    private AnalysisType _type;

    private int _parameter;

    public AnalysisContext() {
        _type = AnalysisType.INFINITE;
        _parameter = 0;
    }

    public void set(AnalysisType type, int parameter) {
        _type = type;
        _parameter = parameter;
    }

    public AnalysisType getAnalysisType() {
        return _type;
    }

    public int getParameter() {
        return _parameter;
    }
}
