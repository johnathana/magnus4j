package magnus4j.chess.logic;

import magnus4j.chess.Piece;
import magnus4j.chess.Square;
import magnus4j.chess.move.Move;
import magnus4j.chess.move.MoveType;
import magnus4j.chess.position.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * King move logic.
 */
public class KingMoves {

    /**
     * Get king moves.
     *
     * @param square
     *            square of the king.
     * @return a <code>List</code> with the available moves.
     */
    public static List<Square> getKingMoves(final Position position, final Square square) {

        List<Square> moves = PieceMoves.getMoveSquareOne(position, square, MoveType.ALL);

        if ("-".equals(position.getCastling()) || PositionUtils.isKingInCheck(position))
            return moves;

        moves.addAll(getCastlingMoves(position, square));
        return moves;
    }

    /**
     * Filter legal moves.
     *
     * @param squareList
     *            the move list to filter.
     * @param position
     *            the current position.
     * @param squareFrom
     *            the current square.
     * @return the list of valid moves.
     */
    public static List<Square> filterLegalMoves(final List<Square> squareList,
            final Position position, final Square squareFrom) {

        List<Square> legalMoves = new ArrayList<>();

        for (Square squareTo : squareList) {
            Move move = new Move(squareFrom, squareTo);
            if (PositionUtils.isKingInCheckAfterMove(position, move))
                continue;

            legalMoves.add(squareTo);
        }

        return legalMoves;
    }

    private static List<Square> getCastlingMoves(final Position position, final Square square) {

        List<Square> castlingMoves = new ArrayList<>();

        Piece piece = position.getPiece(square);
        if (piece == Piece.WHITE_KING) {
            if (position.getCastling().contains("K") && position.areAllEmpty(Square.F1, Square.G1)
                    && PositionUtils.areNotAttacked(position, Square.F1, Square.G1)) {
                castlingMoves.add(Square.G1);
            }

            if (position.getCastling().contains("Q")
                    && position.areAllEmpty(Square.B1, Square.C1, Square.D1)
                    && PositionUtils.areNotAttacked(position, Square.C1, Square.D1)) {
                castlingMoves.add(Square.C1);
            }

        } else {
            if (position.getCastling().contains("k") && position.areAllEmpty(Square.F8, Square.G8)
                    && PositionUtils.areNotAttacked(position, Square.F8, Square.G8)) {
                castlingMoves.add(Square.G8);
            }

            if (position.getCastling().contains("q")
                    && position.areAllEmpty(Square.B8, Square.C8, Square.D8)
                    && PositionUtils.areNotAttacked(position, Square.C8, Square.D8)) {
                castlingMoves.add(Square.C8);
            }
        }

        return castlingMoves;
    }
}
