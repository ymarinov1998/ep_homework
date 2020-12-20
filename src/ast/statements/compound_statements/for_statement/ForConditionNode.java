package ast.statements.compound_statements.for_statement;

import ast.Node;
import ast.expressions.ExpressionNode;

public class ForConditionNode implements Node {
    private final ExpressionNode expression;

    public ForConditionNode(ExpressionNode expression) {
        this.expression = expression;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<condition>");
        expression.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</condition>");
    }

    @Override
    public String toString() {
        return expression.toString();
    }
}
