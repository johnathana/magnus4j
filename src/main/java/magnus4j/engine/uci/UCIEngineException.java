package magnus4j.engine.uci;

/**
 * UCI engine runtime exception.
 */
public class UCIEngineException extends RuntimeException {

    public UCIEngineException() {
        super();
    }

    public UCIEngineException(Throwable cause) {
        super(cause);
    }

    public UCIEngineException(String message) {
        super(message);
    }

    public UCIEngineException(String message, Throwable cause) {
        super(message, cause);
    }
}
