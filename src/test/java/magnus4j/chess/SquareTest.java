package magnus4j.chess;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by johnathana on 6/24/16.
 */
public class SquareTest {

    @Test
    public void testToString() throws Exception {
        assertEquals("a1", Square.A1.toString());
    }

    @Test
    public void testGetFenSquare() throws Exception {
        assertEquals(63, Square.H1.getFenSquare());
    }

    @Test
    public void testGetFile() throws Exception {
        assertEquals('e', Square.E1.getFile());
    }

    @Test
    public void testGetRank() throws Exception {
        assertEquals('1', Square.E1.getRank());
    }

    @Test
    public void testValueOf() throws Exception {
        assertEquals(Square.valueOf(63), Square.H1);
    }

    @Test
    public void testFromStr() throws Exception {
        assertEquals(Square.A1, Square.fromStr("A1"));
    }
}
