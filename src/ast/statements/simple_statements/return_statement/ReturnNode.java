package ast.statements.simple_statements.return_statement;

import ast.expressions.ExpressionNode;
import ast.statements.StatementNode;

public class ReturnNode extends StatementNode {

    private final ExpressionNode returnValue;

    public ReturnNode(ExpressionNode returnValue) {
        this.returnValue = returnValue;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<return>");
        returnValue.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</return>");
    }

    @Override
    public String toString() {
        return "return " + returnValue;
    }
}
