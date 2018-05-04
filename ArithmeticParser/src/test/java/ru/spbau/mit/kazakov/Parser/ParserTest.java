package ru.spbau.mit.kazakov.Parser;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class ParserTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }

    @Test
    public void printDerivationTreeSimple() throws ParsingException {
        Parser.printDerivationTree("13" + '\0');
        assertEquals("  13  \n", outContent.toString());
    }

    @Test
    public void printDerivationTreeBrackets() throws ParsingException {
        Parser.printDerivationTree("((((((13))))))" + '\0');
        assertEquals("  13  \n", outContent.toString());
    }

    @Test
    public void printDerivationTreePlusSimple() throws ParsingException {
        Parser.printDerivationTree("43 + 2431" + '\0');
        assertEquals("        ADDITION        \n" +
                "      ┌─────┴─────┐     \n" +
                "     43         2431    \n", outContent.toString());
    }

    @Test
    public void printDerivationTreeExprOperators() throws ParsingException {
        Parser.printDerivationTree("1 - 2 - 3 - (5 - 6)" + '\0');
        assertEquals("                                                           SUBTRACTION                                                          \n" +
                "                                ┌───────────────────────────────┴───────────────────────────────┐                               \n" +
                "                           SUBTRACTION                                                     SUBTRACTION                          \n" +
                "                ┌───────────────┴───────────────┐                               ┌───────────────┴───────────────┐               \n" +
                "           SUBTRACTION                          3                               5                               6               \n" +
                "        ┌───────┴───────┐                                                                                                       \n" +
                "        1               2                                                                                                       \n", outContent.toString());
    }

    @Test
    public void printDerivationTreePowOperators() throws ParsingException {
        Parser.printDerivationTree("(42 ^ (24 - 156) + 123)" + '\0');
        assertEquals("                                                                    ADDITION                                                                    \n" +
                "                                    ┌───────────────────────────────────┴───────────────────────────────────┐                                   \n" +
                "                             EXPONENTIATION                                                                123                                  \n" +
                "                  ┌─────────────────┴─────────────────┐                                                                                         \n" +
                "                 42                              SUBTRACTION                                                                                    \n" +
                "                                             ┌────────┴────────┐                                                                                \n" +
                "                                            24                156                                                                               \n", outContent.toString());
    }

    @Test
    public void printDerivationTreeTermOperator() throws ParsingException {
        Parser.printDerivationTree("42 ^ 24 ^ (1 - 2) - 156 * 123 * 32" + '\0');
        assertEquals("                                                                                                                                           SUBTRACTION                                                                                                                                          \n" +
                "                                                                        ┌───────────────────────────────────────────────────────────────────────┴───────────────────────────────────────────────────────────────────────┐                                                                       \n" +
                "                                                                 EXPONENTIATION                                                                                                                                  MULTIPLICATION                                                                 \n" +
                "                                    ┌───────────────────────────────────┴───────────────────────────────────┐                                                                       ┌───────────────────────────────────┴───────────────────────────────────┐                                   \n" +
                "                                   42                                                                EXPONENTIATION                                                          MULTIPLICATION                                                                32                                   \n" +
                "                                                                                          ┌─────────────────┴─────────────────┐                                   ┌─────────────────┴─────────────────┐                                                                                         \n" +
                "                                                                                         24                              SUBTRACTION                             156                                 123                                                                                        \n" +
                "                                                                                                                     ┌────────┴────────┐                                                                                                                                                        \n" +
                "                                                                                                                     1                 2                                                                                                                                                        \n", outContent.toString());
    }

    @Test
    public void printDerivationTreeThrowsExceptionBracketsMismatch() throws ParsingException {
        exception.expect(ParsingException.class);
        exception.expectMessage("Failed to parse at position 14.");
        Parser.printDerivationTree("((((((13)))))" + '\0');
    }

    @Test
    public void printDerivationTreeThrowsExceptionUnexpectedNum() throws ParsingException {
        exception.expect(ParsingException.class);
        exception.expectMessage("Failed to parse at position 18.");
        Parser.printDerivationTree("(13 - 4) * 5 - 9 3" + '\0');
    }

    @Test
    public void printDerivationTreeThrowsExceptionUnexpectedOperator() throws ParsingException {
        exception.expect(ParsingException.class);
        exception.expectMessage("Failed to parse at position 10.");
        Parser.printDerivationTree("54 - 6 * - 3" + '\0');
    }
}