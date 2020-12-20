package ast.expressions.relational_operators;

import ast.expressions.ExpressionNode;

public class LessThanOrEqualNode extends ExpressionNode {
    private final ExpressionNode left;
    private ExpressionNode right;

    public LessThanOrEqualNode(ExpressionNode left, ExpressionNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<less than or equal>");
        left.printNode(tabCount + 1);
        right.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</less than or equal>");
    }

    @Override
    public String toString() {
        return left + " <= " + right;
    }
}