package ast.statements.compound_statements.for_statement;

import ast.Node;
import ast.statements.StatementNode;
import ast.variables.VariableDefinitionNode;

public class ForInitNode implements Node {

    private final StatementNode statement;

    public ForInitNode(StatementNode statement) {
        this.statement = statement;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<init>");
        statement.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</init>");
    }

    @Override
    public String toString() {
        return statement.toString();
    }

}
