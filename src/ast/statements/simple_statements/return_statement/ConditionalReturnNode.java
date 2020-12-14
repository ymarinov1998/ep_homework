package ast.statements.simple_statements.return_statement;

import ast.expressions.ExpressionNode;
import ast.statements.StatementNode;

public class ConditionalReturnNode extends StatementNode {
    private final ExpressionNode ifCondition, ifReturnValue, elseReturnValue;

    public ConditionalReturnNode(ExpressionNode ifCondition, ExpressionNode ifReturnValue, ExpressionNode elseReturnValue) {
        this.ifCondition = ifCondition;
        this.ifReturnValue = ifReturnValue;
        this.elseReturnValue = elseReturnValue;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<conditional return>");
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount + 1)), "<if>");
        ifCondition.printNode(tabCount + 2);
        ifReturnValue.printNode(tabCount + 2);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount + 1)), "</if>");
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount + 1)), "<else>");
        elseReturnValue.printNode(tabCount + 2);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount + 1)), "</else>");
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</conditional return>");
    }
}
