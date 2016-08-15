package magnus4j;

import magnus4j.chess.position.Position;
import magnus4j.engine.StockfishEngine;
import magnus4j.engine.observer.BestMoveObserver;
import magnus4j.engine.observer.PrincipalVariationObserver;
import magnus4j.engine.uci.ObservableEngine;
import magnus4j.engine.uci.analysis.AnalysisType;
import magnus4j.engine.uci.pv.PVConsoleFormatter;

/**
 * magnus4j api example.
 */
public class Main {

    public static void main(String... args) {

        // you can use the internal Stockfish engine
        ObservableEngine stockfish = new ObservableEngine(new StockfishEngine());
        // or use other uci engines in your system
        //ObservableEngine komodo8 = new ObservableEngine(new UCIEngine("Komodo 8", "komodo-8-linux"));

        // define your engine observers
        new PrincipalVariationObserver(stockfish, new PVConsoleFormatter());
        new BestMoveObserver(stockfish);

        // set engine options
        stockfish.setOption("MultiPV", 2);
        // set analysis position
        stockfish.setPosition(new Position("rn3r2/1pB5/3Q2kp/6p1/6b1/8/P1q1NP1P/5K1R b - -"));
        // set analysis parameters
        stockfish.setAnalysisParameters(AnalysisType.DEPTH, 20);
        // go!
        stockfish.start();
    }
}
