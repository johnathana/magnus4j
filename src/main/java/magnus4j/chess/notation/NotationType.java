package magnus4j.chess.notation;

/**
 * Notation type enumeration.
 */
public enum NotationType {

    STANDARD_ALGEBRAIC {
        @Override
        public Notation getInstance() {
            return algebraicNotation;
        }
    },

    FIGURINE_ALGEBRAIC {
        @Override
        public Notation getInstance() {
            return figurineNotation;
        }
    };

    private static final Notation algebraicNotation =
            new AlgebraicNotation(AlgebraicPiece.ALGEBRAIC_MAP);

    private static final Notation figurineNotation =
            new AlgebraicNotation(AlgebraicPiece.FIGURINE_MAP);

    public abstract Notation getInstance();
}
