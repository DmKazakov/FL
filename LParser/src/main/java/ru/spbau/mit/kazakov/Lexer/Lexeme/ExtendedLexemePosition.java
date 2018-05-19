package ru.spbau.mit.kazakov.Lexer.Lexeme;

import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.kazakov.Lexer.LexerUtils;

public class ExtendedLexemePosition<T> extends LexemePosition {
    private T value;

    public ExtendedLexemePosition(int line, int begin, int end, @NotNull LexerUtils.Lexeme lexeme, T value) {
        super(line, begin, end, lexeme);
        this.value = value;
    }

    public ExtendedLexemePosition(@NotNull LexerUtils.Lexeme lexeme, T value) {
        super(lexeme);
        this.value = value;
    }

    @Override
    @NotNull
    public String toString() {
        return lexeme.toString() + "(" + value + ", " + line + ", " + begin + ", " + end + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ExtendedLexemePosition) {
            ExtendedLexemePosition l = (ExtendedLexemePosition) o;
            return l.line == line && l.begin == begin && l.end == end && l.lexeme.equals(lexeme) && l.value.equals(value);
        } else {
            return false;
        }
    }
}
