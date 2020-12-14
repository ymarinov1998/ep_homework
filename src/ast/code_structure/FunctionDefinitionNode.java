package ast.code_structure;

import ast.Node;
import ast.code_structure.parameters.FormalParameterNode;
import ast.variables.types.TypeNode;

public class FunctionDefinitionNode implements Node {

    private String functionName;
    private FormalParameterNode formalParameters;
    private TypeNode returnTypeNode;
    private BlockNode blockNode;

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public void setFormalParameters(FormalParameterNode formalParameters) {
        this.formalParameters = formalParameters;
    }

    public void setReturnTypeNode(TypeNode returnTypeNode) {
        this.returnTypeNode = returnTypeNode;
    }

    public void setBlockNode(BlockNode blockNode) {
        this.blockNode = blockNode;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<function: " + functionName + ">");
        if (formalParameters != null)
            formalParameters.printNode(tabCount + 1);
        if (returnTypeNode != null)
            returnTypeNode.printNode(tabCount + 1);
        blockNode.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</function>");
    }
}
