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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (!statements.isEmpty())
            statements.forEach(statement -> sb.append("\n\t").append(statement));
        else
            sb.append("\n\t").append("pass");
        return sb.toString();
    }

    public String toStringIndented() {
        StringBuilder sb = new StringBuilder();
        if (!statements.isEmpty())
            statements.forEach(statement -> sb.append("\n\t\t").append(statement));
        else
            sb.append("\n\t").append("pass");
        return sb.toString();
    }
}
