package magnus4j.chess.move;

import java.util.EnumSet;
import java.util.Set;

/**
 * Chess board move types.
 */
public enum MoveType {

    UP(0, 1),

    DOWN(0, -1),

    RIGHT(1, 0),

    LEFT(-1, 0),

    UP_RIGHT(1, 1),

    UP_LEFT(-1, 1),

    DOWN_RIGHT(1, -1),

    DOWN_LEFT(-1, -1),

    KNIGHT_MOVE1(2, 1),

    KNIGHT_MOVE2(2, -1),

    KNIGHT_MOVE3(1, 2),

    KNIGHT_MOVE4(-1, 2),

    KNIGHT_MOVE5(-2, 1),

    KNIGHT_MOVE6(-2, -1),

    KNIGHT_MOVE7(1, -2),

    KNIGHT_MOVE8(-1, -2),

    WHITE_PAWN_FIRST(0, 2),

    BLACK_PAWN_FIRST(0, -2);

    public static final Set<MoveType> ROOK = EnumSet.of(UP, DOWN, LEFT, RIGHT);

    public static final Set<MoveType> BISHOP = EnumSet.of(UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT);

    public static final Set<MoveType> KNIGHT = EnumSet.of(KNIGHT_MOVE1, KNIGHT_MOVE2, KNIGHT_MOVE3,
        KNIGHT_MOVE4, KNIGHT_MOVE5, KNIGHT_MOVE6, KNIGHT_MOVE7, KNIGHT_MOVE8);

    public static final Set<MoveType> ALL =
        EnumSet.of(UP, DOWN, LEFT, RIGHT, UP_LEFT, UP_RIGHT, DOWN_LEFT, DOWN_RIGHT);

    /**
     * File increment.
     */
    private int _fileInc;

    /**
     * Rank increment.
     */
    private int _rankInc;

    /**
     * Default constructor.
     * 
     * @param fileInc
     *            the file increment
     * @param rankInc
     *            the rank increment
     */
    MoveType(int fileInc, int rankInc) {
        _fileInc = fileInc;
        _rankInc = rankInc;
    }

    public int getFileInc() {
        return _fileInc;
    }

    public int getRankInc() {
        return _rankInc;
    }
}
