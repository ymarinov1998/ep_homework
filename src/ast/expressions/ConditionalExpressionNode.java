package ast.expressions;

public class ConditionalExpressionNode extends ExpressionNode {
    private final ExpressionNode ifExpr;
    private final ExpressionNode condition;
    private final ExpressionNode elseExpr;

    public ConditionalExpressionNode(ExpressionNode ifExpr, ExpressionNode condition, ExpressionNode elseExpr) {
        this.ifExpr = ifExpr;
        this.condition = condition;
        this.elseExpr = elseExpr;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<conditional expression>");
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount + 1)), "<condition>");
        condition.printNode(tabCount + 2);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount + 1)), "</condition>");
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount + 1)), "<if expression>");
        ifExpr.printNode(tabCount + 2);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount + 1)), "</if expression>");
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount + 1)), "<else expression>");
        elseExpr.printNode(tabCount + 2);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount + 1)), "</else expression>");
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</conditional expression>");
    }
}