package ast.statements.compound_statements.if_statement;

import ast.Node;
import ast.code_structure.BlockNode;

public class ElseNode implements Node {
    private final BlockNode block;

    public ElseNode(BlockNode block) {
        this.block = block;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<else>");
        block.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</else>");
    }
}
