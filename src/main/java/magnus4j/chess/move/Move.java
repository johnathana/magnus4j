package magnus4j.chess.move;

import magnus4j.chess.PieceType;
import magnus4j.chess.Square;

/**
 * An immutable chess move.
 */
public class Move {

    protected Square _from;

    protected Square _to;

    protected PieceType _promotion;

    /**
     * Move constructor.
     * 
     * @param from
     *            from square.
     * @param to
     *            to square.
     */
    public Move(final Square from, final Square to) {
        _from = from;
        _to = to;
    }

    /**
     * Promotion move constructor.
     * 
     * @param from
     *            from square.
     * @param to
     *            to square.
     * @param promotion
     *            promotion piece type.
     */
    public Move(final Square from, final Square to, final PieceType promotion) {
        _from = from;
        _to = to;
        _promotion = promotion;
    }

    public Square getFrom() {
        return _from;
    }

    public Square getTo() {
        return _to;
    }

    public PieceType getPromotionType() {
        return _promotion;
    }

    public boolean isPromotion() {
        return (_promotion != null);
    }

    @Override
    public String toString() {
        return String.valueOf(_from) + _to;
    }
}
