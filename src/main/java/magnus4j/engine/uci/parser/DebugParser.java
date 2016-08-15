package magnus4j.engine.uci.parser;

/**
 * Debug parser prints uci lines to stdout.
 */
public class DebugParser implements UCIParser {

    @Override
    public void parseUCILine(String line) {
        System.out.println(line);
    }
}
