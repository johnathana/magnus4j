package magnus4j.chess.notation;

import magnus4j.chess.Piece;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Algebraic piece maps.
 */
public class AlgebraicPiece {

    public static final Map<Piece, String> ALGEBRAIC_MAP =
        Collections.unmodifiableMap(new EnumMap<Piece, String>(Piece.class) {
            {
                put(Piece.WHITE_KING, "K");
                put(Piece.BLACK_KING, "K");
                put(Piece.WHITE_QUEEN, "Q");
                put(Piece.BLACK_QUEEN, "Q");
                put(Piece.WHITE_ROOK, "R");
                put(Piece.BLACK_ROOK, "R");
                put(Piece.WHITE_BISHOP, "B");
                put(Piece.BLACK_BISHOP, "B");
                put(Piece.WHITE_KNIGHT, "N");
                put(Piece.BLACK_KNIGHT, "N");
                put(Piece.WHITE_PAWN, "");
                put(Piece.BLACK_PAWN, "");
            }
        });

    public static final Map<Piece, String> FIGURINE_MAP =
        Collections.unmodifiableMap(new EnumMap<Piece, String>(Piece.class) {
            {
                put(Piece.WHITE_KING, "♔");
                put(Piece.BLACK_KING, "♚");
                put(Piece.WHITE_QUEEN, "♕");
                put(Piece.BLACK_QUEEN, "♛");
                put(Piece.WHITE_ROOK, "♖");
                put(Piece.BLACK_ROOK, "♜");
                put(Piece.WHITE_BISHOP, "♗");
                put(Piece.BLACK_BISHOP, "♝");
                put(Piece.WHITE_KNIGHT, "♘");
                put(Piece.BLACK_KNIGHT, "♞");
                put(Piece.WHITE_PAWN, "♙");
                put(Piece.BLACK_PAWN, "♟");
            }
        });
}
