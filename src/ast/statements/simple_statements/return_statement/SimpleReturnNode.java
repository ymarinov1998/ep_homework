package ast.statements.simple_statements.return_statement;

import ast.expressions.ExpressionNode;
import ast.statements.StatementNode;

public class SimpleReturnNode extends StatementNode {

    private final ExpressionNode returnValue;

    public SimpleReturnNode(ExpressionNode returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<return>");
        returnValue.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</return>");
    }
}
