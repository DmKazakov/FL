package ru.spbau.mit.kazakov.Lexer;

import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.kazakov.Lexer.Lexeme.ExtendedLexemePosition;
import ru.spbau.mit.kazakov.Lexer.Lexeme.LexemePosition;

import java.util.ArrayList;
import java.util.List;

import static ru.spbau.mit.kazakov.Lexer.LexerUtils.*;
import static ru.spbau.mit.kazakov.Lexer.LexerUtils.Lexeme.COMPOSITION;
import static ru.spbau.mit.kazakov.Lexer.LexerUtils.Lexeme.EOF;


public class Lexer {
    private String sourceCode;
    private int currentPosition = 0;
    private int currentLine = 1;
    private int currentPositionOnLine = 1;
    private List<LexemePosition> lexemes = new ArrayList<>();

    public Lexer(@NotNull String sourceCode) {
        this.sourceCode = sourceCode + '\0';
    }

    @NotNull
    public List<LexemePosition> getLexemes() throws LexerException {
        int prevPos = currentPosition;
        skipWhiteSpaceChars();
        tokenize();

        while (prevPos != currentPosition) {
            prevPos = currentPosition;
            skipWhiteSpaceChars();
            tokenize();
        }

        if (currentPosition != sourceCode.length() - 1) {
            throw new LexerException("Failed to tokenize. Line " + currentLine + ", position "
                    + currentPositionOnLine + ".");
        }

        lexemes.add(new LexemePosition(currentLine, currentPositionOnLine, currentPositionOnLine, EOF));
        return lexemes;
    }

    private char currentChar() {
        return sourceCode.charAt(currentPosition);
    }

    @NotNull
    private String currentStringChar() {
        return Character.toString(currentChar());
    }

    private char moveCurrentChar() {
        char prevChar = currentChar();
        currentPosition++;
        currentPositionOnLine++;
        return prevChar;
    }

    private char skipWhiteSpaceChars() {
        char prevChar = currentChar();
        while (isWhitespace(currentChar())) {
            if (currentChar() == '\r') {
                currentLine++;
                currentPositionOnLine = 0;
                currentPosition++;
                if (currentChar() == '\n') {
                    currentPosition++;
                }
            } else if (currentChar() == '\n') {
                currentLine++;
                currentPositionOnLine = 0;
                currentPosition++;
            } else {
                currentPosition++;
            }
            currentPositionOnLine++;
        }
        return prevChar;
    }

    private void tokenize() throws LexerException {
        if (isLexeme(currentStringChar()) && toLexeme(currentStringChar()).getType().equals(LexemeType.SEPARATOR)) {
            Lexeme separator = toLexeme(currentStringChar());
            moveCurrentChar();
            addLexeme(separator, 1);
        } else if (Character.isDigit(currentChar()) || currentChar() == '.') {                                                                     //num lits
            tokenizeNum();
        } else if (isIdentifierStart(currentChar())) {
            tokenizeAlphabetic();
        } else if (isOperatorPart(currentChar())) {
            tokenizeOperator();
        }
    }

    private void tokenizeNum() throws LexerException {
        StringBuilder numBuilder = new StringBuilder();
        if (currentChar() == '.') {
            addLexeme(COMPOSITION, 1);
            numBuilder.append(currentChar());
            moveCurrentChar();
            int currentLine = this.currentLine;
            int currentPositionOnLine = this.currentPositionOnLine;
            if(isWhitespace(currentChar())) {
                skipWhiteSpaceChars();
                if (isIdentifierStart(currentChar())) {
                    tokenizeAlphabetic();
                    return;
                }
            }
            lexemes.remove(lexemes.size() - 1);
            if (!Character.isDigit(currentChar())) {
                throw new LexerException("Digit expected. Line " + currentLine + ", position "
                        + currentPositionOnLine + ".");
            }
        }

        numBuilder.append(currentChar());
        while (isNum(numBuilder)) {
            moveCurrentChar();
            numBuilder.append(currentChar());
        }

        char lastChar = numBuilder.charAt(numBuilder.length() - 1);
        if (lastChar == 'e' || lastChar == 'E') {
            moveCurrentChar();
            if (currentChar() == '+' || currentChar() == '-') {
                numBuilder.append(currentChar());
                moveCurrentChar();
            }
            if (Character.isDigit(currentChar())) {
                numBuilder.append(currentChar());
                while (isNum(numBuilder)) {
                    moveCurrentChar();
                    numBuilder.append(currentChar());
                }
            } else {
                throw new LexerException("Digit expected. Line " + currentLine + ", position "
                        + currentPositionOnLine + ".");
            }
        }

        String num = numBuilder.deleteCharAt(numBuilder.length() - 1).toString();
        addLexeme(Lexeme.NUM, Double.parseDouble(num), num.length());
    }

    private void tokenizeOperator() throws LexerException {
        char prevChar = currentChar();
        moveCurrentChar();
        String token = Character.toString(prevChar) + Character.toString(currentChar());
        if (isLexeme(token)) {
            moveCurrentChar();
            addLexeme(toLexeme(token), token.length());
        } else if (isLexeme(Character.toString(prevChar))) {
            addLexeme(toLexeme(Character.toString(prevChar)), 1);
        } else {
            throw new LexerException("Unexpected symbol. Line " + currentLine + ", position "
                    + currentPositionOnLine + ".");
        }
    }

    private void tokenizeAlphabetic() {
        StringBuilder tokenBuilder = new StringBuilder();
        while (isIdentifierPart(currentChar())) {
            tokenBuilder.append(currentChar());
            moveCurrentChar();
        }

        String token = tokenBuilder.toString();
        if (!isLexeme(token)) {
            addLexeme(Lexeme.IDENTIFIER, token, token.length());
        } else if (toLexeme(token).equals(Lexeme.BOOLEAN)) {
            addLexeme(toLexeme(token), token, token.length());
        } else {
            addLexeme(toLexeme(token), token.length());
        }
    }

    private void addLexeme(@NotNull LexerUtils.Lexeme lexeme, int length) {
        lexemes.add(new LexemePosition(currentLine, currentPositionOnLine - length,
                currentPositionOnLine - 1, lexeme));
    }

    private <T> void addLexeme(@NotNull LexerUtils.Lexeme lexeme, @NotNull T value, int length) {
        lexemes.add(new ExtendedLexemePosition<>(currentLine, currentPositionOnLine - length,
                currentPositionOnLine - 1, lexeme, value));
    }
}
