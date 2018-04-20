package ru.spbau.mit.kazakov.Lexer;

import org.jetbrains.annotations.NotNull;

public class LexerException extends Exception {
    public LexerException(@NotNull String message) {
        super(message);
    }
}
