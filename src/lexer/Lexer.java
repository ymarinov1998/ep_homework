package lexer;

import token.Token;

public interface Lexer<Type> {
    Token<Type> getNextToken();
}
