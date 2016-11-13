package magnus4j.pgn;

import magnus4j.chess.game.Game;
import magnus4j.chess.move.Move;
import magnus4j.chess.notation.Notation;
import magnus4j.chess.notation.NotationType;
import magnus4j.chess.position.FEN;
import magnus4j.chess.position.MovablePosition;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * PGN reader.
 */
public class PGNReader {

    private static final String PGN_MOVE_REGEX = "[0-9]*\\.\\s?([^' ']+ [^' ']+)";

    private static final Notation notation = NotationType.FIGURINE_ALGEBRAIC.getInstance();


    public static Game gameFromPGN(final String pgn) {

        List<Move> moves = new ArrayList<>();
        MovablePosition pos = new MovablePosition(FEN.START_POSITION);

        Pattern p = Pattern.compile(PGN_MOVE_REGEX);
        Matcher matcher = p.matcher(pgn);

        try {
            while (matcher.find()) {
                String[] moveRow = matcher.group(1).split(" ");

                for (String moveStr : moveRow) {
                    Move move = notation.stringToMove(moveStr.trim(), pos);
                    pos.makeMove(move);
                    moves.add(move);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return new Game(moves);
    }
}
