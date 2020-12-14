package ast.variables.types.literals;

import ast.expressions.ExpressionNode;

public class IntLiteralNode extends ExpressionNode {
    private final String value;

    public IntLiteralNode(String value) {
        this.value = value;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<int: " + value + ">");
    }
}
