package magnus4j.engine.uci.pv;

import magnus4j.chess.notation.Notation;
import magnus4j.chess.notation.NotationType;
import magnus4j.engine.uci.UCIUtils;

/**
 * Principal Variation console formatter.
 */
public class PVConsoleFormatter implements PrincipalVariationStrategy {

    private Notation notation = NotationType.STANDARD_ALGEBRAIC.getInstance();

    @Override
    public void handlePVUpdate(final PVInfo pvInfo) {

        StringBuilder pvLine = new StringBuilder();

        for (int i = 0; i < pvInfo.getMultiPV(); i++) {
            pvLine.append('I');
        }

        pvLine.append(". ");

        if (pvInfo.isMate()) {
            pvLine.append(String.format("(#%d)", Math.abs(pvInfo.getScore())));
        } else {
            String equality = UCIUtils.equalityScore(pvInfo.getScore());
            pvLine.append(String.format("%s", equality));

            pvLine.append(String.format(" (%+.2f) [%d]: ", pvInfo.getScore() / 100.0, pvInfo.getDepth()));
        }

        String pvMoves = notation.lineToString(pvInfo.getLine());
        pvLine.append(" ").append(pvMoves);
        System.out.format("%s%n", pvLine.toString());
    }
}
