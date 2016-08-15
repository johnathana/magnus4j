package magnus4j.chess;

import magnus4j.chess.logic.KingMoves;
import magnus4j.chess.position.Position;

import java.util.Collections;
import java.util.List;

/**
 * Piece enumeration.
 */
public enum Piece {

    WHITE_KING(PieceType.KING, Side.WHITE),

    WHITE_QUEEN(PieceType.QUEEN, Side.WHITE),

    WHITE_ROOK(PieceType.ROOK, Side.WHITE),

    WHITE_BISHOP(PieceType.BISHOP, Side.WHITE),

    WHITE_KNIGHT(PieceType.KNIGHT, Side.WHITE),

    WHITE_PAWN(PieceType.PAWN, Side.WHITE),

    BLACK_KING(PieceType.KING, Side.BLACK),

    BLACK_QUEEN(PieceType.QUEEN, Side.BLACK),

    BLACK_ROOK(PieceType.ROOK, Side.BLACK),

    BLACK_BISHOP(PieceType.BISHOP, Side.BLACK),

    BLACK_KNIGHT(PieceType.KNIGHT, Side.BLACK),

    BLACK_PAWN(PieceType.PAWN, Side.BLACK);

    /**
     * Piece type.
     */
    private final PieceType _pieceType;

    /**
     * Piece side.
     */
    private final Side _pieceSide;

    /**
     * Constructor.
     *
     * @param pieceType
     *            the piece type.
     * @param pieceSide
     *            the piece pieceSide.
     */
    Piece(final PieceType pieceType, final Side pieceSide) {
        _pieceType = pieceType;
        _pieceSide = pieceSide;
    }

    /**
     * The side of the piece.
     * 
     * @return the <code>Side</code>.
     */
    public Side getSide() {
        return _pieceSide;
    }

    /**
     * The type of the piece.
     * 
     * @return the <code>PieceType</code>.
     */
    public PieceType getPieceType() {
        return _pieceType;
    }

    /**
     * Get legal move squares.
     *
     * @param position
     *            the position.
     * @param square
     *            the piece square.
     * @return a <code>List</code> of legal move squares.
     */
    public List<Square> getLegalMoveSquares(final Position position, Square square) {

        if (_pieceSide != position.getActiveSide())
            return Collections.emptyList();

        List<Square> targetSquares = _pieceType.getMoveSquares(position, square);

        return KingMoves.filterLegalMoves(targetSquares, position, square);
    }
}
