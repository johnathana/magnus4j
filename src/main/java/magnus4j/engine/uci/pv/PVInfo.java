package magnus4j.engine.uci.pv;

import magnus4j.chess.move.Move;
import magnus4j.chess.notation.Notation;
import magnus4j.chess.notation.NotationType;
import magnus4j.chess.position.Position;
import magnus4j.engine.uci.UCIUtils;

import java.util.Arrays;
import java.util.List;

/**
 * UCI principal variation line.
 */
public class PVInfo {

    int _depth;

    int _multiPV = 1;

    long _nodes;

    int _nps;

    int _score;

    boolean _isMate;

    boolean _isNotBound;

    List<String> _moves;

    Position _position;

    /**
     * PVInfo constructor.
     *
     * @param position
     *            the active position.
     * @param tokens
     *            pv info tokens.
     */
    public PVInfo(final Position position, String[] tokens) {
        _position = position;
        parseTokens(tokens);
    }

    private void parseTokens(String[] tokens) {
        int i = 1;
        while (i < tokens.length) {

            switch (tokens[i++]) {
            case "depth":
                _depth = Integer.parseInt(tokens[i++]);
                break;

            case "multipv":
                _multiPV = Integer.parseInt(tokens[i++]);
                break;

            case "nodes":
                _nodes = Long.parseLong(tokens[i++]);
                break;

            case "nps":
                _nps = Integer.parseInt(tokens[i++]);
                break;

            case "pv":
                String pv[] = Arrays.copyOfRange(tokens, i, tokens.length);
                _moves = Arrays.asList(pv);
                break;

            case "score":
                _isMate = tokens[i++].equals("mate");
                _score = Integer.parseInt(tokens[i++]) * (_position.isWhitesTurn() ? 1 : -1);

                if (i < tokens.length && !tokens[i].equals("upperbound")
                    && !tokens[i].equals("lowerbound")) {
                    _isNotBound = true;
                }

                break;
            }
        }
    }

    public int getMultiPV() {
        return _multiPV;
    }

    public boolean isNotBound() {
        return _isNotBound;
    }

    public List<String> getMoves() {
        return _moves;
    }

    @Override
    public String toString() {

        StringBuilder pvLine = new StringBuilder();

        if (_isMate) {
            pvLine.append("Mate in #").append(Math.abs(_score));
        } else {
            String equality = UCIUtils.equalityScore(_score);
            pvLine.append(equality).append(" (");

            String scoreInCP = String.format("%+.2f", _score / 100.0);
            pvLine.append(scoreInCP).append(") \t").append(_depth);
            pvLine.append("\t").append(_nps / 1000).append(" kN/s");
        }

        Notation notation = NotationType.FIGURINE_ALGEBRAIC.getInstance();
        List<Move> moveList = UCIUtils.pvToMoves(_moves);

        String pvMoves = notation.movesToString(moveList, _position);
        pvLine.append("\n").append(pvMoves);

        return pvLine.toString();
    }
}
