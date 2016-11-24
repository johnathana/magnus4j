package magnus4j.chess.position;

import magnus4j.chess.Piece;
import magnus4j.chess.PieceType;
import magnus4j.chess.Side;
import magnus4j.chess.Square;
import magnus4j.chess.logic.PieceUtils;
import magnus4j.chess.logic.PositionUtils;
import magnus4j.chess.move.Move;
import magnus4j.chess.move.MoveType;

/**
 * Mutable position.
 */
public class MovablePosition extends Position {

    /**
     * Default constructor.
     */
    public MovablePosition() {
        super();
    }

    /**
     * Constructor from position.
     * 
     * @param position
     *            the position to set.
     */
    public MovablePosition(final Position position) {
        super(position);
    }

    /**
     * Constructor from fen.
     * 
     * @param fen
     *            the fen position to set.
     */
    public MovablePosition(final String fen) {
        super(fen);
    }

    /**
     * Set piece to position.
     * 
     * @param piece
     *            the piece to set.
     * @param square
     *            the square to set the piece.
     */
    public void setPiece(final Piece piece, final Square square) {
        _position.put(square, piece);
    }

    /**
     * Set active position.
     *
     * @param side
     *            the side to set.
     */
    public void setActiveSide(Side side) {
        _activeSide = side;
    }

    /**
     * Remove piece from square.
     *
     * @param square
     *            the square.
     */
    public void removePiece(final Square square) {
        _position.remove(square);
    }

    /**
     * Make a move.
     * 
     * @param move
     *            the move to make.
     */
    public void makeMove(final Move move) {

        boolean isCapture = isCaptureMove(move);
        Square enPassantSquare = getEnPassantSquare(move);

        Piece piece = getPiece(move.getFrom());
        if (piece == null) {
            new Exception().printStackTrace();
            throw new IllegalArgumentException(
                "No piece in " + move.getFrom() + " square (" + this.getFEN() + ")");
        }

        movePiece(move);
        updateCastlingRights(move, piece);

        _activeSide = _activeSide.opposite();
        _enPassant = enPassantSquare;
        _halfMoveClock = isCapture ? 0 : _halfMoveClock + 1;
        if (isWhitesTurn())
            _fullMoveNumber++;
    }

    private boolean isCaptureMove(final Move move) {
        return hasPiece(move.getTo()) || isEnPassantCapture(move);
    }

    private boolean isEnPassantCapture(final Move move) {
        Piece piece = getPiece(move.getFrom());
        return piece.getPieceType() == PieceType.PAWN && move.getTo() == _enPassant;
    }

    private Square getEnPassantSquare(final Move move) {
        Piece piece = getPiece(move.getFrom());
        if (piece.getPieceType() != PieceType.PAWN)
            return null;

        if (Math.abs(move.getFrom().getRank() - move.getTo().getRank()) == 2) {
            return PositionUtils.findSquare(move.getFrom(),
                (piece == Piece.WHITE_PAWN) ? MoveType.UP : MoveType.DOWN);
        }
        return null;
    }

    private void movePiece(final Move move) {
        Piece piece = move.isPromotion()
                ? PieceUtils.getPieceFromType(move.getPromotionType(), this.getActiveSide())
                : getPiece(move.getFrom());

        if (isEnPassantCapture(move)) {
            removePiece(PositionUtils.findEnPassantCaptureSquare(this, move.getFrom()));
        }

        removePiece(move.getFrom());
        setPiece(piece, move.getTo());

        castlingMove(move, piece);
    }

    private void castlingMove(Move move, Piece piece) {

        if ("-".equals(_castling) || piece.getPieceType() != PieceType.KING) {
            return;
        }

        if (PieceUtils.isWhiteShortCastle(piece, move)) {
            removePiece(Square.H1);
            setPiece(Piece.WHITE_ROOK, Square.F1);
        } else if (PieceUtils.isWhiteLongCastle(piece, move)) {
            removePiece(Square.A1);
            setPiece(Piece.WHITE_ROOK, Square.D1);
        } else if (PieceUtils.isBlackShortCastle(piece, move)) {
            removePiece(Square.H8);
            setPiece(Piece.BLACK_ROOK, Square.F8);
        } else if (PieceUtils.isBlackLongCastle(piece, move)) {
            removePiece(Square.A8);
            setPiece(Piece.BLACK_ROOK, Square.D8);
        }
    }

    private void updateCastlingRights(final Move move, final Piece piece) {

        if ("-".equals(_castling))
            return;

        PieceType pieceType = piece.getPieceType();
        if (pieceType != PieceType.KING && pieceType != PieceType.ROOK) {
            return;
        }

        switch (piece) {
        case WHITE_KING:
            removeCastlingRights("K");
            removeCastlingRights("Q");
            break;

        case BLACK_KING:
            removeCastlingRights("k");
            removeCastlingRights("q");
            break;

        case WHITE_ROOK:
            if (move.getFrom() == Square.H1) {
                removeCastlingRights("K");
            } else if (move.getFrom() == Square.A1) {
                removeCastlingRights("Q");
            }
            break;

        case BLACK_ROOK:
            if (move.getFrom() == Square.H8) {
                removeCastlingRights("k");
            } else if (move.getFrom() == Square.A8) {
                removeCastlingRights("q");
            }
            break;
        }
    }

    /**
     * Remove castling rights.
     * 
     * @param flag
     *            the castling flag.
     */
    private void removeCastlingRights(final String flag) {

        if (!_castling.contains(flag)) {
            return;
        }

        _castling = _castling.replace(flag, "");

        if (_castling.trim().equals("")) {
            _castling = "-";
        }
    }
}
