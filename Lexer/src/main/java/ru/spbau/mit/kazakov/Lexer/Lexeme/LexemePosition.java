package ru.spbau.mit.kazakov.Lexer.Lexeme;

import org.jetbrains.annotations.NotNull;
import ru.spbau.mit.kazakov.Lexer.LexerUtils;

public class LexemePosition {
    protected int line;
    protected int begin;
    protected int end;
    protected LexerUtils.Lexeme lexeme;

    public LexemePosition(int line, int begin, int end, @NotNull LexerUtils.Lexeme lexeme) {
        this.line = line;
        this.begin = begin;
        this.end = end;
        this.lexeme = lexeme;
    }

    @Override
    @NotNull
    public String toString() {
        return lexeme.toString() + "(" + line + ", " + begin + ", " + end + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof LexemePosition) {
            LexemePosition l = (LexemePosition) o;
            return l.line == line && l.begin == begin && l.end == end && l.lexeme.equals(lexeme);
        } else {
            return false;
        }
    }
}
