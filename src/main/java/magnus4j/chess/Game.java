package magnus4j.chess;

import magnus4j.chess.move.Move;
import magnus4j.chess.notation.NotationType;
import magnus4j.chess.position.MovablePosition;
import magnus4j.chess.position.Position;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * Chess game.
 */
public class Game implements Iterable<Move> {

    private int _moveIndex;

    private Position _startPosition;

    private List<Move> _moves;

    private MovablePosition _currentPosition;

    /**
     * Default constructor.
     *
     * @param position
     *            the starting position.
     */
    public Game(final Position position) {
        _moveIndex = 0;
        _startPosition = position;
        _moves = new LinkedList<>();
        _currentPosition = new MovablePosition(_startPosition);
    }

    /**
     * Moves constructor.
     *
     * @param moves
     *            the starting position.
     */
    public Game(final List<Move> moves) {
        _moveIndex = 0;
        _startPosition = new Position();
        _moves = new LinkedList<>(moves);
        _currentPosition = new MovablePosition(_startPosition);
    }

    public int getMoveIndex() {
        return _moveIndex;
    }

    public Position getStartPosition() {
        return _startPosition;
    }

    public MovablePosition getCurrentPosition() {
        return _currentPosition;
    }

    /**
     * Go to move with the specified index.
     * 
     * @param ply
     *            the ply.
     */
    public void gotoMove(int ply) {

        if (ply < 0 || ply > _moves.size()) {
            return;
        }

        if (ply < _moveIndex) {
            _moveIndex = 0;
            _currentPosition = new MovablePosition(_startPosition);
        }

        for (int i = _moveIndex; i < ply; i++) {
            Move move = _moves.get(i);
            _currentPosition.doMove(move);
        }

        _moveIndex = ply;
    }

    /**
     * Play the move provided.
     * 
     * @param move
     *            the move.
     */
    public void playMove(final Move move) {
        if (_moveIndex == _moves.size()) {
            _moves.add(move);
            _currentPosition.doMove(move);
            _moveIndex = _moves.size();
        } else {
            _moves.add(_moveIndex, move);
            _moves.subList(_moveIndex + 1, _moves.size()).clear();
            _moveIndex = 0;
            _currentPosition = new MovablePosition(_startPosition);
            gotoMove(_moves.size());
        }
    }

    @Override
    public String toString() {
        return NotationType.STANDARD_ALGEBRAIC.getInstance().movesToString(_moves, _startPosition);
    }

    @Override
    public Iterator<Move> iterator() {
        return _moves.iterator();
    }
}
