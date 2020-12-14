package ast.variables.types.literals;

import ast.expressions.ExpressionNode;

public class BoolLiteralNode extends ExpressionNode {
    private final String value;

    public BoolLiteralNode(String value) {
        this.value = value;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<bool: " + value + ">");
    }
}
