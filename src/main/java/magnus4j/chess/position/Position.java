package magnus4j.chess.position;

import magnus4j.chess.Piece;
import magnus4j.chess.Side;
import magnus4j.chess.Square;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;
import java.util.Set;

/**
 * Chess position representation.
 */
public class Position {

    /**
     * The chess position as a map.
     */
    Map<Square, Piece> _position;

    /**
     * The side to move.
     */
    Side _activeSide = Side.WHITE;

    /**
     * The castling rights.
     */
    String _castling = "";

    /**
     * The en passant target square.
     */
    Square _enPassant;

    /**
     * The number of half moves since the last capture.
     */
    int _halfMoveClock = 0;

    /**
     * The move counter.
     */
    int _fullMoveNumber = 1;

    /**
     * Start position constructor.
     */
    public Position() {
        this(FEN.START_POSITION);
    }

    /**
     * Copy constructor.
     * 
     * @param position
     *            the given position.
     */
    public Position(final Position position) {
        _position = new EnumMap<>(Square.class);
        _position.putAll(position.getPosition());
        _activeSide = position.getActiveSide();
        _castling = position.getCastling();
        _enPassant = position.getEnPassant();
        _halfMoveClock = position.getHalfMoveClock();
        _fullMoveNumber = position.getFullMoveNumber();
    }

    /**
     * Constructor from FEN string.
     * 
     * @param fen
     *            the fen string.
     */
    public Position(final String fen) {
        FEN.positionFromFEN(this, fen);
    }

    public String getFEN() {
        return FEN.fenFromPosition(this);
    }

    public boolean hasPiece(final Square square) {
        return _position.containsKey(square);
    }

    public boolean isEmpty(final Square square) {
        return !_position.containsKey(square);
    }

    public boolean areAllEmpty(final Square... squares) {
        for (Square s : squares) {
            if (_position.containsKey(s)) {
                return false;
            }
        }
        return true;
    }

    public Piece getPiece(final Square square) {
        return _position.get(square);
    }

    public Map<Square, Piece> getPosition() {
        return Collections.unmodifiableMap(_position);
    }

    public Set<Square> getSquaresWithPiece() {
        return Collections.unmodifiableSet(_position.keySet());
    }

    public Side getActiveSide() {
        return _activeSide;
    }

    public boolean isWhitesTurn() {
        return _activeSide == Side.WHITE;
    }

    public Square getEnPassant() {
        return _enPassant;
    }

    public String getCastling() {
        return _castling;
    }

    public int getHalfMoveClock() {
        return _halfMoveClock;
    }

    public int getFullMoveNumber() {
        return _fullMoveNumber;
    }

    @Override
    public String toString() {
        return getFEN();
    }
}
