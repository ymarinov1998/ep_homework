package exceptions;

import token.Token;
import token.TokenType;

public class SyntaxException extends CompilationException {
    public SyntaxException(String message, Token<TokenType> token) {
        super(message, token.getLine(), token.getPosition());
    }

    public SyntaxException(String message, Token<TokenType> token, Throwable cause) {
        super(message, token.getLine(), token.getPosition(), cause);
    }
}
