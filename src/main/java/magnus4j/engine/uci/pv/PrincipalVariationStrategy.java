package magnus4j.engine.uci.pv;

/**
 * Principal Variation strategy pattern.
 */
public interface PrincipalVariationStrategy {

    void handlePVUpdate(PVInfo pvInfo);
}
