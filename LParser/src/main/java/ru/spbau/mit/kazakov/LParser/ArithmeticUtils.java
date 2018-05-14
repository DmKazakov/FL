package ru.spbau.mit.kazakov.LParser;

import ru.spbau.mit.kazakov.Lexer.LexerUtils.Lexeme;

import java.util.HashMap;
import java.util.Map;


public class ArithmeticUtils {
    private static final Map<Lexeme, Operator> OPERATORS = new HashMap<>();

    static {
        OPERATORS.put(Lexeme.ADDITION, Operator.ADDITION);
        OPERATORS.put(Lexeme.SUBTRACTION, Operator.SUBTRACTION);
        OPERATORS.put(Lexeme.MULTIPLICATION, Operator.MULTIPLICATION);
        OPERATORS.put(Lexeme.DIVISION, Operator.DIVISION);
        OPERATORS.put(Lexeme.MODULO, Operator.MODULO);
        OPERATORS.put(Lexeme.EXPONENTIATION, Operator.EXPONENTIATION);
        OPERATORS.put(Lexeme.EQUAL, Operator.EQUAL);
        OPERATORS.put(Lexeme.NOT_EQUAL, Operator.NOT_EQUAL);
        OPERATORS.put(Lexeme.LESS, Operator.LESS);
        OPERATORS.put(Lexeme.LESS_OR_EQ, Operator.LESS_OR_EQ);
        OPERATORS.put(Lexeme.GREATER, Operator.GREATER);
        OPERATORS.put(Lexeme.GREATER_OR_EQ, Operator.GREATER_OR_EQ);
        OPERATORS.put(Lexeme.OR, Operator.OR);
        OPERATORS.put(Lexeme.AND, Operator.AND);
    }

    public enum OperatorType {
        EXPRESSION {
            @Override
            public OperatorType lower() {
                return TERM;
            }
        },
        TERM {
            @Override
            public OperatorType lower() {
                return POWER;
            }
        },
        POWER {
            @Override
            public OperatorType lower() {
                return null;
            }
        },
        CLAUSE {
            @Override
            public OperatorType lower() {
                return CONJUNCTION;
            }
        },
        CONJUNCTION {
            @Override
            public OperatorType lower() {
                return EQUALITY;
            }
        },
        EQUALITY {
            @Override
            public OperatorType lower() {
                return INEQUALITY;
            }
        },
        INEQUALITY {
            @Override
            public OperatorType lower() {
                return EXPRESSION;
            }
        };

        public abstract OperatorType lower();
    }

    public enum Operator {
        ADDITION(OperatorType.EXPRESSION), SUBTRACTION(OperatorType.EXPRESSION), MULTIPLICATION(OperatorType.TERM),
        DIVISION(OperatorType.TERM), MODULO(OperatorType.TERM), EXPONENTIATION(OperatorType.POWER),
        EQUAL(OperatorType.EQUALITY), NOT_EQUAL(OperatorType.EQUALITY), LESS(OperatorType.INEQUALITY),
        GREATER(OperatorType.INEQUALITY), LESS_OR_EQ(OperatorType.INEQUALITY), GREATER_OR_EQ(OperatorType.INEQUALITY),
        OR(OperatorType.CLAUSE), AND(OperatorType.CONJUNCTION);

        private OperatorType type;

        Operator(OperatorType type) {
            this.type = type;
        }

        public OperatorType getType() {
            return type;
        }
    }

    public static boolean isOperator(Lexeme token) {
        return OPERATORS.containsKey(token);
    }

    public static Operator lexemeToOperator(Lexeme token) {
        return OPERATORS.get(token);
    }
}
