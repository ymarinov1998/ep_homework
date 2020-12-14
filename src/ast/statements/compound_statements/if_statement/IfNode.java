package ast.statements.compound_statements.if_statement;

import ast.code_structure.BlockNode;
import ast.expressions.ExpressionNode;
import ast.statements.StatementNode;

import java.util.List;

public class IfNode extends StatementNode {
    private ExpressionNode expression;
    private BlockNode block;
    private List<ElseIfNode> elseIfStatements;
    private ElseNode elseStatement;

    public void setExpression(ExpressionNode expression) {
        this.expression = expression;
    }

    public void setBlock(BlockNode block) {
        this.block = block;
    }

    public void setElseIfStatements(List<ElseIfNode> elseIfStatements) {
        this.elseIfStatements = elseIfStatements;
    }

    public void setElseStatement(ElseNode elseStatement) {
        this.elseStatement = elseStatement;
    }

    @Override
    public void printNode(int tabCount) {

    }
}
