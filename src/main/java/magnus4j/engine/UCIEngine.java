package magnus4j.engine;

import magnus4j.engine.uci.AbstractUCIEngine;

/**
 * UCI engine wrapper.
 */
public class UCIEngine extends AbstractUCIEngine {

    private String _engineName;

    private String _engineCommand;

    public UCIEngine(final String engineName, final String engineCommand) {
        _engineName = engineName;
        _engineCommand = engineCommand;
    }

    @Override
    public String getEngineName() {
        return _engineName;
    }

    @Override
    public String getEngineCommand() {
        return _engineCommand;
    }
}
