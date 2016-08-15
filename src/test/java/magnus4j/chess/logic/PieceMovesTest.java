package magnus4j.chess.logic;

import magnus4j.chess.PieceType;
import magnus4j.chess.Square;
import magnus4j.chess.position.Position;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

/**
 * Created by johnathana on 1/4/16.
 */
public class PieceMovesTest {

    @Test
    public void testGetTargetSquares() throws Exception {
        Position position = new Position("8/8/2pkpR2/3nr2q/rPR1P3/5PB1/4K3/3Q4 w - - 0 1");
        for (Square s : position.getSquaresWithPiece()) {
            PieceMoves.getMoveSquares(position, s);
        }
    }

    @Test
    public void testKnightMovesStartingPosition() {
        Position position = new Position();
        assertEquals(Arrays.asList(Square.C3, Square.A3),
            PieceMoves.getMoveSquares(position, Square.B1));
        assertEquals(Arrays.asList(Square.D2, Square.C3, Square.A3),
            PieceType.KNIGHT.getControllingSquares(position, Square.B1));
    }
}
