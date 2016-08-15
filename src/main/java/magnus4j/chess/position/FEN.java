package magnus4j.chess.position;

import magnus4j.chess.Piece;
import magnus4j.chess.Side;
import magnus4j.chess.Square;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

/**
 * Forsyth–Edwards Notation.
 */
public class FEN {

    /**
     * The FEN for the starting position.
     */
    public static final String START_POSITION =
        "rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1";

    /**
     * FEN to Piece map.
     */
    private static final Map<String, Piece> FEN_TO_PIECE_MAP = new HashMap<>();

    /**
     * Piece to FEN map.
     */
    private static final Map<Piece, String> PIECE_TO_FEN_MAP = new EnumMap<>(Piece.class);


    static {
        FEN_TO_PIECE_MAP.put("K", Piece.WHITE_KING);
        FEN_TO_PIECE_MAP.put("k", Piece.BLACK_KING);
        FEN_TO_PIECE_MAP.put("Q", Piece.WHITE_QUEEN);
        FEN_TO_PIECE_MAP.put("q", Piece.BLACK_QUEEN);
        FEN_TO_PIECE_MAP.put("R", Piece.WHITE_ROOK);
        FEN_TO_PIECE_MAP.put("r", Piece.BLACK_ROOK);
        FEN_TO_PIECE_MAP.put("B", Piece.WHITE_BISHOP);
        FEN_TO_PIECE_MAP.put("b", Piece.BLACK_BISHOP);
        FEN_TO_PIECE_MAP.put("N", Piece.WHITE_KNIGHT);
        FEN_TO_PIECE_MAP.put("n", Piece.BLACK_KNIGHT);
        FEN_TO_PIECE_MAP.put("P", Piece.WHITE_PAWN);
        FEN_TO_PIECE_MAP.put("p", Piece.BLACK_PAWN);

        PIECE_TO_FEN_MAP.put(Piece.WHITE_KING, "K");
        PIECE_TO_FEN_MAP.put(Piece.BLACK_KING, "k");
        PIECE_TO_FEN_MAP.put(Piece.WHITE_QUEEN, "Q");
        PIECE_TO_FEN_MAP.put(Piece.BLACK_QUEEN, "q");
        PIECE_TO_FEN_MAP.put(Piece.WHITE_ROOK, "R");
        PIECE_TO_FEN_MAP.put(Piece.BLACK_ROOK, "r");
        PIECE_TO_FEN_MAP.put(Piece.WHITE_BISHOP, "B");
        PIECE_TO_FEN_MAP.put(Piece.BLACK_BISHOP, "b");
        PIECE_TO_FEN_MAP.put(Piece.WHITE_KNIGHT, "N");
        PIECE_TO_FEN_MAP.put(Piece.BLACK_KNIGHT, "n");
        PIECE_TO_FEN_MAP.put(Piece.WHITE_PAWN, "P");
        PIECE_TO_FEN_MAP.put(Piece.BLACK_PAWN, "p");
    }


    /**
     * Set position from FEN.
     * 
     * @param position
     *            the position to init.
     * @param fen
     *            the fen string.
     */
    public static void positionFromFEN(final Position position, final String fen) {

        // validateFEN(fen);
        position._position = new EnumMap<>(Square.class);

        String[] fenArray = fen.split(" ");

        int squareNum = 0;
        for (char c : fenArray[0].toCharArray()) {

            /* Space gap */
            if (Character.isDigit(c)) {
                squareNum += Character.getNumericValue(c);
                continue;
            }

            /* Row change */
            if (c == '/') {
                continue;
            }

            Piece piece = FEN_TO_PIECE_MAP.get(String.valueOf(c));
            position._position.put(Square.valueOf(squareNum), piece);

            squareNum++;
        }

        try {
            position._activeSide = sideFromFEN(fenArray[1]);
            position._castling = fenArray[2];

            if (!"-".equals(fenArray[3])) {
                position._enPassant = Square.valueOf(fenArray[3].toUpperCase());
            }
            position._halfMoveClock = Integer.valueOf(fenArray[4]);
            position._fullMoveNumber = Integer.valueOf(fenArray[5]);

        } catch (ArrayIndexOutOfBoundsException ignored) {
        }
    }

    /**
     * FEN from position.
     * 
     * @param position
     *            the position.
     * @return the fen string.
     */
    public static String fenFromPosition(final Position position) {
        StringBuilder sb = new StringBuilder(64);

        for (int row = 0; row < 8; row++) {
            int empty = 0;
            for (int col = 0; col < 8; col++) {

                int fenSquare = (8 * row) + col;

                Square square = Square.valueOf(fenSquare);

                Piece piece = position._position.get(square);
                if (piece == null) {
                    empty++;
                } else {

                    if (empty > 0) {
                        sb.append(empty);
                        empty = 0;
                    }

                    sb.append(PIECE_TO_FEN_MAP.get(piece));
                }
            }

            if (empty > 0) {
                sb.append(empty);
            }

            if (row != 7) {
                sb.append("/");
            }
        }

        sb.append(" ");
        sb.append(sideToFEN(position._activeSide)).append(" ");
        sb.append(position._castling).append(" ");
        sb.append(position._enPassant != null ? position._enPassant.toString().toLowerCase() : "-")
            .append(" ");
        sb.append(position._halfMoveClock).append(" ");
        sb.append(position._fullMoveNumber);

        return sb.toString();
    }

    /**
     * Translate side from FEN.
     *
     * @param fenSide
     *            the fen side string.
     * @return the active side.
     */
    private static Side sideFromFEN(final String fenSide) {
        switch (fenSide) {
        case "w":
            return Side.WHITE;
        case "b":
            return Side.BLACK;
        default:
            throw new IllegalArgumentException("Invalid color fen name.");
        }
    }

    /**
     * Side to FEN.
     *
     * @param side
     *            the active side.
     * @return the fen active side.
     */
    private static String sideToFEN(final Side side) {
        return (side == Side.WHITE) ? "w" : "b";
    }

}
