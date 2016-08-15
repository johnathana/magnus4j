package magnus4j.engine.observer;

import magnus4j.engine.uci.ObservableEngine;

import java.util.Observer;

/**
 * Chess engine observer.
 */
public abstract class AbstractEngineObserver implements Observer {

    /**
     * The magnus4j.engine to listen.
     */
    protected final ObservableEngine _engine;

    /**
     * Chess magnus4j.engine observer constructor.
     *
     * @param observableEngine
     *            the magnus4j.engine to listen.
     */
    public AbstractEngineObserver(final ObservableEngine observableEngine) {
        _engine = observableEngine;
        _engine.addObserver(this);
    }

    /**
     * Return active magnus4j.engine.
     */
    public ObservableEngine getEngine() {
        return _engine;
    }

}
