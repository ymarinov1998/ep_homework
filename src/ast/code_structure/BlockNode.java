package ast.code_structure;

import ast.Node;
import ast.statements.StatementNode;

import java.util.List;

public class BlockNode implements Node {

    List<StatementNode> statements;

    public BlockNode(List<StatementNode> statements) {
        this.statements = statements;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<block>");
        statements.forEach(statement -> statement.printNode(tabCount + 1));
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</block>");
    }
}
