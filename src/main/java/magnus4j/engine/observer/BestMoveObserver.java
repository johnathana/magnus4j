package magnus4j.engine.observer;

import magnus4j.chess.move.Move;
import magnus4j.chess.notation.NotationType;
import magnus4j.engine.uci.ObservableEngine;

import java.util.Observable;

/**
 * Best move observer.
 */
public class BestMoveObserver extends AbstractEngineObserver {

    /**
     * Best move observer.
     *
     * @param observableEngine
     *            the magnus4j.engine to listen.
     */
    public BestMoveObserver(ObservableEngine observableEngine) {
        super(observableEngine);
    }

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof Move) {
            Move bestMove = (Move) arg;

            System.out.println("Best move: " + NotationType.STANDARD_ALGEBRAIC.getInstance()
                    .moveToString(bestMove, _engine.getPosition()));
        }
    }
}
