package ast.statements.compound_statements.for_statement;

import ast.Node;
import ast.statements.StatementNode;

public class ForLoopNode implements Node {
    private final StatementNode statement;

    public ForLoopNode(StatementNode statement) {
        this.statement = statement;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<loop>");
        statement.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<loop>");
    }

    @Override
    public String toString() {
        return statement.toString();
    }
}
