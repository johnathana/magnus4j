package magnus4j.engine.observer;

import magnus4j.engine.uci.ObservableEngine;
import magnus4j.engine.uci.pv.PVInfo;
import magnus4j.engine.uci.pv.PrincipalVariationStrategy;

import java.util.Observable;

/**
 * Principal variation observer.
 */
public class PrincipalVariationObserver extends AbstractEngineObserver {

    private PrincipalVariationStrategy _principalVariationStrategy;

    /**
     * Principal variation observer.
     *
     * @param observableEngine
     *            the engine to listen.
     */
    public PrincipalVariationObserver(ObservableEngine observableEngine,
            PrincipalVariationStrategy pvStrategy) {
        super(observableEngine);
        _principalVariationStrategy = pvStrategy;
    }

    @Override
    public void update(Observable o, Object arg) {

        if (arg instanceof PVInfo) {
            PVInfo pvInfo = (PVInfo) arg;
            _principalVariationStrategy.handlePVUpdate(pvInfo);
        }
    }

}
