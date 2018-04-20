package ru.spbau.mit.kazakov.Lexer;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;


public class LexerUtils {
    private static final Map<String, Lexeme> LEXEMES = new HashMap<>();
    private static final Set<Character> WHITESPACES = new HashSet<>();

    private static final Pattern numPattern = Pattern.compile("([0-9]+\\.)|(([0-9]+|([0-9]+)?\\.[0-9]+)?([eE](\\+|-)?[0-9]+)?)");

    static {
        LEXEMES.put("+", Lexeme.ADDITION);
        LEXEMES.put("-", Lexeme.SUBTRACTION);
        LEXEMES.put("*", Lexeme.MULTIPLICATION);
        LEXEMES.put("/", Lexeme.DIVISION);
        LEXEMES.put("%", Lexeme.MODULO);
        LEXEMES.put("^", Lexeme.EXPONENTIATION);
        LEXEMES.put("&&", Lexeme.AND);
        LEXEMES.put("||", Lexeme.OR);
        LEXEMES.put(">", Lexeme.GREATER);
        LEXEMES.put("<", Lexeme.LESS);
        LEXEMES.put(">=", Lexeme.GREATER_OR_EQ);
        LEXEMES.put("<=", Lexeme.LESS_OR_EQ);
        LEXEMES.put("==", Lexeme.EQUAL);
        LEXEMES.put("!=", Lexeme.NOT_EQUAL);

        WHITESPACES.add('\r');
        WHITESPACES.add('\n');
        WHITESPACES.add('\t');
        WHITESPACES.add('\f');
        WHITESPACES.add(' ');

        LEXEMES.put("if", Lexeme.IF);
        LEXEMES.put("then", Lexeme.THEN);
        LEXEMES.put("else", Lexeme.ELSE);
        LEXEMES.put("while", Lexeme.WHILE);
        LEXEMES.put("do", Lexeme.DO);
        LEXEMES.put("read", Lexeme.READ);
        LEXEMES.put("write", Lexeme.WRITE);

        LEXEMES.put("(", Lexeme.LEFT_BRACKET);
        LEXEMES.put(")", Lexeme.RIGHT_BRACKET);
        LEXEMES.put(";", Lexeme.SEMICOLON);
        LEXEMES.put(",", Lexeme.COMMA);

        LEXEMES.put("true", Lexeme.BOOLEAN);
        LEXEMES.put("false", Lexeme.BOOLEAN);
    }

    public enum LexemeType {
        KEYWORD, OPERATOR, SEPARATOR, IDENTIFIER, LITERAL
    }

    public enum Lexeme {
        ADDITION(LexemeType.OPERATOR), SUBTRACTION(LexemeType.OPERATOR), MULTIPLICATION(LexemeType.OPERATOR),
        DIVISION(LexemeType.OPERATOR), MODULO(LexemeType.OPERATOR), EXPONENTIATION(LexemeType.OPERATOR), AND(LexemeType.OPERATOR),
        OR(LexemeType.OPERATOR), GREATER(LexemeType.OPERATOR), LESS(LexemeType.OPERATOR), GREATER_OR_EQ(LexemeType.OPERATOR),
        LESS_OR_EQ(LexemeType.OPERATOR), EQUAL(LexemeType.OPERATOR), NOT_EQUAL(LexemeType.OPERATOR),

        IF(LexemeType.KEYWORD), THEN(LexemeType.KEYWORD), ELSE(LexemeType.KEYWORD), WHILE(LexemeType.KEYWORD),
        DO(LexemeType.KEYWORD), READ(LexemeType.KEYWORD), WRITE(LexemeType.KEYWORD),

        COMMA(LexemeType.SEPARATOR), SEMICOLON(LexemeType.SEPARATOR), LEFT_BRACKET(LexemeType.SEPARATOR),
        RIGHT_BRACKET(LexemeType.SEPARATOR),

        NUM(LexemeType.LITERAL), BOOLEAN(LexemeType.LITERAL),

        IDENTIFIER(LexemeType.IDENTIFIER);

        private LexemeType type;

        Lexeme(@NotNull LexemeType type) {
            this.type = type;
        }

        @NotNull
        public LexemeType getType() {
            return type;
        }
    }

    public static boolean isLexeme(@NotNull String token) {
        return LEXEMES.containsKey(token);
    }

    @NotNull
    public static Lexeme toLexeme(@NotNull String token) {
        return LEXEMES.get(token);
    }

    public static boolean isWhitespace(char token) {
        return WHITESPACES.contains(token);
    }

    public static boolean isIdentifierStart(char character) {
        return (Character.isLetter(character) && Character.isLowerCase(character)) || character == '_';
    }

    public static boolean isIdentifierPart(char character) {
        return isIdentifierStart(character) || Character.isDigit(character);
    }

    public static boolean isOperatorPart(char character) {
        return (character == '+') || (character == '-') || (character == '*') || (character == '/') ||
                (character == '%') || (character == '^') || (character == '&') || (character == '|') ||
                (character == '=') || (character == '<') || (character == '>') || (character == '!');
    }

    public static boolean isNum(@NotNull CharSequence token) {
        return numPattern.matcher(token).matches();
    }
}
