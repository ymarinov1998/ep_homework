package ast.statements.compound_statements.switch_statement;

import ast.statements.StatementNode;
import ast.variables.VariableNode;

import java.util.List;

public class SwitchNode extends StatementNode {

    private final VariableNode variable;
    private final List<CaseNode> cases;
    private final DefaultNode defaultStatement;

    public SwitchNode(VariableNode variable, List<CaseNode> cases, DefaultNode defaultStatement) {
        this.variable = variable;
        this.cases = cases;
        this.defaultStatement = defaultStatement;
    }

    @Override
    public void printNode(int tabCount) {
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "<switch>");
        variable.printNode(tabCount + 1);
        cases.forEach(caseNode -> caseNode.printNode(tabCount + 1));
        defaultStatement.printNode(tabCount + 1);
        System.out.printf("%s%s%n", "\t".repeat(Math.max(0, tabCount)), "</switch>");
    }
}
