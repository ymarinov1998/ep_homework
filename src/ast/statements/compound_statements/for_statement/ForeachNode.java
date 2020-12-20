package ast.statements.compound_statements.for_statement;

import ast.code_structure.BlockNode;
import ast.variables.TypedVariableNode;
import ast.statements.StatementNode;
import ast.variables.SimpleVariableNode;

public class ForeachNode extends StatementNode {
    private final TypedVariableNode typedVariable;
    private final SimpleVariableNode simpleVariable;
    private final BlockNode block;

    public ForeachNode(TypedVariableNode typedVariable, SimpleVariableNode simpleVariable, BlockNode block) {
        this.typedVariable = typedVariable;
        this.simpleVariable = simpleVariable;
        this.block = block;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<for each>");
        typedVariable.printNode(tabCount + 1);
        simpleVariable.printNode(tabCount + 1);
        block.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</for each>");
    }

    @Override
    public String toString() {
        return "for " + typedVariable.getVariableName() + " in " + simpleVariable + block.toStringIndented();
    }
}
