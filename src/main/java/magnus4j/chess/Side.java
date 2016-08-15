package magnus4j.chess;

/**
 * Side representation.
 */
public enum Side {

    WHITE,

    BLACK;

    /**
     * Return the opposite side.
     *
     * @return the opposite side.
     */
    public Side opposite() {
        return (this == BLACK) ? WHITE : BLACK;
    }
}
