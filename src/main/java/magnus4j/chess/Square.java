package magnus4j.chess;

import java.util.HashMap;
import java.util.Map;

/**
 * Board square enumeration.
 */
public enum Square {

    A8(0),  B8(1),  C8(2),  D8(3),  E8(4),  F8(5),  G8(6),  H8(7),
    A7(8),  B7(9),  C7(10), D7(11), E7(12), F7(13), G7(14), H7(15),
    A6(16), B6(17), C6(18), D6(19), E6(20), F6(21), G6(22), H6(23),
    A5(24), B5(25), C5(26), D5(27), E5(28), F5(29), G5(30), H5(31),
    A4(32), B4(33), C4(34), D4(35), E4(36), F4(37), G4(38), H4(39),
    A3(40), B3(41), C3(42), D3(43), E3(44), F3(45), G3(46), H3(47),
    A2(48), B2(49), C2(50), D2(51), E2(52), F2(53), G2(54), H2(55),
    A1(56), B1(57), C1(58), D1(59), E1(60), F1(61), G1(62), H1(63);

    /**
     * FEN square number.
     */
    private int _fenSquare;

    /**
     * Map for FEN number / <code>Square</code>.
     */
    private static Map<Integer, Square> _fenMap = new HashMap<>();

    static {
        for (Square square : Square.values()) {
            _fenMap.put(square._fenSquare, square);
        }
    }

    /**
     * Constructor.
     *
     * @param fenSquare
     *            the fen number of the square.
     */
    Square(int fenSquare) {
        _fenSquare = fenSquare;
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

    /**
     * Get FEN square number.
     * 
     * @return the fen number.
     */
    public int getFenSquare() {
        return _fenSquare;
    }

    /**
     * File of the square.
     * 
     * @return the file.
     */
    public char getFile() {
        return toString().charAt(0);
    }

    /**
     * Rank of the square.
     * 
     * @return the rank.
     */
    public char getRank() {
        return toString().charAt(1);
    }

    /**
     * FEN to <code>Square</code>.
     * 
     * @param fenSquare
     *            the fen square.
     * @return the <code>Square</code>
     */
    public static Square valueOf(final int fenSquare) {
        return _fenMap.get(fenSquare);
    }

    /**
     * FEN name to <code>Square</code>.
     *
     * @param name
     *            the fen name.
     * @return the <code>Square</code>.
     */
    public static Square fromStr(final String name) {
        return Square.valueOf(name.toUpperCase());
    }
}
