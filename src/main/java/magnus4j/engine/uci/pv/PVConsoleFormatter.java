package magnus4j.engine.uci.pv;

import magnus4j.chess.notation.NotationType;
import magnus4j.engine.uci.UCIUtils;

/**
 * Principal Variation console formatter.
 */
public class PVConsoleFormatter implements PrincipalVariationStrategy {

    @Override
    public void handlePVUpdate(final PVInfo pvInfo) {

        StringBuilder pvLine = new StringBuilder();

        for (int i = 0; i < pvInfo.getMultiPV(); i++) {
            pvLine.append('I');
        }

        pvLine.append(". ");

        if (pvInfo._isMate) {
            pvLine.append(String.format("(#%d)", Math.abs(pvInfo._score)));
        } else {
            String equality = UCIUtils.equalityScore(pvInfo._score);
            pvLine.append(String.format("%s", equality));

            pvLine.append(String.format(" (%+.2f) [%d]: ", pvInfo._score / 100.0, pvInfo._depth));
        }

        String pvMoves = NotationType.STANDARD_ALGEBRAIC.getInstance()
            .movesToString(UCIUtils.pvToMoves(pvInfo._moves), pvInfo._position);
        pvLine.append(" ").append(pvMoves);

        System.out.format("%s%n", pvLine.toString());
    }
}
