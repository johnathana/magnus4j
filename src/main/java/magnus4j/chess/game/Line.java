package magnus4j.chess.game;

import magnus4j.chess.move.Move;
import magnus4j.chess.position.Position;

import java.util.Iterator;
import java.util.List;

/**
 * Chess line.
 */
public class Line implements Iterable<Move> {

    private Position _position;

    private List<Move> _moves;


    public Line(final Position position, final List<Move> moves) {
        _position = position;
        _moves = moves;
    }

    public Position getPosition() {
        return _position;
    }

    public List<Move> getMoves() {
        return _moves;
    }

    @Override
    public Iterator<Move> iterator() {
        return _moves.iterator();
    }
}
