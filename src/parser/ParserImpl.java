package parser;

import ast.code_structure.BlockNode;
import ast.code_structure.FunctionDefinitionNode;
import ast.code_structure.ProgramNode;
import ast.expressions.ConditionalExpressionNode;
import ast.expressions.ExpressionNode;
import ast.expressions.FunctionCallNode;
import ast.code_structure.parameters.ActualParametersNode;
import ast.code_structure.parameters.FormalParameterNode;
import ast.expressions.relational_operators.*;
import ast.statements.compound_statements.for_statement.*;
import ast.statements.compound_statements.switch_statement.CaseNode;
import ast.statements.compound_statements.switch_statement.DefaultNode;
import ast.statements.compound_statements.switch_statement.SwitchNode;
import ast.statements.simple_statements.assignment.assignables.ArrayInitializationNode;
import ast.statements.simple_statements.assignment.compound_assignment.AdditiveAssignmentNode;
import ast.statements.simple_statements.assignment.compound_assignment.DivisiveAssignmentNode;
import ast.statements.simple_statements.assignment.compound_assignment.MultiplicativeAssignmentNode;
import ast.statements.simple_statements.assignment.compound_assignment.SubtractiveAssignmentNode;
import ast.statements.simple_statements.assignment.simple_assignment.SimpleAssignmentNode;
import ast.statements.simple_statements.return_statement.ReturnNode;
import ast.variables.*;
import ast.expressions.additive_operators.AdditionNode;
import ast.expressions.additive_operators.DisjunctionNode;
import ast.expressions.additive_operators.SubtractionNode;
import ast.variables.types.TypeNode;
import ast.variables.types.literals.BoolLiteralNode;
import ast.variables.types.literals.IntLiteralNode;
import ast.expressions.multiplicative_operators.DivisionNode;
import ast.expressions.multiplicative_operators.MultiplicationNode;
import ast.expressions.unary_operators.InversionNode;
import ast.expressions.unary_operators.MinusNode;
import ast.statements.StatementNode;
import ast.statements.compound_statements.if_statement.ElseIfNode;
import ast.statements.compound_statements.if_statement.ElseNode;
import ast.statements.compound_statements.if_statement.IfNode;
import ast.statements.compound_statements.while_statement.WhileNode;
import ast.statements.simple_statements.assignment.*;
import ast.statements.simple_statements.unary_operations.DecrementNode;
import ast.statements.simple_statements.unary_operations.IncrementNode;
import exceptions.SyntaxException;
import lexer.Lexer;
import lexer.LexerImpl;
import scanner.InputScanner;
import scanner.InputScannerImpl;
import token.Token;
import token.TokenType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ParserImpl {

    private final Lexer<TokenType> lexer;
    private Token<TokenType> currentToken;
    private ProgramNode programNode;

    public ParserImpl(Lexer<TokenType> lexer) {
        this.lexer = lexer;
        currentToken = lexer.getNextToken();
    }

    private void accept(TokenType tokenType) {
        if (!currentToken.getTokenType().equals(tokenType)) {
            throw new SyntaxException(
                    String.format("Token mismatch! Expected '%s', found '%s'",
                            tokenType.value,
                            currentToken.getTokenType().value),
                    currentToken);
        }
        currentToken = lexer.getNextToken();
    }

    private void program() {
        List<FunctionDefinitionNode> functionDefinitions = new ArrayList<>();
        while (currentToken != null && currentToken.getTokenType().equals(TokenType.FUNCTION)) {
            functionDefinitions.add(functionDefinition());
        }
        programNode = new ProgramNode(functionDefinitions);
    }

    private FunctionDefinitionNode functionDefinition() {
        FunctionDefinitionNode functionDefinition = new FunctionDefinitionNode();
        accept(TokenType.FUNCTION);
        functionDefinition.setFunctionName(currentToken.getTokenText());
        accept(TokenType.IDENTIFIER);
        accept(TokenType.LPAREN);
        if (TokenType.isPrimitiveType(currentToken.getTokenType())) {
            functionDefinition.setFormalParameters(formalParameters());
        }
        accept(TokenType.RPAREN);
        if (currentToken.getTokenType().equals(TokenType.RETURNS)) {
            accept(TokenType.RETURNS);
            functionDefinition.setReturnTypeNode(type());
        }
        functionDefinition.setBlockNode(block());
        return functionDefinition;
    }

    private FormalParameterNode formalParameters() {
        List<TypedVariableNode> parameters = new ArrayList<>();
        parameters.add(new TypedVariableNode(type(), currentToken.getTokenText()));
        accept(TokenType.IDENTIFIER);
        while (currentToken.getTokenType().equals(TokenType.COMMA)) {
            accept(TokenType.COMMA);
            parameters.add(new TypedVariableNode(type(), currentToken.getTokenText()));
            accept(TokenType.IDENTIFIER);
        }
        return new FormalParameterNode(parameters);
    }

    private TypeNode type() {
        if (TokenType.isPrimitiveType(currentToken.getTokenType())) {
            String type = currentToken.getTokenText();
            boolean isArray = false;
            accept(currentToken.getTokenType());
            if (currentToken.getTokenType().equals(TokenType.LSQUARE)) {
                accept(TokenType.LSQUARE);
                accept(TokenType.RSQUARE);
                isArray = true;
            }
            return new TypeNode(type, isArray);
        } else {
            throw new SyntaxException(
                    String.format("Expected int / bool, found '%s'", currentToken.getTokenText()), currentToken);
        }
    }

    private BlockNode block() {
        List<StatementNode> statements = new ArrayList<>();
        accept(TokenType.LBRACKET);
        while (TokenType.isStatementTerminal(currentToken.getTokenType())) {
            statements.add(statement());
        }
        accept(TokenType.RBRACKET);
        return new BlockNode(statements);
    }

    private StatementNode statement() {
        StatementNode statement;
        if (TokenType.isCompoundStatementTerminal(currentToken.getTokenType())) {
            statement = compoundStatement();
        } else {
            statement = simpleStatement();
            accept(TokenType.SEMICOLON);
        }
        return statement;
    }

    private StatementNode compoundStatement() {
        return switch (currentToken.getTokenType()) {
            case IF -> ifStatement();
            case WHILE -> whileStatement();
            case FOR -> forStatement();
            case SWITCH -> switchStatement();
            default -> throw new SyntaxException(
                    String.format("Expected compound statement, found '%s'", currentToken.getTokenText()), currentToken);
        };
    }

    private StatementNode ifStatement() {
        IfNode ifNode = new IfNode();
        accept(TokenType.IF);
        ifNode.setExpression(expression());
        ifNode.setBlock(block());
        while (currentToken.getTokenType().equals(TokenType.ELSEIF)) {
            List<ElseIfNode> elseIfStatements = new ArrayList<>();
            accept(TokenType.ELSEIF);
            elseIfStatements.add(new ElseIfNode(expression(), block()));
            ifNode.setElseIfStatements(elseIfStatements);
        }
        if (currentToken.getTokenType().equals(TokenType.ELSE)) {
            accept(TokenType.ELSE);
            ifNode.setElseStatement(new ElseNode(block()));
        }
        return ifNode;
    }

    private StatementNode whileStatement() {
        accept(TokenType.WHILE);
        return new WhileNode(expression(), block());
    }

    private StatementNode forStatement() {
        accept(TokenType.FOR);
        if (currentToken.getTokenType().equals(TokenType.LPAREN)) {
            accept(TokenType.LPAREN);
            var forInitNode = new ForInitNode(variableDefinition());
            accept(TokenType.SEMICOLON);
            var forConditionNode = new ForConditionNode(expression());
            accept(TokenType.SEMICOLON);
            var forLoopNode = new ForLoopNode(simpleStatement());
            accept(TokenType.RPAREN);
            return new ForNode(forInitNode, forConditionNode, forLoopNode, block());
        } else {
            TypedVariableNode typedVariableNode = new TypedVariableNode(type(), currentToken.getTokenText());
            accept(TokenType.IDENTIFIER);
            accept(TokenType.IN);
            SimpleVariableNode simpleVariableNode = new SimpleVariableNode(currentToken.getTokenText());
            accept(TokenType.IDENTIFIER);
            return new ForeachNode(typedVariableNode, simpleVariableNode, block());
        }
    }

    private StatementNode switchStatement() {
        accept(TokenType.SWITCH);
        accept(TokenType.LPAREN);
        var variable = variable();
        accept(TokenType.RPAREN);
        accept(TokenType.LBRACKET);
        List<CaseNode> cases = new ArrayList<>();
        while (currentToken.getTokenType().equals(TokenType.CASE)) {
            accept(TokenType.CASE);
            cases.add(new CaseNode(expression(), block()));
        }
        accept(TokenType.DEFAULT);
        var defaultNode = new DefaultNode(block());
        accept(TokenType.RBRACKET);
        return new SwitchNode(variable, cases, defaultNode);
    }

    private StatementNode simpleStatement() {
        StatementNode statement = null;
        switch (currentToken.getTokenType()) {
            case INT, BOOL -> statement = variableDefinition();
            case RETURN -> statement = returnStatement();
            case IDENTIFIER -> {
                String name = currentToken.getTokenText();
                VariableNode variable = variable();
                switch (currentToken.getTokenType()) {
                    case BECOMES -> statement = new SimpleAssignmentNode(variable, simpleAssignment());
                    case ASSIGNMENT_ADD -> statement = new AdditiveAssignmentNode(variable, assignment());
                    case ASSIGNMENT_DIFF -> statement = new SubtractiveAssignmentNode(variable, assignment());
                    case ASSIGNMENT_PROD -> statement = new MultiplicativeAssignmentNode(variable, assignment());
                    case ASSIGNMENT_QUOT -> statement = new DivisiveAssignmentNode(variable, assignment());
                    case INCREMENT -> {
                        statement = new IncrementNode(variable);
                        accept(currentToken.getTokenType());
                    }
                    case DECREMENT -> {
                        statement = new DecrementNode(variable);
                        accept(currentToken.getTokenType());
                    }
                    case LPAREN -> {
                        var functionCallNode = functionCall();
                        functionCallNode.setFunctionName(name);
                        statement = functionCallNode;
                    }
                }
            }
            default -> throw new SyntaxException(
                    String.format("Expected simple statement, found '%s'", currentToken.getTokenText()), currentToken);
        }
        return statement;
    }

    private StatementNode variableDefinition() {
        return new VariableDefinitionNode(type(), new SimpleAssignmentNode(variable(), simpleAssignment()));
    }

    private StatementNode returnStatement() {
        accept(TokenType.RETURN);
        return new ReturnNode(expression());
    }

    private VariableNode variable() {
        String name = currentToken.getTokenText();
        accept(TokenType.IDENTIFIER);
        if (currentToken.getTokenType().equals(TokenType.LSQUARE)) {
            accept(TokenType.LSQUARE);
            IndexedVariableNode indexedVariable = new IndexedVariableNode(name, expression());
            accept(TokenType.RSQUARE);
            return indexedVariable;
        }
        return new SimpleVariableNode(name);
    }

    private AssignableNode assignment() {
        if (TokenType.isAssignmentTerminal(currentToken.getTokenType())) {
            accept(currentToken.getTokenType());
            return expression();
        } else {
            throw new SyntaxException(
                    String.format("Expected compound assignment, found '%s'", currentToken.getTokenText()), currentToken);
        }
    }

    private AssignableNode simpleAssignment() {
        if (currentToken.getTokenType().equals(TokenType.BECOMES)) {
            accept(TokenType.BECOMES);
            return assignable();
        } else {
            throw new SyntaxException(
                    String.format("Expected assignment, found '%s'", currentToken.getTokenText()), currentToken);
        }
    }

    private FunctionCallNode functionCall() {
        FunctionCallNode functionCall = new FunctionCallNode();
        accept(TokenType.LPAREN);
        if (TokenType.isFactorTerminal(currentToken.getTokenType())) {
            functionCall.setActualParameters(actualParameters());
        }
        accept(TokenType.RPAREN);
        return functionCall;
    }

    private ActualParametersNode actualParameters() {
        List<ExpressionNode> actualParameters = new ArrayList<>();
        actualParameters.add(expression());
        while (currentToken.getTokenType().equals(TokenType.COMMA)) {
            accept(TokenType.COMMA);
            actualParameters.add(expression());
        }
        return new ActualParametersNode(actualParameters);
    }

    private AssignableNode assignable() {
        if (TokenType.isFactorTerminal(currentToken.getTokenType())) {
            return expression();
        } else if (TokenType.isPrimitiveType(currentToken.getTokenType())) {
            return arrayInitialization();
        } else {
            throw new SyntaxException(
                    String.format("Expected assignable, found '%s'", currentToken.getTokenText()), currentToken);
        }
    }

    private ArrayInitializationNode arrayInitialization() {
        if (TokenType.isPrimitiveType(currentToken.getTokenType())) {
            var type = currentToken.getTokenText();
            accept(currentToken.getTokenType());
            accept(TokenType.LSQUARE);
            ArrayInitializationNode arrayInitialization = new ArrayInitializationNode(type, expression());
            accept(TokenType.RSQUARE);
            return arrayInitialization;
        } else {
            throw new SyntaxException(
                    String.format("Expected array initialization, found '%s'", currentToken.getTokenText()), currentToken);
        }
    }

    private ExpressionNode expression() {
        ExpressionNode expressionNode = disjunction();
        var currentTokenType = currentToken.getTokenType();
        if (currentTokenType.equals(TokenType.IF)) {
            accept(TokenType.IF);
            ExpressionNode conditionNode = disjunction();
            accept(TokenType.ELSE);
            return new ConditionalExpressionNode(expressionNode, conditionNode, disjunction());
        }
        return expressionNode;
    }

    private ExpressionNode disjunction() {
        ExpressionNode expressionNode = conjunction();
        var currentTokenType = currentToken.getTokenType();
        if (currentTokenType.equals(TokenType.OR)) {
            accept(currentTokenType);
            return new DisjunctionNode(expressionNode, disjunction());
        }
        return expressionNode;
    }

    private ExpressionNode conjunction() {
        ExpressionNode expressionNode = equality();
        var currentTokenType = currentToken.getTokenType();
        if (currentTokenType.equals(TokenType.AND)) {
            accept(currentTokenType);
            return new DisjunctionNode(expressionNode, conjunction());
        }
        return expressionNode;
    }

    private ExpressionNode equality() {
        ExpressionNode expressionNode = relation();
        var currentTokenType = currentToken.getTokenType();
        if (TokenType.isEqualityOperator(currentTokenType)) {
            accept(currentTokenType);
            switch (currentTokenType) {
                case EQUAL -> expressionNode = new EqualNode(expressionNode, equality());
                case NOTEQUAL -> expressionNode = new NotEqualNode(expressionNode, equality());
            }
        }
        return expressionNode;
    }

    private ExpressionNode relation() {
        ExpressionNode expressionNode = arithmeticOperation();
        var currentTokenType = currentToken.getTokenType();
        if (TokenType.isRelationalOperator(currentTokenType)) {
            accept(currentTokenType);
            switch (currentTokenType) {
                case LESS_THAN -> expressionNode = new LessThanNode(expressionNode, relation());
                case LESS_THAN_OR_EQUAL -> expressionNode = new LessThanOrEqualNode(expressionNode, relation());
                case GREATER_THAN -> expressionNode = new GreaterThanNode(expressionNode, relation());
                case GREATER_THAN_OR_EQUAL -> expressionNode = new GreaterThanOrEqualNode(expressionNode, relation());
            }
        }
        return expressionNode;
    }

    private ExpressionNode arithmeticOperation() {
        ExpressionNode expressionNode = term();
        var currentTokenType = currentToken.getTokenType();
        if (TokenType.isAdditiveOperator(currentTokenType)) {
            accept(currentTokenType);
            switch (currentTokenType) {
                case PLUS -> expressionNode = new AdditionNode(expressionNode, arithmeticOperation());
                case MINUS -> expressionNode = new SubtractionNode(expressionNode, arithmeticOperation());
            }
        }
        return expressionNode;
    }

    private ExpressionNode term() {
        ExpressionNode expressionNode = factor();
        var currentTokenType = currentToken.getTokenType();
        if (TokenType.isMultiplicativeOperator(currentTokenType)) {
            accept(currentTokenType);
            switch (currentTokenType) {
                case MUL -> expressionNode = new MultiplicationNode(expressionNode, term());
                case DIV -> expressionNode = new DivisionNode(expressionNode, term());
            }
        }
        return expressionNode;
    }

    private ExpressionNode factor() {
        switch (currentToken.getTokenType()) {
            case NOT:
                accept(TokenType.NOT);
                return new InversionNode(factorVariant());
            case MINUS:
                accept(TokenType.MINUS);
                return new MinusNode(factorVariant());
            default:
                return factorVariant();
        }
    }

    private ExpressionNode factorVariant() {
        ExpressionNode expressionNode;
        switch (currentToken.getTokenType()) {
            case IDENTIFIER -> {
                String name = currentToken.getTokenText();
                expressionNode = variable();
                if (currentToken.getTokenType().equals(TokenType.LPAREN)) {
                    var functionCallNode = functionCall();
                    functionCallNode.setFunctionName(name);
                    expressionNode = functionCallNode;
                }
            }
            case INT_LITERAL -> {
                expressionNode = new IntLiteralNode(currentToken.getTokenText());
                accept(TokenType.INT_LITERAL);
            }
            case TRUE, FALSE -> {
                expressionNode = new BoolLiteralNode(currentToken.getTokenText());
                accept(currentToken.getTokenType());
            }
            case LPAREN -> {
                accept(TokenType.LPAREN);
                expressionNode = expression();
                accept(TokenType.RPAREN);
            }
            default -> throw new SyntaxException(
                    String.format("Expected factor, found '%s'", currentToken.getTokenText()), currentToken);
        }
        return expressionNode;
    }

    public void tryParse() {
        program();
    }

    public void printTree() {
        programNode.printNode(0);
    }

    public static void main(String[] args) throws IOException {
        InputScanner scanner = new InputScannerImpl("resources/parser_test.txt");
        Lexer<TokenType> lexer = new LexerImpl(scanner);
        ParserImpl parser = new ParserImpl(lexer);
        parser.tryParse();
        parser.printTree();
    }
}


