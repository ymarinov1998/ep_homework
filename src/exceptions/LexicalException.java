package exceptions;

public class LexicalException extends CompilationException {
    public LexicalException(String message, int line, int position) {
        super(message, line, position);
    }

    public LexicalException(String message, int line, int position, Throwable cause) {
        super(message, line, position, cause);
    }
}
