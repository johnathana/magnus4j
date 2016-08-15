package magnus4j.chess.logic;

import magnus4j.chess.Piece;
import magnus4j.chess.PieceType;
import magnus4j.chess.Side;
import magnus4j.chess.Square;
import magnus4j.chess.move.Move;

/**
 * Piece utility functions.
 */
public class PieceUtils {

    /**
     * Get piece from type and side.
     *
     * @param pieceType
     *            the piece type.
     * @param side
     *            the current side.
     * @return the piece.
     */
    public static Piece getPieceFromType(final PieceType pieceType, final Side side) {

        Piece piece;

        switch (pieceType) {
        case PAWN:
            piece = (side == Side.WHITE) ? Piece.WHITE_PAWN : Piece.BLACK_PAWN;
            break;
        case KNIGHT:
            piece = (side == Side.WHITE) ? Piece.WHITE_KNIGHT : Piece.BLACK_KNIGHT;
            break;
        case BISHOP:
            piece = (side == Side.WHITE) ? Piece.WHITE_BISHOP : Piece.BLACK_BISHOP;
            break;
        case ROOK:
            piece = (side == Side.WHITE) ? Piece.WHITE_ROOK : Piece.BLACK_ROOK;
            break;
        case QUEEN:
            piece = (side == Side.WHITE) ? Piece.WHITE_QUEEN : Piece.BLACK_QUEEN;
            break;
        case KING:
            piece = (side == Side.WHITE) ? Piece.WHITE_KING : Piece.BLACK_KING;
            break;
        default:
            throw new IllegalArgumentException();
        }

        return piece;
    }

    public static boolean isWhiteShortCastle(final Piece piece, final Move move) {
        return (piece == Piece.WHITE_KING && move.getFrom() == Square.E1
            && move.getTo() == Square.G1);
    }

    public static boolean isWhiteLongCastle(final Piece piece, final Move move) {
        return (piece == Piece.WHITE_KING && move.getFrom() == Square.E1
            && move.getTo() == Square.C1);
    }

    public static boolean isBlackShortCastle(final Piece piece, final Move move) {
        return (piece == Piece.BLACK_KING && move.getFrom() == Square.E8
            && move.getTo() == Square.G8);
    }

    public static boolean isBlackLongCastle(final Piece piece, final Move move) {
        return (piece == Piece.BLACK_KING && move.getFrom() == Square.E8
            && move.getTo() == Square.C8);
    }
}
