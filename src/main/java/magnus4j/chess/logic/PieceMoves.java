package magnus4j.chess.logic;

import magnus4j.chess.Piece;
import magnus4j.chess.Square;
import magnus4j.chess.move.MoveType;
import magnus4j.chess.position.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Generate piece moves.
 */
public class PieceMoves {

    /**
     * Get piece moves.
     *
     * @param position
     *            the current position.
     * @param square
     *            square of the piece.
     * @return a <code>List</code> with the available moves.
     */
    public static List<Square> getMoveSquares(final Position position, final Square square) {
        Piece piece = position.getPiece(square);
        return piece.getLegalMoveSquares(position, square);
    }

    public static List<Square> getControllingSquareOne(final Square square,
            final Set<MoveType> typeSet) {
        List<Square> moveList = new ArrayList<>();
        for (MoveType type : typeSet) {
            Square returnSquare = PositionUtils.findSquare(square, type);

            if (returnSquare != null)
                moveList.add(returnSquare);
        }
        return moveList;
    }

    public static List<Square> getControllingSquareAny(final Position position, final Square square,
            final Set<MoveType> moveTypes) {

        List<Square> movesList = new ArrayList<>();

        for (MoveType type : moveTypes) {

            Square moveSquare = square;
            do {
                moveSquare = PositionUtils.findSquare(moveSquare, type);

                if (moveSquare == null)
                    break;

                movesList.add(moveSquare);

            } while (position.isEmpty(moveSquare));
        }

        return movesList;
    }

    public static List<Square> getMoveSquareOne(final Position position, final Square square,
            final Set<MoveType> typeSet) {

        List<Square> moveList = new ArrayList<>();
        for (MoveType type : typeSet) {
            Square candidateSquare = PositionUtils.findSquare(square, type);

            if (PositionUtils.isEmptyOrHasOppositePiece(candidateSquare, position))
                moveList.add(candidateSquare);
        }
        return moveList;
    }

    public static List<Square> getMoveSquareAny(final Position position, final Square square,
            final Set<MoveType> moveTypes) {

        List<Square> movesList = new ArrayList<>();

        for (MoveType type : moveTypes) {

            Square moveSquare = square;
            do {
                moveSquare = PositionUtils.findSquare(moveSquare, type);

                if (moveSquare == null)
                    break;

                if (PositionUtils.isEmptyOrHasOppositePiece(moveSquare, position))
                    movesList.add(moveSquare);

            } while (position.isEmpty(moveSquare));
        }

        return movesList;
    }

}
