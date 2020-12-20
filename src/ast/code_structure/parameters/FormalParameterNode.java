package ast.code_structure.parameters;

import ast.Node;
import ast.variables.TypedVariableNode;

import java.util.List;

public class FormalParameterNode implements Node {
    List<TypedVariableNode> parameters;

    public FormalParameterNode(List<TypedVariableNode> parameters) {
        this.parameters = parameters;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<formal parameters>");
        parameters.forEach(parameter -> parameter.printNode(tabCount + 1));
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</formal parameters>");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        int numParameters = parameters.size();
        for (int i = 0; i < numParameters; i++) {
            sb.append(parameters.get(i));
            if (i < numParameters - 1) {
                sb.append((", "));
            }
        }
        sb.append(")");
        return sb.toString();
    }
}
