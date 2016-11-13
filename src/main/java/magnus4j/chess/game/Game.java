package magnus4j.chess.game;

import magnus4j.chess.game.Line;
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

    private Line _line;

    private MovablePosition _currentPosition;

    /**
     * Default constructor.
     *
     * @param position
     *            the starting position.
     */
    public Game(final Position position) {
        _moveIndex = 0;
        _line = new Line(position, new LinkedList<>());
        _currentPosition = new MovablePosition(position);
    }

    /**
     * Moves constructor.
     *
     * @param moves
     *            the starting position.
     */
    public Game(final List<Move> moves) {
        _moveIndex = 0;
        _line = new Line(new Position(), moves);
        _currentPosition = new MovablePosition();
    }

    public int getMoveIndex() {
        return _moveIndex;
    }

    public Position getStartPosition() {
        return _line.getPosition();
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

        if (ply < 0 || ply > _line.getMoves().size()) {
            return;
        }

        if (ply < _moveIndex) {
            _moveIndex = 0;
            _currentPosition = new MovablePosition(_line.getPosition());
        }

        for (int i = _moveIndex; i < ply; i++) {
            Move move = _line.getMoves().get(i);
            _currentPosition.makeMove(move);
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
        if (_moveIndex == _line.getMoves().size()) {
            _line.getMoves().add(move);
            _currentPosition.makeMove(move);
            _moveIndex = _line.getMoves().size();
        } else {
            _line.getMoves().add(_moveIndex, move);
            _line.getMoves().subList(_moveIndex + 1, _line.getMoves().size()).clear();
            _moveIndex = 0;
            _currentPosition = new MovablePosition(_line.getPosition());
            gotoMove(_line.getMoves().size());
        }
    }

    @Override
    public String toString() {
        return NotationType.STANDARD_ALGEBRAIC.getInstance().lineToString(_line);
    }

    @Override
    public Iterator<Move> iterator() {
        return _line.iterator();
    }
}
