package magnus4j.engine.uci;

import magnus4j.chess.PieceType;
import magnus4j.chess.Square;
import magnus4j.chess.move.Move;

import java.util.ArrayList;
import java.util.List;

/**
 * UCI helper functions.
 */
public class UCIUtils {

    /**
     * UCI move parser.
     *
     * @param uciMove
     *            the uci move.
     * @return the <code>Move</code>.
     */
    public static Move moveFromUCI(final String uciMove) {

        Square from = Square.fromStr(uciMove.substring(0, 2));
        Square to = Square.fromStr(uciMove.substring(2, 4));

        if (uciMove.length() == 5) {
            PieceType promotion = parsePromotion(uciMove.substring(4, 5));
            return new Move(from, to, promotion);
        }

        return new Move(from, to);
    }

    /**
     * PV to move list.
     *
     * @param pv
     *            the engine's pv.
     * @return the list of moves.
     */
    public static List<Move> pvToMoves(final List<String> pv) {
        List<Move> moves = new ArrayList<>();
        for (String m : pv) {
            Move move = UCIUtils.moveFromUCI(m);
            moves.add(move);
        }
        return moves;
    }

    /**
     * Algebraic score notation.
     *
     * @param score
     *            normalized uci score.
     * @return the score notation.
     */
    public static String equalityScore(final int score) {

        int absScore = Math.abs(score);

        if (absScore < 40) {
            return "=";
        }

        /* White's advantage */
        if (score > 0) {
            if (absScore > 100)
                return "+-";
            else if (absScore > 50)
                return "±";
            else
                return "+=";
        }

        /* Black's advantage */
        if (score < 0) {
            if (absScore > 100)
                return "-+";
            else if (absScore > 50)
                return "∓";
            else
                return "-=";
        }

        throw new IllegalArgumentException();
    }

    /**
     * Parse UCI promotion.
     *
     * @param promotionStr
     *            the uci promotion.
     * @return the piece type.
     */
    private static PieceType parsePromotion(final String promotionStr) {
        switch (promotionStr) {
        case "q":
            return PieceType.QUEEN;
        case "r":
            return PieceType.ROOK;
        case "b":
            return PieceType.BISHOP;
        case "n":
            return PieceType.KNIGHT;
        default:
            throw new IllegalArgumentException("Invalid UCI promotion.");
        }
    }
}
