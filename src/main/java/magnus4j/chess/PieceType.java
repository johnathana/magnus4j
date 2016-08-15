package magnus4j.chess;

import magnus4j.chess.logic.KingMoves;
import magnus4j.chess.logic.PawnMoves;
import magnus4j.chess.logic.PieceMoves;
import magnus4j.chess.move.MoveType;
import magnus4j.chess.position.Position;

import java.util.List;

/**
 * Chess piece type.
 */
public enum PieceType {

    KING {
        @Override
        public List<Square> getControllingSquares(Position position, Square square) {
            return PieceMoves.getControllingSquareOne(square, MoveType.ALL);
        }

        @Override
        public List<Square> getMoveSquares(Position position, Square square) {
            return KingMoves.getKingMoves(position, square);
        }
    },

    QUEEN {
        @Override
        public List<Square> getControllingSquares(Position position, Square square) {
            return PieceMoves.getControllingSquareAny(position, square, MoveType.ALL);
        }

        @Override
        public List<Square> getMoveSquares(Position position, Square square) {
            return PieceMoves.getMoveSquareAny(position, square, MoveType.ALL);
        }
    },

    ROOK {
        @Override
        public List<Square> getControllingSquares(Position position, Square square) {
            return PieceMoves.getControllingSquareAny(position, square, MoveType.ROOK);
        }

        @Override
        public List<Square> getMoveSquares(Position position, Square square) {
            return PieceMoves.getMoveSquareAny(position, square, MoveType.ROOK);
        }
    },

    BISHOP {
        @Override
        public List<Square> getControllingSquares(Position position, Square square) {
            return PieceMoves.getControllingSquareAny(position, square, MoveType.BISHOP);
        }

        @Override
        public List<Square> getMoveSquares(Position position, Square square) {
            return PieceMoves.getMoveSquareAny(position, square, MoveType.BISHOP);
        }
    },

    KNIGHT {
        @Override
        public List<Square> getControllingSquares(Position position, Square square) {
            return PieceMoves.getControllingSquareOne(square, MoveType.KNIGHT);
        }

        @Override
        public List<Square> getMoveSquares(Position position, Square square) {
            return PieceMoves.getMoveSquareOne(position, square, MoveType.KNIGHT);
        }
    },

    PAWN {
        @Override
        public List<Square> getControllingSquares(Position position, Square square) {
            return PawnMoves.getPawnControllingSquares(position, square);
        }

        @Override
        public List<Square> getMoveSquares(Position position, Square square) {
            return PawnMoves.getMoveSquares(position, square);
        }
    };

    public abstract List<Square> getControllingSquares(Position position, Square square);

    public abstract List<Square> getMoveSquares(Position position, Square square);
}
