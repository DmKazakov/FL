package ru.spbau.mit.kazakov.Lexer;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import ru.spbau.mit.kazakov.Lexer.Lexeme.ExtendedLexemePosition;
import ru.spbau.mit.kazakov.Lexer.Lexeme.LexemePosition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static ru.spbau.mit.kazakov.Lexer.LexerUtils.Lexeme.EOF;

public class LexerTest {
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void testConstructor() {
        new Lexer("sth");
    }

    @Test
    public void testGetLexemesNumSimple() throws LexerException {
        Lexer lexer = new Lexer("4321");
        List<LexemePosition> expected = new ArrayList<>();
        expected.add(new ExtendedLexemePosition<>(1, 1, 4, LexerUtils.Lexeme.NUM, 4321.0));
        expected.add(new LexemePosition(1, 5, 5, EOF));
        assertEquals(expected, lexer.getLexemes());
    }

    @Test
    public void testGetLexemesNumStartDot() throws LexerException {
        Lexer lexer = new Lexer(".4321");
        List<LexemePosition> expected = new ArrayList<>();
        expected.add(new ExtendedLexemePosition<>(1, 1, 5, LexerUtils.Lexeme.NUM, 0.4321));
        expected.add(new LexemePosition(1, 6, 6, EOF));
        assertEquals(expected, lexer.getLexemes());
    }

    @Test
    public void testGetLexemesNum() throws LexerException {
        Lexer lexer = new Lexer("10.542e-1");
        List<LexemePosition> expected = new ArrayList<>();
        expected.add(new ExtendedLexemePosition<>(1, 1, 9, LexerUtils.Lexeme.NUM, 1.0542));
        expected.add(new LexemePosition(1, 10, 10, EOF));
        assertEquals(expected, lexer.getLexemes());
    }

    @Test
    public void testGetLexemesLess() throws LexerException {
        Lexer lexer = new Lexer("(fl <3)");
        List<LexemePosition> expected = Arrays.asList(new LexemePosition(1, 1, 1, LexerUtils.Lexeme.LEFT_BRACKET),
                new ExtendedLexemePosition<>(1, 2, 3, LexerUtils.Lexeme.IDENTIFIER, "fl"),
                new LexemePosition(1, 5, 5, LexerUtils.Lexeme.LESS),
                new ExtendedLexemePosition<>(1, 6, 6, LexerUtils.Lexeme.NUM, 3.0),
                new LexemePosition(1, 7, 7, LexerUtils.Lexeme.RIGHT_BRACKET),
                new LexemePosition(1, 8, 8, EOF));
        assertEquals(expected, lexer.getLexemes());
    }

    @Test
    public void testGetLexemesComma() throws LexerException {
        Lexer lexer = new Lexer("do not worry,\tbe happy");
        List<LexemePosition> expected = Arrays.asList(new LexemePosition(1, 1, 2, LexerUtils.Lexeme.DO),
                new ExtendedLexemePosition<>(1, 4, 6, LexerUtils.Lexeme.IDENTIFIER, "not"),
                new ExtendedLexemePosition<>(1, 8, 12, LexerUtils.Lexeme.IDENTIFIER, "worry"),
                new LexemePosition(1, 13, 13, LexerUtils.Lexeme.COMMA),
                new ExtendedLexemePosition<>(1, 15, 16, LexerUtils.Lexeme.IDENTIFIER, "be"),
                new ExtendedLexemePosition<>(1, 18, 22, LexerUtils.Lexeme.IDENTIFIER, "happy"),
                new LexemePosition(1, 23, 23, EOF));
        assertEquals(expected, lexer.getLexemes());
    }

    @Test
    public void testGetLexemesOr() throws LexerException {
        Lexer lexer = new Lexer("summertime||sadness");
        List<LexemePosition> expected = Arrays.asList(new ExtendedLexemePosition<>(1, 1, 10, LexerUtils.Lexeme.IDENTIFIER, "summertime"),
                new LexemePosition(1, 11, 12, LexerUtils.Lexeme.OR),
                new ExtendedLexemePosition<>(1, 13, 19, LexerUtils.Lexeme.IDENTIFIER, "sadness"),
                new LexemePosition(1, 20, 20, EOF));
        assertEquals(expected, lexer.getLexemes());
    }

    @Test
    public void testGetLexemesBoolean() throws LexerException {
        Lexer lexer = new Lexer("c==false&&java== true");
        List<LexemePosition> expected = Arrays.asList(new ExtendedLexemePosition<>(1, 1, 1, LexerUtils.Lexeme.IDENTIFIER, "c"),
                new LexemePosition(1, 2, 3, LexerUtils.Lexeme.EQUAL),
                new ExtendedLexemePosition<>(1, 4, 8, LexerUtils.Lexeme.BOOLEAN, "false"),
                new LexemePosition(1, 9, 10, LexerUtils.Lexeme.AND),
                new ExtendedLexemePosition<>(1, 11, 14, LexerUtils.Lexeme.IDENTIFIER, "java"),
                new LexemePosition(1, 15, 16, LexerUtils.Lexeme.EQUAL),
                new ExtendedLexemePosition<>(1, 18, 21, LexerUtils.Lexeme.BOOLEAN, "true"),
                new LexemePosition(1, 22, 22, EOF));
        assertEquals(expected, lexer.getLexemes());
    }

    @Test
    public void testGetLexemesOneLineMedium() throws LexerException {
        Lexer lexer = new Lexer("read x;\fif y + 1 == x then write y else\fwrite x");
        List<LexemePosition> expected = Arrays.asList(new LexemePosition(1, 1, 4, LexerUtils.Lexeme.READ),
                new ExtendedLexemePosition<>(1, 6, 6, LexerUtils.Lexeme.IDENTIFIER, "x"),
                new LexemePosition(1, 7, 7, LexerUtils.Lexeme.SEMICOLON),
                new LexemePosition(1, 9, 10, LexerUtils.Lexeme.IF),
                new ExtendedLexemePosition<>(1, 12, 12, LexerUtils.Lexeme.IDENTIFIER, "y"),
                new LexemePosition(1, 14, 14, LexerUtils.Lexeme.ADDITION),
                new ExtendedLexemePosition<>(1, 16, 16, LexerUtils.Lexeme.NUM, 1.0),
                new LexemePosition(1, 18, 19, LexerUtils.Lexeme.EQUAL),
                new ExtendedLexemePosition<>(1, 21, 21, LexerUtils.Lexeme.IDENTIFIER, "x"),
                new LexemePosition(1, 23, 26, LexerUtils.Lexeme.THEN),
                new LexemePosition(1, 28, 32, LexerUtils.Lexeme.WRITE),
                new ExtendedLexemePosition<>(1, 34, 34, LexerUtils.Lexeme.IDENTIFIER, "y"),
                new LexemePosition(1, 36, 39, LexerUtils.Lexeme.ELSE),
                new LexemePosition(1, 41, 45, LexerUtils.Lexeme.WRITE),
                new ExtendedLexemePosition<>(1, 47, 47, LexerUtils.Lexeme.IDENTIFIER, "x"),
                new LexemePosition(1, 48, 48, EOF));
        assertEquals(expected, lexer.getLexemes());
    }

    @Test
    public void testGetLexemesLF() throws LexerException {
        Lexer lexer = new Lexer("first % line;\nsecond^line");
        List<LexemePosition> expected = Arrays.asList(new ExtendedLexemePosition<>(1, 1, 5, LexerUtils.Lexeme.IDENTIFIER, "first"),
                new LexemePosition(1, 7, 7, LexerUtils.Lexeme.MODULO),
                new ExtendedLexemePosition<>(1, 9, 12, LexerUtils.Lexeme.IDENTIFIER, "line"),
                new LexemePosition(1, 13, 13, LexerUtils.Lexeme.SEMICOLON),
                new ExtendedLexemePosition<>(2, 1, 6, LexerUtils.Lexeme.IDENTIFIER, "second"),
                new LexemePosition(2, 7, 7, LexerUtils.Lexeme.EXPONENTIATION),
                new ExtendedLexemePosition<>(2, 8, 11, LexerUtils.Lexeme.IDENTIFIER, "line"),
                new LexemePosition(2, 12, 12, EOF));
        assertEquals(expected, lexer.getLexemes());
    }

    @Test
    public void testGetLexemesCR() throws LexerException {
        Lexer lexer = new Lexer("while true\rdie");
        List<LexemePosition> expected = Arrays.asList(new LexemePosition(1, 1, 5, LexerUtils.Lexeme.WHILE),
                new ExtendedLexemePosition<>(1, 7, 10, LexerUtils.Lexeme.BOOLEAN, "true"),
                new ExtendedLexemePosition<>(2, 1, 3, LexerUtils.Lexeme.IDENTIFIER, "die"),
                new LexemePosition(2, 4, 4, EOF));
        assertEquals(expected, lexer.getLexemes());
    }

    @Test
    public void testGetLexemesCRLF() throws LexerException {
        Lexer lexer = new Lexer("while true\r\ndie");
        List<LexemePosition> expected = Arrays.asList(new LexemePosition(1, 1, 5, LexerUtils.Lexeme.WHILE),
                new ExtendedLexemePosition<>(1, 7, 10, LexerUtils.Lexeme.BOOLEAN, "true"),
                new ExtendedLexemePosition<>(2, 1, 3, LexerUtils.Lexeme.IDENTIFIER, "die"),
                new LexemePosition(2, 4, 4, EOF));
        assertEquals(expected, lexer.getLexemes());
    }

    @Test
    public void testGetLexemesLFCR() throws LexerException {
        Lexer lexer = new Lexer("while true\n\rdie");
        List<LexemePosition> expected = Arrays.asList(new LexemePosition(1, 1, 5, LexerUtils.Lexeme.WHILE),
                new ExtendedLexemePosition<>(1, 7, 10, LexerUtils.Lexeme.BOOLEAN, "true"),
                new ExtendedLexemePosition<>(3, 1, 3, LexerUtils.Lexeme.IDENTIFIER, "die"),
                new LexemePosition(3, 4, 4, EOF));
        assertEquals(expected, lexer.getLexemes());
    }

    @Test
    public void testGetLexemesMultiLineMedium() throws LexerException {
        Lexer lexer = new Lexer("read x;\n\nif y + 1 == x\r\n then\n write y else\fwrite x");
        List<LexemePosition> expected = Arrays.asList(new LexemePosition(1, 1, 4, LexerUtils.Lexeme.READ),
                new ExtendedLexemePosition<>(1, 6, 6, LexerUtils.Lexeme.IDENTIFIER, "x"),
                new LexemePosition(1, 7, 7, LexerUtils.Lexeme.SEMICOLON),
                new LexemePosition(3, 1, 2, LexerUtils.Lexeme.IF),
                new ExtendedLexemePosition<>(3, 4, 4, LexerUtils.Lexeme.IDENTIFIER, "y"),
                new LexemePosition(3, 6, 6, LexerUtils.Lexeme.ADDITION),
                new ExtendedLexemePosition<>(3, 8, 8, LexerUtils.Lexeme.NUM, 1.0),
                new LexemePosition(3, 10, 11, LexerUtils.Lexeme.EQUAL),
                new ExtendedLexemePosition<>(3, 13, 13, LexerUtils.Lexeme.IDENTIFIER, "x"),
                new LexemePosition(4, 2, 5, LexerUtils.Lexeme.THEN),
                new LexemePosition(5, 2, 6, LexerUtils.Lexeme.WRITE),
                new ExtendedLexemePosition<>(5, 8, 8, LexerUtils.Lexeme.IDENTIFIER, "y"),
                new LexemePosition(5, 10, 13, LexerUtils.Lexeme.ELSE),
                new LexemePosition(5, 15, 19, LexerUtils.Lexeme.WRITE),
                new ExtendedLexemePosition<>(5, 21, 21, LexerUtils.Lexeme.IDENTIFIER, "x"),
                new LexemePosition(5, 22, 22, EOF));
        assertEquals(expected, lexer.getLexemes());
    }

    @Test
    public void testGetLexemesThrowsExceptionAfterDot() throws LexerException {
        exception.expect(LexerException.class);
        exception.expectMessage("Digit expected. Line 1, position 5.");
        Lexer lexer = new Lexer("do .ab");
        lexer.getLexemes();
    }

    @Test
    public void testGetLexemesThrowsExceptionAfterExp() throws LexerException {
        exception.expect(LexerException.class);
        exception.expectMessage("Digit expected. Line 1, position 9.");
        Lexer lexer = new Lexer("if .321er");
        lexer.getLexemes();
    }

    @Test
    public void testGetLexemesThrowsException() throws LexerException {
        exception.expect(LexerException.class);
        exception.expectMessage("Unexpected symbol. Line 1, position 7.");
        Lexer lexer = new Lexer("read !t");
        lexer.getLexemes();
    }
}
