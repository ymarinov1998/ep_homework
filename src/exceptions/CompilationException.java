package exceptions;


public class CompilationException extends RuntimeException {
    public CompilationException(String message, int line, int position) {
        super(addLineAndPosition(message, line, position));
    }

    public CompilationException(String message, int line, int position, Throwable cause) {
        super(addLineAndPosition(message, line, position), cause);
    }

    private static String addLineAndPosition(String message, int line, int position) {
        return String.format("%s at line %d, position %d.",message,line,position);
    }
}
