package magnus4j.chess.notation;

import magnus4j.chess.game.Line;
import magnus4j.chess.move.Move;
import magnus4j.chess.position.Position;

/**
 * Notation interface.
 */
public interface Notation {
    /**
     * Translates algebraic notation to <code>Move</code>.
     *
     * @param strMove
     *            the move string.
     * @param position
     *            the current position.
     * @return the move.
     */
    Move stringToMove(String strMove, Position position);

    /**
     * Translate a <code>Move</code> to algebraic notation.
     *
     * @param move the current <code>Move</code>.
     * @param position the current <code>Position</code>.
     * @return the algebraic notation of the move.
     */
    String moveToString(Move move, Position position);

    /**
     * Translates a line to algebraic notation.
     *
     * @param line
     *            the line.
     * @return the algebraic notation of the moves.
     */
    String lineToString(Line line);
}
