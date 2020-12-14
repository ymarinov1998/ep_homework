package ast.expressions.multiplicative_operators;

import ast.expressions.ExpressionNode;

public class DivisionNode extends ExpressionNode {
    private final ExpressionNode left;
    private final ExpressionNode right;

    public DivisionNode(ExpressionNode left, ExpressionNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<division>");
        left.printNode(tabCount + 1);
        right.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</division>");
    }
}
