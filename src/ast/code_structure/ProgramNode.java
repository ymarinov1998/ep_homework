package ast.code_structure;

import ast.Node;

import java.util.List;

public class ProgramNode implements Node {

    private final List<FunctionDefinitionNode> functionDefinitions;

    public ProgramNode(List<FunctionDefinitionNode> functionDefinitions) {
        this.functionDefinitions = functionDefinitions;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<program>");
        functionDefinitions.forEach(function -> function.printNode(tabCount + 1));
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</program>");
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("from typing import *\n\n");
        functionDefinitions.forEach(sb::append);
        return sb.toString();
    }

}
