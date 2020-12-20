package ast.variables;

public class SimpleVariableNode extends VariableNode {
    private final String variableName;

    public SimpleVariableNode(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<variable: " + variableName + ">");
    }

    @Override
    public String toString() {
        return variableName;
    }
}
