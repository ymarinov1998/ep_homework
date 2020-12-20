package ast.statements.simple_statements.unary_operations;

import ast.statements.StatementNode;
import ast.variables.VariableNode;

public class DecrementNode extends StatementNode {
    private final VariableNode variable;

    public DecrementNode(VariableNode variable) {
        this.variable = variable;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<decrement>");
        variable.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</decrement>");
    }

    @Override
    public String toString() {
        return variable + " = " + variable + " - " + 1;
    }
}
