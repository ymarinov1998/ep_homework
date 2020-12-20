package ast.expressions.unary_operators;

import ast.expressions.ExpressionNode;

public class InversionNode extends ExpressionNode {
    private final ExpressionNode expression;

    public InversionNode(ExpressionNode expression) {
        this.expression = expression;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<inversion>");
        expression.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</inversion>");
    }

    @Override
    public String toString() {
        return "not " + expression;
    }
}
