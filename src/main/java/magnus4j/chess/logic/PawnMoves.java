package magnus4j.chess.logic;

import magnus4j.chess.Side;
import magnus4j.chess.Square;
import magnus4j.chess.move.MoveType;
import magnus4j.chess.position.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Pawn move logic.
 */
public class PawnMoves {

    /**
     * Get pawn controlling squares.
     *
     * @param position
     *            the current position.
     * @param square
     *            square of the pawn.
     * @return a <code>List</code> with the squares.
     */
    public static List<Square> getPawnControllingSquares(final Position position,
            final Square square) {
        Side pawnSide = position.getPiece(square).getSide();
        if (pawnSide == Side.WHITE) {
            return PositionUtils.findSquares(square, MoveType.UP_LEFT, MoveType.UP_RIGHT);
        } else {
            return PositionUtils.findSquares(square, MoveType.DOWN_LEFT, MoveType.DOWN_RIGHT);
        }
    }

    /**
     * Get pawn move squares.
     *
     * @param position
     *            the current position.
     * @param square
     *            square of the pawn.
     * @return a <code>List</code> with the available moves.
     */
    public static List<Square> getMoveSquares(final Position position, final Square square) {

        List<Square> moves;
        Side pawnSide = position.getPiece(square).getSide();

        if (pawnSide == Side.WHITE) {
            moves = getWhitePawnMoves(position, square);
        } else {
            moves = getBlackPawnMoves(position, square);
        }

        List<Square> attackingSquares = PawnMoves.getPawnControllingSquares(position, square);
        moves.addAll(PawnMoves.filterPawnCapturingMoves(attackingSquares, position));
        return moves;
    }

    /**
     * Function to filter valid capturing pawn moves.
     * Square should be en passant square or have enemy piece.
     *
     * @param squareList
     *            the list of capturing squares.
     * @param position
     *            the current position.
     * @return the valid moves.
     */
    private static List<Square> filterPawnCapturingMoves(final List<Square> squareList,
            final Position position) {
        List<Square> capturingSquares = new ArrayList<>();
        for (Square square : squareList) {
            if (PositionUtils.hasOppositePiece(square, position)
                || position.getEnPassant() == square) {
                capturingSquares.add(square);
            }
        }
        return capturingSquares;
    }

    private static List<Square> getWhitePawnMoves(Position position, Square square) {
        List<Square> moves = new ArrayList<>();

        Square forwardSquare = PositionUtils.findSquare(square, MoveType.UP);
        if (position.isEmpty(forwardSquare))
            moves.add(forwardSquare);

        if (square.getRank() == '2') {
            Square forward2Squares = PositionUtils.findSquare(square, MoveType.WHITE_PAWN_FIRST);
            if (position.areAllEmpty(forwardSquare, forward2Squares))
                moves.add(forward2Squares);
        }

        return moves;
    }

    private static List<Square> getBlackPawnMoves(Position position, Square square) {
        List<Square> moves = new ArrayList<>();

        Square forwardSquare = PositionUtils.findSquare(square, MoveType.DOWN);
        if (position.isEmpty(forwardSquare))
            moves.add(forwardSquare);

        if (square.getRank() == '7') {
            Square forward2Squares = PositionUtils.findSquare(square, MoveType.BLACK_PAWN_FIRST);
            if (position.areAllEmpty(forwardSquare, forward2Squares))
                moves.add(forward2Squares);
        }

        return moves;
    }
}
