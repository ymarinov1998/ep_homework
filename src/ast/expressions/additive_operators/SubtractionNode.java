package ast.expressions.additive_operators;

import ast.expressions.ExpressionNode;

public class SubtractionNode extends ExpressionNode {
    private final ExpressionNode left;
    private final ExpressionNode right;

    public SubtractionNode(ExpressionNode left, ExpressionNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<subtraction>");
        left.printNode(tabCount + 1);
        right.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</subtraction>");
    }

    @Override
    public String toString() {
        return left + " - " + right;
    }
}
