package parser;

import lexer.Lexer;
import lexer.LexerImpl;
import scanner.InputScanner;
import scanner.InputScannerImpl;
import token.Token;
import token.TokenType;

import java.io.IOException;

//TODO: Create custom exceptions
//TODO: Create AST
public class ParserImpl {

    private final Lexer<TokenType> lexer;
    private Token<TokenType> currentToken;

    public ParserImpl(Lexer<TokenType> lexer) {
        this.lexer = lexer;
        currentToken = lexer.getNextToken();
    }

    private void accept(TokenType tokenType) {
        if (!currentToken.getTokenType().equals(tokenType))
            throw new RuntimeException(
                    String.format("Token mismatch! Expected %s, found %s on line: %d, position: %d.",
                            tokenType.value, currentToken.getTokenText(), currentToken.getLine(), currentToken.getPosition())
            );
        currentToken = lexer.getNextToken();
    }

    public void tryParse() {
        program();
    }

    private void program() {
        while (currentToken.getTokenType().equals(TokenType.FUNCTION)) {
            functionDefinition();
        }
    }

    private void functionDefinition() {
        accept(TokenType.FUNCTION);
        accept(TokenType.IDENTIFIER);
        accept(TokenType.LPAREN);
        if (TokenType.isPrimitiveType(currentToken.getTokenType())) {
            formalParameters();
        }
        accept(TokenType.RPAREN);
        if (currentToken.getTokenType().equals(TokenType.RETURNS)) {
            accept(TokenType.RETURNS);
            type();
        }
        block();
    }

    private void formalParameters() {
        type();
        accept(TokenType.IDENTIFIER);
        if (currentToken.getTokenType().equals(TokenType.COMMA)) {
            accept(TokenType.COMMA);
            formalParameters();
        }
    }

    private void type() {
        if (TokenType.isPrimitiveType(currentToken.getTokenType())) {
            accept(currentToken.getTokenType());
            if (currentToken.getTokenType().equals(TokenType.LSQUARE)) {
                accept(TokenType.LSQUARE);
                accept(TokenType.RSQUARE);
            }
        } else {
            throw new RuntimeException(
                    String.format("Token mismatch! Expected INT / BOOL, found %s on line: %d, position: %d.",
                            currentToken.getTokenName(), currentToken.getLine(), currentToken.getPosition())
            );
        }
    }

    private void block() {
        accept(TokenType.LBRACKET);
        while (TokenType.isStatementTerminal(currentToken.getTokenType())) {
            statement();
        }
        accept(TokenType.RBRACKET);
    }

    private void statement() {
        if (TokenType.isCompoundStatementTerminal(currentToken.getTokenType())) {
            compoundStatement();
        } else {
            simpleStatement();
            accept(TokenType.SEMICOLON);
        }
    }

    private void compoundStatement() {
        switch (currentToken.getTokenType()) {
            case IF -> ifStatement();
            case WHILE -> whileStatement();
            case FOR -> forStatement();
            case SWITCH -> switchStatement();
        }
    }

    private void ifStatement() {
        accept(TokenType.IF);
        expression();
        block();
        while (currentToken.getTokenType().equals(TokenType.ELSEIF)) {
            accept(TokenType.ELSEIF);
            expression();
            block();
        }
        if (currentToken.getTokenType().equals(TokenType.ELSE)) {
            accept(TokenType.ELSE);
            block();
        }
    }

    private void whileStatement() {
        accept(TokenType.WHILE);
        expression();
        block();
    }

    //TODO: Generalize FOR LOOP expressions
    private void forStatement() {
        accept(TokenType.FOR);
        if (currentToken.getTokenType().equals(TokenType.LPAREN)) {
            accept(TokenType.LPAREN);
            variableDefinition();
            accept(TokenType.SEMICOLON);
            expression();
            accept(TokenType.SEMICOLON);
            simpleStatement();
            accept(TokenType.RPAREN);
        } else {
            type();
            accept(TokenType.IDENTIFIER);
            accept(TokenType.IN);
            accept(TokenType.IDENTIFIER);
        }
        block();
    }

    private void switchStatement() {
        accept(TokenType.SWITCH);
        accept(TokenType.LPAREN);
        accept(TokenType.IDENTIFIER);
        accept(TokenType.RPAREN);
        accept(TokenType.LBRACKET);
        while (currentToken.getTokenType().equals(TokenType.CASE)) {
            accept(TokenType.CASE);
            expression();
            block();
        }
        accept(TokenType.DEFAULT);
        block();
        accept(TokenType.RBRACKET);
    }

    private void simpleStatement() {
        switch (currentToken.getTokenType()) {
            case INT, BOOL -> variableDefinition();
            case RETURN -> returnStatement();
            case IDENTIFIER -> {
                variable();
                switch (currentToken.getTokenType()) {
                    case BECOMES, ASSIGNMENT_ADD, ASSIGNMENT_DIFF, ASSIGNMENT_PROD, ASSIGNMENT_QUOT -> assignment();
                    case INCREMENT, DECREMENT -> accept(currentToken.getTokenType());
                    case LPAREN -> functionCall();
                    default -> throw new RuntimeException(
                            String.format("Token mismatch! Expected assignment, unary operation or a function call, " +
                                            "found %s on line: %d, position: %d.",
                                    currentToken.getTokenText(), currentToken.getLine(), currentToken.getPosition())
                    );
                }
            }
        }
    }

    private void variableDefinition() {
        type();
        variable();
        assignment();
    }

    private void returnStatement() {
        accept(TokenType.RETURN);
        expression();
        if (currentToken.getTokenType().equals(TokenType.IF)) {
            accept(TokenType.IF);
            expression();
            accept(TokenType.ELSE);
            expression();
        }
    }

    private void variable() {
        accept(TokenType.IDENTIFIER);
        if (currentToken.getTokenType().equals(TokenType.LSQUARE)) {
            accept(TokenType.LSQUARE);
            expression();
            accept(TokenType.RSQUARE);
        }
    }

    private void assignment() {
        if (TokenType.isAssignmentTerminal(currentToken.getTokenType())) {
            if (currentToken.getTokenType().equals(TokenType.BECOMES)) {
                accept(TokenType.BECOMES);
                assignable();
            } else {
                accept(currentToken.getTokenType());
                expression();
            }
        } else {
            throw new RuntimeException(
                    String.format("Token mismatch! Expected assignment operator, found %s on line: %d, position: %d.",
                            currentToken.getTokenText(), currentToken.getLine(), currentToken.getPosition())
            );
        }
    }

    private void functionCall() {
        accept(TokenType.LPAREN);
        if (TokenType.isFactorTerminal(currentToken.getTokenType())) {
            actualParameters();
        }
        accept(TokenType.RPAREN);
    }

    private void actualParameters() {
        expression();
        if (currentToken.getTokenType().equals(TokenType.COMMA)) {
            expression();
        }
    }

    private void assignable() {
        if (TokenType.isFactorTerminal(currentToken.getTokenType())) {
            expression();
        } else if (TokenType.isPrimitiveType(currentToken.getTokenType())) {
            arrayInitialization();
        }
    }

    private void arrayInitialization() {
        if (TokenType.isPrimitiveType(currentToken.getTokenType())) {
            accept(currentToken.getTokenType());
            accept(TokenType.LSQUARE);
            expression();
            accept(TokenType.RSQUARE);
        } else {
            throw new RuntimeException(
                    String.format("Token mismatch! Expected array initialization, found %s on line: %d, position: %d.",
                            currentToken.getTokenText(), currentToken.getLine(), currentToken.getPosition())
            );
        }
    }

    private void expression() {
        simpleExpression();
        if (TokenType.isRelationalOperator(currentToken.getTokenType())) {
            accept(currentToken.getTokenType());
            simpleExpression();
        }
    }

    private void simpleExpression() {
        signedTerm();
        if (TokenType.isAdditiveOperator(currentToken.getTokenType())
                || currentToken.getTokenType().equals(TokenType.OR)) {
            accept(currentToken.getTokenType());
            signedTerm();
        }
    }

    private void signedTerm() {
        if (TokenType.isUnaryOperator(currentToken.getTokenType())) {
            accept(currentToken.getTokenType());
        }
        term();
    }

    //TODO: Figure out if it should be factor() or signedTerm()
    private void term() {
        factor();
        if (TokenType.isMultiplicativeOperator(currentToken.getTokenType())
                || currentToken.getTokenType().equals(TokenType.AND)) {
            accept(currentToken.getTokenType());
            //factor
            signedTerm();
        }
    }

    private void factor() {
        switch (currentToken.getTokenType()) {
            case IDENTIFIER -> {
                variable();
                if (currentToken.getTokenType().equals(TokenType.LPAREN))
                    functionCall();
            }
            case INT_LITERAL -> accept(TokenType.INT_LITERAL);
            case TRUE, FALSE -> accept(currentToken.getTokenType());
            case LPAREN -> {
                accept(TokenType.LPAREN);
                expression();
                accept(TokenType.RPAREN);
            }
            default -> throw new RuntimeException(
                    String.format("Token mismatch! Expected factor, found %s on line: %d, position: %d.",
                            currentToken.getTokenText(), currentToken.getLine(), currentToken.getPosition())
            );
        }
    }

    public static void main(String[] args) throws IOException {
        InputScanner scanner = new InputScannerImpl("resources/test3.txt");
        Lexer<TokenType> lexer = new LexerImpl(scanner);
        ParserImpl parser = new ParserImpl(lexer);
        parser.tryParse();
    }

}
