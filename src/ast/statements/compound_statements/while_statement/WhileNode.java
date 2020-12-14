package ast.statements.compound_statements.while_statement;

import ast.code_structure.BlockNode;
import ast.expressions.ExpressionNode;
import ast.statements.StatementNode;

public class WhileNode extends StatementNode {
    private ExpressionNode expression;
    private BlockNode block;

    public WhileNode(ExpressionNode expression, BlockNode block) {
        this.expression = expression;
        this.block = block;
    }

    @Override
    public void printNode(int tabCount) {

    }
}
