package ast.statements.compound_statements.switch_statement;

import ast.code_structure.BlockNode;
import ast.Node;
import ast.expressions.ExpressionNode;

public class CaseNode implements Node {
    private final ExpressionNode expression;
    private final BlockNode block;

    public CaseNode(ExpressionNode expression, BlockNode block) {
        this.expression = expression;
        this.block = block;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<case>");
        expression.printNode(tabCount + 1);
        block.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</case>");
    }
}
