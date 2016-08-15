package magnus4j.chess.logic;

import magnus4j.chess.Piece;
import magnus4j.chess.PieceType;
import magnus4j.chess.Side;
import magnus4j.chess.Square;
import magnus4j.chess.move.Move;
import magnus4j.chess.move.MoveType;
import magnus4j.chess.position.MovablePosition;
import magnus4j.chess.position.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Position helper functions.
 */
public class PositionUtils {

    /**
     * Find related square.
     *
     * @param square
     *            the current square.
     * @param moveType
     *            the move type.
     * @return the related square.
     */
    public static Square findSquare(final Square square, MoveType moveType) {

        char file = (char) (square.getFile() + moveType.getFileInc());
        char rank = (char) (square.getRank() + moveType.getRankInc());

        /* File overflow check */
        if ('a' > file || file > 'h') {
            return null;
        }

        /* Rank overflow check */
        if ('1' > rank || rank > '8') {
            return null;
        }

        return Square.fromStr(String.valueOf(file) + rank);
    }

    /**
     * Find related squares.
     * 
     * @param square
     *            the current square.
     * @param moveTypes
     *            the move types.
     * @return the <code>List</code> of squares.
     */
    public static List<Square> findSquares(final Square square, final MoveType... moveTypes) {
        List<Square> moveList = new ArrayList<>();
        for (MoveType type : moveTypes) {
            Square returnSquare = findSquare(square, type);

            if (returnSquare != null)
                moveList.add(returnSquare);
        }
        return moveList;
    }

    /**
     * Find en passant capture square.
     * 
     * @param position
     *            the current position.
     * @param square
     *            the move square.
     * @return the capture square.
     */
    public static Square findEnPassantCaptureSquare(final Position position, final Square square) {
        return PositionUtils.findSquare(position.getEnPassant(),
            (position.getPiece(square) == Piece.WHITE_PAWN) ? MoveType.DOWN : MoveType.UP);
    }

    /**
     * Returns the squares under attack by pieces of the given side.
     *
     * @param position
     *            the current position.
     * @param side
     *            the attacking side.
     * @return the list of squares.
     */
    public static Set<Square> getAttackedSquares(final Position position, final Side side) {

        Set<Square> attackedSquares = new HashSet<>();

        for (Square square : position.getSquaresWithPiece()) {
            Piece piece = position.getPiece(square);

            if (piece.getSide() != side)
                continue;

            attackedSquares.addAll(piece.getPieceType().getControllingSquares(position, square));
        }

        return attackedSquares;
    }

    /**
     * Check if the enemy king is in check.
     *
     * @param position
     *            the current position.
     * @return true if enemy king in check.
     */
    public static boolean isKingInCheck(final Position position) {

        Piece king = PieceUtils.getPieceFromType(PieceType.KING, position.getActiveSide());

        Square kingSquare = getSquareWithPiece(king, position);

        Set<Square> attackedSquares =
            getAttackedSquares(position, position.getActiveSide().opposite());

        return attackedSquares.contains(kingSquare);
    }

    /**
     * Mate check.
     *
     * @param position
     *            the current position.
     * @return true if is mate.
     */
    public static boolean isMate(final Position position) {

        for (Square square : position.getSquaresWithPiece()) {
            Piece piece = position.getPiece(square);

            if (piece.getSide() != position.getActiveSide())
                continue;

            if (!PieceMoves.getMoveSquares(position, square).isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Check if king is in check after the move.
     *
     * @param position
     *            the current position.
     * @return true on check.
     */
    public static boolean isKingInCheckAfterMove(final Position position, final Move move) {

        Piece king = PieceUtils.getPieceFromType(PieceType.KING, position.getActiveSide());
        MovablePosition posAfterMove = new MovablePosition(position);
        posAfterMove.doMove(move);

        Square kingSquare = getSquareWithPiece(king, posAfterMove);

        Set<Square> attackedSquares =
            getAttackedSquares(posAfterMove, posAfterMove.getActiveSide());

        return attackedSquares.contains(kingSquare);
    }

    /**
     * Check if the attacked square has an opposite piece.
     *
     * @param attackingSquare
     *            the attacking square.
     * @param position
     *            the current position.
     * @return true if opposite piece on attacking square
     */
    public static boolean hasOppositePiece(final Square attackingSquare, final Position position) {

        if (position.isEmpty(attackingSquare)) {
            return false;
        }

        Piece attackedPiece = position.getPiece(attackingSquare);

        return (position.getActiveSide() != attackedPiece.getSide());
    }

    public static boolean isEmptyOrHasOppositePiece(final Square square, final Position position) {

        if (square == null)
            return false;

        if (position.isEmpty(square))
            return true;

        Piece piece = position.getPiece(square);
        return (position.getActiveSide() != piece.getSide());
    }

    /**
     * Find squares with specified piece.
     * 
     * @param piece
     *            the piece to find.
     * @param position
     *            the position to search.
     * @return a list of the squares.
     */
    public static List<Square> getSquaresWithPiece(final Piece piece, final Position position) {
        List<Square> squareList = new ArrayList<>();
        for (Square square : position.getSquaresWithPiece()) {
            if (position.getPiece(square) == piece)
                squareList.add(square);
        }

        return squareList;
    }

    /**
     * Find square with piece. One-to-one relationship eg king.
     * 
     * @param piece
     *            the piece to find.
     * @param position
     *            the position to search.
     * @return the square with piece.
     */
    public static Square getSquareWithPiece(final Piece piece, final Position position) {

        for (Square square : position.getSquaresWithPiece()) {
            if (position.getPiece(square) == piece) {
                return square;
            }
        }
        return null;
    }

    /**
     * Get squares with piece with target square.
     *
     * @return the list of squares.
     */
    public static List<Square> piecesWithTargetSquare(final Piece piece, final Square targetSquare,
            final Position position) {

        List<Square> squares = new ArrayList<>();

        List<Square> candidateSquares = PositionUtils.getSquaresWithPiece(piece, position);

        for (Square square : candidateSquares) {
            if (PieceMoves.getMoveSquares(position, square).contains(targetSquare))
                squares.add(square);
        }

        return squares;
    }

    /**
     * Check that squares are not currently attacked by enemy pieces.
     * 
     * @param position
     *            the position
     * @param squares
     *            the squares to check
     * @return true if are not under attack.
     */
    public static boolean areNotAttacked(final Position position, final Square... squares) {

        Set<Square> attackedSquares =
            getAttackedSquares(position, position.getActiveSide().opposite());

        for (Square s : squares) {
            if (attackedSquares.contains(s)) {
                return false;
            }
        }
        return true;
    }
}
