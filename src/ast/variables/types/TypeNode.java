package ast.variables.types;

import ast.Node;

public class TypeNode implements Node {
    private final String type;
    private final boolean isArray;

    public TypeNode(String type, boolean isArray) {
        this.type = type;
        this.isArray = isArray;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<type: " + type + (isArray ? "[]>" : ">"));
    }
}
