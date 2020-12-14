package ast.statements.compound_statements.for_statement;

import ast.code_structure.BlockNode;
import ast.statements.StatementNode;

public class ForNode extends StatementNode {
    private final ForInitNode forInit;
    private final ForConditionNode forCondition;
    private final ForLoopNode forLoop;
    private final BlockNode block;

    public ForNode(ForInitNode forInit, ForConditionNode forCondition, ForLoopNode forLoop, BlockNode block) {
        this.forInit = forInit;
        this.forCondition = forCondition;
        this.forLoop = forLoop;
        this.block = block;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<for>");
        forInit.printNode(tabCount + 1);
        forCondition.printNode(tabCount + 1);
        forLoop.printNode(tabCount + 1);
        block.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</for>");
    }
}
