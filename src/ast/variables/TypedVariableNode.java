package ast.variables;

import ast.Node;
import ast.variables.types.TypeNode;

public class TypedVariableNode implements Node {
    private final TypeNode variableType;
    private final String variableName;

    public TypedVariableNode(TypeNode variableType, String variableName) {
        this.variableType = variableType;
        this.variableName = variableName;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<typed variable>");
        variableType.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount + 1)), "<identifier: " + variableName + ">");
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</typed variable>");
    }

    @Override
    public String toString() {
        return variableName + ": " + variableType;
    }

    public String getVariableName() {
        return variableName;
    }
}
