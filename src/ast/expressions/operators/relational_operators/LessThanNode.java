package ast.expressions.operators.relational_operators;

import ast.expressions.ExpressionNode;

public class LessThanNode extends ExpressionNode {
    private final ExpressionNode left;
    private final ExpressionNode right;

    public LessThanNode(ExpressionNode left, ExpressionNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<less than>");
        left.printNode(tabCount + 1);
        right.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</less than>");
    }
}
