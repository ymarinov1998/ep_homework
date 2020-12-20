package ast.statements.compound_statements.if_statement;

import ast.code_structure.BlockNode;
import ast.expressions.ExpressionNode;

public class ElseIfNode extends ExpressionNode {
    private final ExpressionNode expression;
    private final BlockNode block;

    public ElseIfNode(ExpressionNode expression, BlockNode block) {
        this.expression = expression;
        this.block = block;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<elseif>");
        expression.printNode(tabCount + 1);
        block.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</elseif>");
    }

    @Override
    public String toString() {
        return "elif " + expression + ":" + block.toStringIndented();
    }
}
