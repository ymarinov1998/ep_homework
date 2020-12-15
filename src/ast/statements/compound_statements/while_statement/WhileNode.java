package ast.statements.compound_statements.while_statement;

import ast.code_structure.BlockNode;
import ast.expressions.ExpressionNode;
import ast.statements.StatementNode;

public class WhileNode extends StatementNode {
    private final ExpressionNode expression;
    private final BlockNode block;

    public WhileNode(ExpressionNode expression, BlockNode block) {
        this.expression = expression;
        this.block = block;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<while>");
        expression.printNode(tabCount + 1);
        block.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</while>");
    }
}
