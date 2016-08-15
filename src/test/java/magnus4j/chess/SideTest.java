package magnus4j.chess;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by johnathana on 6/24/16.
 */
public class SideTest {

    @Test
    public void testOpposite() throws Exception {
        assertEquals(Side.WHITE, Side.BLACK.opposite());
        assertEquals(Side.BLACK, Side.WHITE.opposite());
    }
}
