package ru.spbau.mit.kazakov.Parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class TreePrinterTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void printSingleNode() {
        TreePrinter.print(getSingleNode("t"));
        assertEquals("   t  \n", outContent.toString());
    }

    @Test
    public void printOneChildNode() {
        TreePrinter.print(new Node(getSingleNode("ch"), null, "p"));
        assertEquals("      p     \n" + "   ┌──┘     \n" + "  ch        \n", outContent.toString());
    }

    @Test
    public void printTwoChildNode() {
        TreePrinter.print(getTwoChildNode("p", "ch1", "ch2"));
        assertEquals("        p       \n" + "    ┌───┴───┐   \n" + "   ch1     ch2  \n", outContent.toString());
    }

    @Test
    public void printCompleteTree() {
        TreePrinter.print(new Node(getTwoChildNode("1l", "2ll", "2lr"),
                getTwoChildNode("1r", "2rl", "2rr"), "p"));
        assertEquals("                p               \n" +
                "        ┌───────┴───────┐       \n" +
                "       1l              1r       \n" +
                "    ┌───┴───┐       ┌───┴───┐   \n" +
                "   2ll     2lr     2rl     2rr  \n", outContent.toString());
    }

    @Test
    public void printNotCompleteTree() {
        TreePrinter.print(new Node(getTwoChildNode("1l", "2ll", "2lr"),
                null, "p"));
        assertEquals("                p               \n" +
                "        ┌───────┘               \n" +
                "       1l                       \n" +
                "    ┌───┴───┐                   \n" +
                "   2ll     2lr                  \n", outContent.toString());
    }

    private Node getSingleNode(String text) {
        return new Node(null, null, text);
    }

    private Node getTwoChildNode(String parent, String leftChild, String rightChild) {
        return new Node(getSingleNode(leftChild), getSingleNode(rightChild), parent);
    }

    private static class Node implements TreePrinter.PrintableNode {
        private Node left;
        private Node right;
        private String text;

        public Node(Node left, Node right, String text) {
            this.left = left;
            this.right = right;
            this.text = text;
        }

        @Override
        public TreePrinter.PrintableNode getLeftChild() {
            return left;
        }

        @Override
        public TreePrinter.PrintableNode getRightChild() {
            return right;
        }

        @Override
        public String getText() {
            return text;
        }
    }
}