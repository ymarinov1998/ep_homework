package token;

public class TokenImpl implements Token<TokenType> {

    private final TokenType tokenType;
    private final String text;
    private final int line;
    private final int position;

    public TokenImpl(TokenType tokenType, String text, int position, int line) {
        this.tokenType = tokenType;
        this.text = text;
        this.position = position;
        this.line = line;
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

    @Override
    public String toString() {
        return "TokenImpl{" +
                "tokenType=" + tokenType +
                ", text='" + text + '\'' +
                ", line=" + line +
                ", position=" + position +
                '}';
    }
}
