package ru.spbau.mit.kazakov.Parser;

public class ParsingException extends Exception {
    private int errorPosition;

    public ParsingException(int errorPosition) {
        this.errorPosition = errorPosition;
    }

    public int getErrorPosition() {
        return errorPosition;
    }
}
