package lexer;

import scanner.InputScanner;
import scanner.ScannerImpl;
import token.Token;
import token.TokenImpl;
import token.TokenType;

public class LexerImpl implements Lexer<TokenType> {

    private final InputScanner scanner;
    private char currentCharacter;
    private int currentPosition;
    private int currentLine;

    public LexerImpl(InputScanner scanner) {
        this.scanner = scanner;
    }

    @Override
    public Token<TokenType> getNextToken() {
        currentCharacter = scanner.getCurrentCharacter();
        currentLine = scanner.getCurrentLine();
        currentPosition = scanner.getCurrentPosition();
        while (scanner.hasInput()) {
            switch (currentCharacter) {
                case ' ':
                case '\t':
                    handleWhitespace();
                    continue;
                case '+':
                    return handleTwoCharacterOperation(TokenType.PLUS, TokenType.INCREMENT, TokenType.ASSIGNMENT_ADD);
                case '-':
                    return handleTwoCharacterOperation(TokenType.MINUS, TokenType.DECREMENT, TokenType.ASSIGNMENT_DIFF);
                case '*':
                    return handleTwoCharacterOperation(TokenType.MUL, TokenType.ASSIGNMENT_PROD);
                case '/':
                    return handleTwoCharacterOperation(TokenType.DIV, TokenType.ASSIGNMENT_QUOT);
                case '=':
                    return handleTwoCharacterOperation(TokenType.BECOMES, TokenType.EQUAL);
                case '<':
                    return handleTwoCharacterOperation(TokenType.LESS_THAN, TokenType.LESS_THAN_OR_EQUAL);
                case '>':
                    return handleTwoCharacterOperation(TokenType.GREATER_THAN, TokenType.GREATER_THAN_OR_EQUAL);
                case '{':
                    return BuildTokenAndAdvance(TokenType.LBRACKET);
                case '}':
                    return BuildTokenAndAdvance(TokenType.RBRACKET);
                case '[':
                    return BuildTokenAndAdvance(TokenType.LSQUARE);
                case ']':
                    return BuildTokenAndAdvance(TokenType.RSQUARE);
                case '(':
                    return BuildTokenAndAdvance(TokenType.LPAREN);
                case ')':
                    return BuildTokenAndAdvance(TokenType.RPAREN);
                case ';':
                    return BuildTokenAndAdvance(TokenType.SEMICOLON);
                case ',':
                    return BuildTokenAndAdvance(TokenType.COMMA);
                default:
                    if (isLetter(currentCharacter)) return handleIdentifier();
                    if (isDigit(currentCharacter)) return handleIntLiteral();
                    return BuildTokenAndAdvance(TokenType.OTHER, String.valueOf(currentCharacter));
            }
        }
        return null;
    }

    private boolean isDigit(char currentCharacter) {
        return Character.isDigit(currentCharacter);
    }

    private boolean isLetter(char currentCharacter) {
        return Character.isLetter(currentCharacter);
    }

    private void handleWhitespace() {
        while (scanner.getCurrentCharacter() == ' ' || scanner.getCurrentCharacter() == '\t') {
            scanner.readNextCharacter();
            currentCharacter = scanner.getCurrentCharacter();
        }
        currentLine = scanner.getCurrentLine();
        currentPosition = scanner.getCurrentPosition();
    }

    private Token<TokenType> handleTwoCharacterOperation(TokenType foundToken, TokenType... expectedTokens) {
        scanner.readNextCharacter();
        for (var token : expectedTokens) {
            if (scanner.getCurrentCharacter() == token.value.charAt(1)) {
                return BuildTokenAndAdvance(token);
            }
        }
        return BuildToken(foundToken);
    }

    private Token<TokenType> BuildToken(TokenType tokenType) {
        return new TokenImpl(tokenType, currentPosition, currentLine);
    }

    private Token<TokenType> BuildToken(TokenType tokenType, String text) {
        return new TokenImpl(tokenType, text, currentPosition, currentLine);
    }

    private Token<TokenType> BuildTokenAndAdvance(TokenType tokenType) {
        scanner.readNextCharacter();
        return new TokenImpl(tokenType, currentPosition, currentLine);
    }

    private Token<TokenType> BuildTokenAndAdvance(TokenType tokenType, String text) {
        scanner.readNextCharacter();
        return new TokenImpl(tokenType, text, currentPosition, currentLine);
    }

    private Token<TokenType> handleIntLiteral() {
        StringBuilder digitString = new StringBuilder();
        while (isDigit(scanner.getCurrentCharacter())) {
            digitString.append(scanner.getCurrentCharacter());
            scanner.readNextCharacter();
        }
        try {
            Integer.parseInt(digitString.toString());
        } catch (NumberFormatException exception) {
            // Handle exception
        }
        return new TokenImpl(TokenType.INT_LITERAL, digitString.toString(), currentPosition, currentLine);
    }

    private Token<TokenType> handleIdentifier() {
        StringBuilder sb = new StringBuilder();
        while (isLetter(scanner.getCurrentCharacter()) || isDigit(scanner.getCurrentCharacter())) {
            sb.append(scanner.getCurrentCharacter());
            scanner.readNextCharacter();
        }
        String identifier = sb.toString();
        return (TokenType.isKeyword(identifier)) ?
                BuildToken(TokenType.valueOf(identifier.toUpperCase()), identifier) : BuildToken(TokenType.IDENTIFIER, identifier);
    }

    private void printTokens() {
        Token<TokenType> token;
        while ((token = getNextToken()) != null) {
            System.out.printf("[%s : '%s']\n", token.getTokenName(), token.getTokenText());
        }
    }

    public static void main(String[] args) {
        try {
            LexerImpl lexer = new LexerImpl(new ScannerImpl("resources/test.txt"));
            lexer.printTokens();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
