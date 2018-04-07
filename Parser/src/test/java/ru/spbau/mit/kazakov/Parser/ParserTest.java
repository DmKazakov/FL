package ru.spbau.mit.kazakov.Parser;

import org.junit.Test;

import static org.junit.Assert.*;

public class ParserTest {
    @Test
    public void printDerivationTree() throws ParsingException {
        Parser.printDerivationTree("0 + 13 * 42 - 7 / 0" + '\0');
        Parser.printDerivationTree("(0 + 13) * ((42 - 7) / 0)" + '\0');
        Parser.printDerivationTree("1 - 2 - 3 - (5 - 6)" + '\0');
        Parser.printDerivationTree("13" + '\0');
        Parser.printDerivationTree("(((((13)))))" + '\0');
        Parser.printDerivationTree("42 ^ 24 - 156 * 123" + '\0');
        Parser.printDerivationTree("(42 ^ (24 - 156) * 123)" + '\0');
    }

    @Test
    public void printDerivationTreeE() throws ParsingException {
        Parser.printDerivationTree("0 13 * 42 - 7 / 0" + '\0');
    }

    @Test
    public void printDerivationTreeE2() throws ParsingException {
        Parser.printDerivationTree("(0 + 13 * ((42 - 7) / 0)" + '\0');
    }
}