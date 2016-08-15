package magnus4j.chess.logic;

import magnus4j.chess.Square;
import magnus4j.chess.move.MoveType;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by johnathana on 12/28/15.
 */
public class PositionUtilsTest {

    @Test
    public void testFindSquare() throws Exception {
        Square s1 = PositionUtils.findSquare(Square.A1, MoveType.UP_RIGHT);
        Square s2 = PositionUtils.findSquare(Square.H1, MoveType.RIGHT);
        Square s3 = PositionUtils.findSquare(Square.H8, MoveType.UP);

        assertEquals(s1, Square.B2);
        assertNull(s2);
        assertNull(s3);
    }
}
