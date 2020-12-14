package ast.statements.compound_statements.if_statement;

import ast.code_structure.BlockNode;
import ast.expressions.ExpressionNode;

public class ElseIfNode extends ExpressionNode {
    private ExpressionNode expression;
    private BlockNode block;

    public ElseIfNode(ExpressionNode expression, BlockNode block) {
        this.expression = expression;
        this.block = block;
    }

    @Override
    public void printNode(int tabCount) {

    }
}
