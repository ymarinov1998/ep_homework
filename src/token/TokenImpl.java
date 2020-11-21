package token;

public class TokenImpl implements Token<TokenType> {

    private final TokenType tokenType;
    private final String text;
    private final int line;
    private final int position;

    public TokenImpl(TokenType tokenType, String text, int line, int position) {
        this.tokenType = tokenType;
        this.text = text;
        this.line = line;
        this.position = position;
    }

    public TokenImpl(TokenType tokenType, int position, int lineNumber) {
        this(tokenType, tokenType.value, position, lineNumber);
    }

    @Override
    public TokenType getTokenType() {
        return tokenType;
    }

    @Override
    public String getTokenName() {
        return tokenType.name();
    }

    @Override
    public String getTokenText() {
        return text;
    }

    @Override
    public int getLine() {
        return line;
    }

    @Override
    public int getPosition() {
        return position;
    }

}
