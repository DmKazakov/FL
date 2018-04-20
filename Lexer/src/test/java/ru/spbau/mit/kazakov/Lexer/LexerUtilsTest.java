package ru.spbau.mit.kazakov.Lexer;

import org.junit.Test;

import static org.junit.Assert.*;
import static ru.spbau.mit.kazakov.Lexer.LexerUtils.*;

public class LexerUtilsTest {
    @Test
    public void testIsLexemePlus() {
        assertTrue(isLexeme("+"));
    }

    @Test
    public void testIsLexemeMinus() {
        assertTrue(isLexeme("-"));
    }

    @Test
    public void testIsLexemeMultiply() {
        assertTrue(isLexeme("*"));
    }

    @Test
    public void testIsLexemeDivide() {
        assertTrue(isLexeme("/"));
    }

    @Test
    public void testIsLexemeModulo() {
        assertTrue(isLexeme("%"));
    }

    @Test
    public void testIsLexemePower() {
        assertTrue(isLexeme("^"));
    }

    @Test
    public void testIsLexemeAnd() {
        assertTrue(isLexeme("&&"));
    }

    @Test
    public void testIsLexemeOr() {
        assertTrue(isLexeme("||"));
    }

    @Test
    public void testIsLexemeGreater() {
        assertTrue(isLexeme(">"));
    }

    @Test
    public void testIsLexemeLess() {
        assertTrue(isLexeme("<"));
    }

    @Test
    public void testIsLexemeGreaterOrEqual() {
        assertTrue(isLexeme(">="));
    }

    @Test
    public void testIsLexemeLessOrEqual() {
        assertTrue(isLexeme("<="));
    }

    @Test
    public void testIsLexemeEqual() {
        assertTrue(isLexeme("=="));
    }

    @Test
    public void testIsLexemeNotEqual() {
        assertTrue(isLexeme("!="));
    }

    @Test
    public void testIsLexemeIf() {
        assertTrue(isLexeme("if"));
    }

    @Test
    public void testIsLexemeThen() {
        assertTrue(isLexeme("then"));
    }

    @Test
    public void testIsLexemeElse() {
        assertTrue(isLexeme("else"));
    }

    @Test
    public void testIsLexemeWhile() {
        assertTrue(isLexeme("while"));
    }

    @Test
    public void testIsLexemeDo() {
        assertTrue(isLexeme("do"));
    }

    @Test
    public void testIsLexemeRead() {
        assertTrue(isLexeme("read"));
    }

    @Test
    public void testIsLexemeWrite() {
        assertTrue(isLexeme("write"));
    }

    @Test
    public void testIsLexemeLeftBracket() {
        assertTrue(isLexeme("("));
    }

    @Test
    public void testIsLexemeRightBracket() {
        assertTrue(isLexeme(")"));
    }

    @Test
    public void testIsLexemeSemicolon() {
        assertTrue(isLexeme(";"));
    }

    @Test
    public void testIsLexemeComma() {
        assertTrue(isLexeme(","));
    }

    @Test
    public void testIsLexemeTrue() {
        assertTrue(isLexeme("true"));
    }

    @Test
    public void testIsLexemeFalse() {
        assertTrue(isLexeme("false"));
    }

    @Test
    public void testIsWhitespaceCR() {
        assertTrue(isWhitespace('\r'));
    }

    @Test
    public void testIsWhitespaceLF() {
        assertTrue(isWhitespace('\n'));
    }

    @Test
    public void testIsWhitespaceSP() {
        assertTrue(isWhitespace(' '));
    }

    @Test
    public void testIsWhitespaceHT() {
        assertTrue(isWhitespace('\t'));
    }

    @Test
    public void testIsWhitespaceFF() {
        assertTrue(isWhitespace('\f'));
    }

    @Test
    public void testIsWhitespaceFalse() {
        assertFalse(isWhitespace('_'));
    }

    @Test
    public void testIsIdentifierStartLetter() {
        assertTrue(isIdentifierStart('q'));

    }

    @Test
    public void testIsIdentifierStartUnderscore() {
        assertTrue(isIdentifierStart('_'));

    }

    @Test
    public void testIsIdentifierStartDigit() {
        assertFalse(isIdentifierStart('1'));

    }

    @Test
    public void testIsIdentifierStartFalse() {
        assertFalse(isIdentifierStart('!'));

    }

    @Test
    public void testIsIdentifierPartLetter() {
        assertTrue(isIdentifierPart('q'));
    }

    @Test
    public void testIsIdentifierPartUnderscore() {
        assertTrue(isIdentifierPart('_'));
    }

    @Test
    public void testIsIdentifierPartDigit() {
        assertTrue(isIdentifierPart('6'));
    }

    @Test
    public void testIsIdentifierPartFalse() {
        assertFalse(isIdentifierPart('?'));
    }

    @Test
    public void testIsOperatorPartLess() {
        assertTrue(isOperatorPart('<'));
    }

    @Test
    public void testIsOperatorPartGreater() {
        assertTrue(isOperatorPart('>'));
    }

    @Test
    public void testIsOperatorPartEqual() {
        assertTrue(isOperatorPart('='));
    }

    @Test
    public void testIsOperatorPartAnd() {
        assertTrue(isOperatorPart('&'));
    }

    @Test
    public void testIsOperatorPartOr() {
        assertTrue(isOperatorPart('|'));
    }

    @Test
    public void testIsOperatorPartExclamation() {
        assertTrue(isOperatorPart('!'));
    }

    @Test
    public void testIsOperatorPartPlus() {
        assertTrue(isOperatorPart('+'));
    }

    @Test
    public void testIsOperatorPartMinus() {
        assertTrue(isOperatorPart('-'));
    }

    @Test
    public void testIsOperatorPartMultiply() {
        assertTrue(isOperatorPart('*'));
    }

    @Test
    public void testIsOperatorPartDivide() {
        assertTrue(isOperatorPart('/'));
    }

    @Test
    public void testIsOperatorPartModulo() {
        assertTrue(isOperatorPart('%'));
    }

    @Test
    public void testIsOperatorPartPower() {
        assertTrue(isOperatorPart('^'));
    }

    @Test
    public void testIsOperatorPartFalse() {
        assertFalse(isOperatorPart('?'));
    }

    @Test
    public void testIsNumSimple() {
        assertTrue(isNum("5431"));
    }

    @Test
    public void testIsNumSimpleDot() {
        assertTrue(isNum("543."));
    }

    @Test
    public void testIsNumSimpleExp() {
        assertTrue(isNum("e32"));
        assertTrue(isNum("E32"));
    }

    @Test
    public void testIsNumPlusExp() {
        assertTrue(isNum("e+7"));
        assertTrue(isNum("E+7"));
    }

    @Test
    public void testIsNumMinusExp() {
        assertTrue(isNum("e-7563"));
        assertTrue(isNum("E-7563"));
    }

    @Test
    public void testIsNumNoExp() {
        assertTrue(isNum("6452.3235"));
    }

    @Test
    public void testIsNumDotStart() {
        assertTrue(isNum(".35431"));
    }

    @Test
    public void testIsNumDotStartExp() {
        assertTrue(isNum(".654e-13"));
    }

    @Test
    public void testIsNum() {
        assertTrue(isNum("215430.02122e+74543"));
        assertTrue(isNum("215430.02122E+74543"));
    }

    @Test
    public void testIsNumExtraLetter() {
        assertFalse(isNum("215430.02122q+74543"));
    }

    @Test
    public void testIsNumExtraDot() {
        assertFalse(isNum("215430.0212.2e+74543"));
    }

    @Test
    public void testIsNumExtraExp() {
        assertFalse(isNum("215430.0212.2e+7454e3"));
    }
}