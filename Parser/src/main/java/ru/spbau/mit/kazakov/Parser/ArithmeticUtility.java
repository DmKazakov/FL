package ru.spbau.mit.kazakov.Parser;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;


public class ArithmeticUtility {
    private static final Map<Character, Operator> OPERATORS = new HashMap<>();

    static {
        OPERATORS.put('+', Operator.ADDITION);
        OPERATORS.put('-', Operator.SUBTRACTION);
        OPERATORS.put('*', Operator.MULTIPLICATION);
        OPERATORS.put('/', Operator.DIVISION);
        OPERATORS.put('^', Operator.EXPONENTIATION);
    }

    public interface Token {
        String toString();
    }

    public enum Operator implements Token {
        ADDITION(true, false, false), SUBTRACTION(true, false, false), MULTIPLICATION(false, true, false),
        DIVISION(false, true, false), EXPONENTIATION(false, false, true);


        private boolean isExprOperator;
        private boolean isTermOperator;
        private boolean isPowerOperator;

        Operator(boolean isExprOperator, boolean isTermOperator, boolean isPowerOperator) {
            this.isExprOperator = isExprOperator;
            this.isTermOperator = isTermOperator;
            this.isPowerOperator = isPowerOperator;
        }

        public boolean isExprOperator() {
            return isExprOperator;
        }

        public boolean isTermOperator() {
            return isTermOperator;
        }

        public boolean isPowerOperator() {
            return isPowerOperator;
        }
    }

    public static class Num implements Token {
        private String num;

        public Num(String num) {
            this.num = num;
        }

        @NotNull
        @Override
        public String toString() {
            return num;
        }
    }

    public static boolean isOperator(char token) {
        return OPERATORS.containsKey(token);
    }

    public static Operator charToOperator(char token) {
        return OPERATORS.get(token);
    }
}
