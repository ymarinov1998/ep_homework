package ast.code_structure.parameters;

import ast.Node;
import ast.expressions.ExpressionNode;

import java.util.List;

public class ActualParametersNode implements Node {

    private final List<ExpressionNode> actualParameters;

    public ActualParametersNode(List<ExpressionNode> actualParameters) {
        this.actualParameters = actualParameters;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<actual parameters>");
        actualParameters.forEach(parameter -> parameter.printNode(tabCount + 1));
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</actual parameters>");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int numParameters = actualParameters.size();
        for (int i = 0; i < numParameters; i++) {
            sb.append(actualParameters.get(i));
            if (i < numParameters - 1) {
                sb.append((", "));
            }
        }
        return sb.toString();
    }
}
