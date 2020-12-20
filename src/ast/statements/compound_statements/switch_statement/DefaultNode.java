package ast.statements.compound_statements.switch_statement;

import ast.code_structure.BlockNode;
import ast.Node;

public class DefaultNode implements Node {
    private final BlockNode block;

    public DefaultNode(BlockNode block) {
        this.block = block;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<default>");
        block.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</default>");
    }

    @Override
    public String toString() {
        return block.toStringIndented();
    }

}
