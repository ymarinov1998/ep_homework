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
    }

}
