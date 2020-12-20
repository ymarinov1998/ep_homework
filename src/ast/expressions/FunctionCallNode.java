package ast.expressions;

import ast.code_structure.parameters.ActualParametersNode;

public class FunctionCallNode extends ExpressionNode {
    private String functionName;
    private ActualParametersNode actualParameters;

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public void setActualParameters(ActualParametersNode actualParameters) {
        this.actualParameters = actualParameters;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<function call: " + functionName + ">");
        if (actualParameters != null)
            actualParameters.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</function call>");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(functionName).append("(");
        if (actualParameters != null) {
            sb.append(actualParameters);
        }
        sb.append(")");
        return sb.toString();
    }
}
