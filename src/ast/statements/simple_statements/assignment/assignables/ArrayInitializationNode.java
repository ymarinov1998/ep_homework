package ast.statements.simple_statements.assignment.assignables;

import ast.expressions.ExpressionNode;
import ast.statements.simple_statements.assignment.AssignableNode;

public class ArrayInitializationNode extends AssignableNode {

    private final String type;
    private final ExpressionNode expression;

    public ArrayInitializationNode(String type, ExpressionNode expression) {
        this.type = type;
        this.expression = expression;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<array initialization: " + type + ">");
        expression.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</array initialization>");
    }

    @Override
    public String toString() {
        return "[0]*(" + expression + ")";
    }
}
