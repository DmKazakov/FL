package ru.spbau.mit.kazakov.Parser;

import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.kazakov.Parser.ArithmeticUtility.*;
import ru.spbau.mit.kazakov.Parser.TreePrinter.PrintableNode;

import java.io.File;
import java.io.IOException;

import static ru.spbau.mit.kazakov.Parser.ArithmeticUtility.charToOperator;
import static ru.spbau.mit.kazakov.Parser.ArithmeticUtility.isOperator;

public class Parser {
    private static String expression;
    private static int currentPosition;

    public static void main(@NotNull String[] args) {
        if (args.length == 0) {
            System.out.println("No file specified.");
            return;
        }
        try {
            String expr = FileUtils.readFileToString(new File(args[0])).trim();
            printDerivationTree(expr + "\0");
        } catch (IOException exception) {
            System.out.println("Unable to read from file " + args[0]);
        } catch (ParsingException exception) {
            System.out.println(exception.getMessage());
        }
    }

    private static char currentChar() {
        return expression.charAt(currentPosition);
    }

    private static char moveCurrentChar() {
        char prevChar = currentChar();
        currentPosition++;
        while (currentChar() == ' ') {
            currentPosition++;
        }
        return prevChar;
    }

    private static char skipWhiteSpaceChars() {
        char prevChar = currentChar();
        while (currentChar() == ' ') {
            currentPosition++;
        }
        return prevChar;
    }

    public static void printDerivationTree(String expr) throws ParsingException {
        currentPosition = 0;
        expression = expr;
        Node root = parseExpression();
        if (currentPosition != expr.length() - 1) {
            throw new ParsingException("Failed to parse at position " + (currentPosition + 1) + ".");
        }
        TreePrinter.print(root);
    }

    @NotNull
    private static Node parseExpression() throws ParsingException {
        Node result = parseTerm();

        while (isOperator(currentChar()) && charToOperator(currentChar()).isExprOperator()) {
            result = new Node(charToOperator(moveCurrentChar()), result, parseTerm());
        }

        return result;
    }

    @NotNull
    private static Node parseTerm() throws ParsingException {
        Node result = parsePower();

        while (isOperator(currentChar()) && charToOperator(currentChar()).isTermOperator()) {
            result = new Node(charToOperator(moveCurrentChar()), result, parsePower());
        }

        return result;
    }

    @NotNull
    private static Node parsePower() throws ParsingException {
        Node result = parseAtom();

        if (isOperator(currentChar()) && charToOperator(currentChar()).isPowerOperator()) {
            result = new Node(charToOperator(moveCurrentChar()), result, parsePower());
        }

        return result;
    }

    @NotNull
    private static Node parseAtom() throws ParsingException {
        if (currentChar() == '(') {
            moveCurrentChar();
            Node result = parseExpression();
            if (currentChar() != ')') {
                throw new ParsingException("Failed to parse at position " + (currentPosition + 1) + ".");
            }
            moveCurrentChar();
            return result;
        } else {
            return parseNum();
        }
    }

    @NotNull
    private static Node parseNum() throws ParsingException {
        StringBuilder num = new StringBuilder();

        if (!Character.isDigit(currentChar())) {
            throw new ParsingException("Failed to parse at position " + (currentPosition + 1) + ".");
        }
        while (Character.isDigit(currentChar())) {
            num.append(currentChar());
            currentPosition++;
        }
        skipWhiteSpaceChars();

        return new Node(new Num(num.toString()), null, null);
    }

    private static class Node implements PrintableNode {
        private Token token;
        private Node leftOperand;
        private Node rightOperand;

        public Node(@NotNull Token token, Node leftOperand, Node rightOperand) {
            this.leftOperand = leftOperand;
            this.rightOperand = rightOperand;
            this.token = token;
        }

        @Override
        public PrintableNode getLeftChild() {
            return leftOperand;
        }

        @Override
        public PrintableNode getRightChild() {
            return rightOperand;
        }

        @Override
        public String getText() {
            return token.toString();
        }
    }
}
