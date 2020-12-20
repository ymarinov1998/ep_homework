package token;

import java.util.HashSet;
import java.util.Set;

public enum TokenType {
    FUNCTION("function"), INT("int"), BOOL("bool"), RETURNS("returns"),
    RETURN("return"), WHILE("while"), FOR("for"), SWITCH("switch"),
    CASE("case"), DEFAULT("default"), IF("if"), ELSEIF("elseif"),
    ELSE("else"), TRUE("true"), FALSE("false"), IN("in"),
    PLUS("+"), MINUS("-"), MUL("*"), DIV("/"),
    BECOMES("="), EQUAL("=="), NOTEQUAL("!="), GREATER_THAN(">"),
    LESS_THAN("<"), GREATER_THAN_OR_EQUAL(">="), LESS_THAN_OR_EQUAL("<="),
    INCREMENT("++"), DECREMENT("--"), ASSIGNMENT_ADD("+="),
    ASSIGNMENT_DIFF("-="), ASSIGNMENT_PROD("*="), ASSIGNMENT_QUOT("/="),
    NOT("not"), AND("and"), OR("or"), LSQUARE("["), RSQUARE("]"),
    LBRACKET("{"), RBRACKET("}"), LPAREN("("), RPAREN(")"), SEMICOLON(";"),
    COMMA(","), IDENTIFIER("identifier"), INT_LITERAL("int literal"), OTHER("");

    public final String value;

    TokenType(final String value) {
        this.value = value;
    }

    public static TokenType valueOf(int token) {
        return TokenType.values()[token];
    }

    public static boolean isKeyword(String identifier) {
        return keywords.contains(identifier);
    }

    private static final Set<String> keywords = new HashSet<>();

    static {
        keywords.add(FUNCTION.value);
        keywords.add(INT.value);
        keywords.add(BOOL.value);
        keywords.add(RETURNS.value);
        keywords.add(RETURN.value);
        keywords.add(WHILE.value);
        keywords.add(FOR.value);
        keywords.add(SWITCH.value);
        keywords.add(CASE.value);
        keywords.add(DEFAULT.value);
        keywords.add(IF.value);
        keywords.add(ELSEIF.value);
        keywords.add(ELSE.value);
        keywords.add(TRUE.value);
        keywords.add(FALSE.value);
        keywords.add(IN.value);
        keywords.add(NOT.value);
        keywords.add(AND.value);
        keywords.add(OR.value);
    }

    public static boolean isPrimitiveType(TokenType tokenType) {
        return tokenType.equals(INT) || tokenType.equals(BOOL);
    }

    private static final Set<TokenType> compoundStatementTerminals = new HashSet<>();

    static {
        compoundStatementTerminals.add(IF);
        compoundStatementTerminals.add(WHILE);
        compoundStatementTerminals.add(FOR);
        compoundStatementTerminals.add(SWITCH);
    }

    public static boolean isStatementTerminal(TokenType tokenType) {
        return isSimpleStatementTerminal(tokenType) || isCompoundStatementTerminal(tokenType);
    }

    public static boolean isSimpleStatementTerminal(TokenType tokenType) {
        return tokenType.equals(IDENTIFIER) || tokenType.equals(RETURN) || isPrimitiveType(tokenType);
    }

    public static boolean isCompoundStatementTerminal(TokenType tokenType) {
        return compoundStatementTerminals.contains(tokenType);
    }

    private static final Set<TokenType> assignmentTerminals = new HashSet<>();

    static {
        assignmentTerminals.add(BECOMES);
        assignmentTerminals.add(ASSIGNMENT_ADD);
        assignmentTerminals.add(ASSIGNMENT_DIFF);
        assignmentTerminals.add(ASSIGNMENT_PROD);
        assignmentTerminals.add(ASSIGNMENT_QUOT);
    }

    public static boolean isAssignmentTerminal(TokenType tokenType) {
        return assignmentTerminals.contains(tokenType);
    }

    public static boolean isEqualityOperator(TokenType tokenType) {
        return tokenType.equals(EQUAL) || tokenType.equals(NOTEQUAL);
    }

    private static final Set<TokenType> relationalOperators = new HashSet<>();

    static {
        relationalOperators.add(LESS_THAN);
        relationalOperators.add(LESS_THAN_OR_EQUAL);
        relationalOperators.add(GREATER_THAN);
        relationalOperators.add(GREATER_THAN_OR_EQUAL);
    }

    public static boolean isRelationalOperator(TokenType tokenType) {
        return relationalOperators.contains(tokenType);
    }

    public static boolean isAdditiveOperator(TokenType tokenType) {
        return tokenType.equals(PLUS) || tokenType.equals(MINUS);
    }

    public static boolean isMultiplicativeOperator(TokenType tokenType) {
        return tokenType.equals(MUL) || tokenType.equals(DIV);
    }

    private static final Set<TokenType> factorTerminal = new HashSet<>();

    static {
        factorTerminal.add(IDENTIFIER);
        factorTerminal.add(INT_LITERAL);
        factorTerminal.add(LPAREN);
        factorTerminal.add(TRUE);
        factorTerminal.add(FALSE);
    }

    public static boolean isFactorTerminal(TokenType tokenType) {
        return factorTerminal.contains(tokenType);
    }
}
