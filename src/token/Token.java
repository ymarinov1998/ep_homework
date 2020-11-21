package token;

public interface Token<Type> {
    Type getTokenType();
    String getTokenName();
    String getTokenText();
    int getLine();
    int getPosition();
}
