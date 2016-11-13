package magnus4j.chess.game;

import magnus4j.chess.Square;
import magnus4j.chess.game.Game;
import magnus4j.chess.move.Move;
import org.junit.Before;
import org.junit.Test;
import magnus4j.pgn.PGNReader;

import static org.junit.Assert.assertEquals;

/**
 * Created by johnathana on 5/10/16.
 */
public class GameTest {

    private Game game;

    @Before
    public void setUp() throws Exception {
        game = PGNReader.gameFromPGN("1.d4 d5 2.c4 e6 3.Nc3 Nf6 ");
    }

    @Test
    public void testPlayMove() throws Exception {
        game.gotoMove(4);
        game.playMove(new Move(Square.G1, Square.F3));
        assertEquals("rnbqkbnr/ppp2ppp/4p3/3p4/2PP4/5N2/PP2PPPP/RNBQKB1R b KQkq - 5 3",
            game.getCurrentPosition().getFEN());
    }
}
