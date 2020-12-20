package ast.variables;

import ast.statements.simple_statements.assignment.AssignmentNode;
import ast.statements.simple_statements.assignment.simple_assignment.SimpleAssignmentNode;
import ast.variables.types.TypeNode;
import ast.statements.StatementNode;

public class VariableDefinitionNode extends StatementNode {
    private final TypeNode type;
    private final AssignmentNode assignment;

    public VariableDefinitionNode(TypeNode type, AssignmentNode assignment) {
        this.type = type;
        this.assignment = assignment;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<variable definition>");
        type.printNode(tabCount + 1);
        assignment.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</variable definition>");
    }

    @Override
    public String toString() {
        SimpleAssignmentNode assignmentNode = (SimpleAssignmentNode) assignment;
        return assignmentNode.getVariable() +
                ": " +
                type +
                " = " +
                assignmentNode.getAssignable();
    }

    public String getVariable() {
        return ((SimpleAssignmentNode) assignment).getVariable();
    }
}
