package ast.statements.simple_statements.assignment.compound_assignment;

import ast.statements.simple_statements.assignment.AssignableNode;
import ast.statements.simple_statements.assignment.AssignmentNode;
import ast.variables.VariableNode;

public class DivisiveAssignmentNode extends AssignmentNode {
    VariableNode variable;
    AssignableNode assignable;

    public DivisiveAssignmentNode(VariableNode variable, AssignableNode assignable) {
        this.variable = variable;
        this.assignable = assignable;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<divisive assignment>");
        variable.printNode(tabCount + 1);
        assignable.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</divisive assignment>");
    }

    @Override
    public String toString() {
        return variable + " /= " + assignable;
    }
}