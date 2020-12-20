package ast.variables;

import ast.expressions.ExpressionNode;

public class IndexedVariableNode extends VariableNode {
    private final String variableName;
    private final ExpressionNode index;

    public IndexedVariableNode(String variableName, ExpressionNode index) {
        this.variableName = variableName;
        this.index = index;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<indexed variable: " + variableName + ">");
        index.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</indexed variable>");
    }

    @Override
    public String toString() {
        return variableName + "[" + index + "]";
    }
}
