
# magnus4j

Magnus4j is a state of the art chess analysis library for java.

Provides:

1. complete chess rules and special moves implementation
2. [Universal Chess Interface (UCI)](https://en.wikipedia.org/wiki/Universal_Chess_Interface) protocol for engine communication
3. built-in [Stockfish 7](https://stockfishchess.org) engine
4. [FEN](https://en.wikipedia.org/wiki/Forsyth%E2%80%93Edwards_Notation) notation
4. [Algebraic](https://en.wikipedia.org/wiki/Algebraic_notation_\(chess\)) notation
5. partial PGN support

# API example

A joy to use:
```java
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
```

Console output:
```
I. -+ (-48.63) [20]:  1... Rf6 2.Qe5 Re6 3.Kg2 Rxe5 4.h4 Rxe2 5.Rf1 Rxf2+ 6.Rxf2 Rxa2 7.Bg3 Qxf2+ 8.Bxf2 Rxf2+ 9.Kxf2 gxh4 10.Ke3 h3 11.Ke4 
II. -+ (-22.06) [20]:  1... Kh7 2.Qe7+ Kg8 3.f4 Qd1+ 4.Kf2 Qxh1 5.Qe3 Qxh2+ 6.Ke1 Nc6 7.Qd3 Bxe2 8.Qe3 Rae8 9.Bb6 Rxe3 10.Bxe3 Qh1+ 11.Kxe2 gxf4 12.Bb6 Qe4+ 13.Kd2 Qb4+ 14.Kd3 Qxb6 15.Kc3 Qd4+ 16.Kc2 
Best move: Rf6
```

## License

This software is released under the MIT License.
