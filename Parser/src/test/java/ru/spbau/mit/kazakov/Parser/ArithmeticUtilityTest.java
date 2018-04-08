package ru.spbau.mit.kazakov.Parser;

import org.junit.Test;

import static org.junit.Assert.*;

public class ArithmeticUtilityTest {

    @Test
    public void testIsOperatorPlus() {
        assertTrue(ArithmeticUtility.isOperator('+'));
    }

    @Test
    public void testIsOperatorMinus() {
        assertTrue(ArithmeticUtility.isOperator('-'));
    }

    @Test
    public void testIsOperatorMultiply() {
        assertTrue(ArithmeticUtility.isOperator('*'));
    }

    @Test
    public void testIsOperatorDivide() {
        assertTrue(ArithmeticUtility.isOperator('/'));
    }

    @Test
    public void testIsOperatorPow() {
        assertTrue(ArithmeticUtility.isOperator('^'));
    }

    @Test
    public void testIsOperatorModulo() {
        assertFalse(ArithmeticUtility.isOperator('%'));
    }

    @Test
    public void testIsOperatorWhiteSpace() {
        assertFalse(ArithmeticUtility.isOperator(' '));
    }

    @Test
    public void testIsOperatorX() {
        assertFalse(ArithmeticUtility.isOperator('x'));
    }

    @Test
    public void testIsTermOperatorPlus() {
        assertFalse(ArithmeticUtility.Operator.ADDITION.isTermOperator());
    }

    @Test
    public void testIsTermOperatorMinus() {
        assertFalse(ArithmeticUtility.Operator.SUBTRACTION.isTermOperator());
    }

    @Test
    public void testIsTermOperatorMultiply() {
        assertTrue(ArithmeticUtility.Operator.MULTIPLICATION.isTermOperator());
    }

    @Test
    public void testIsTermOperatorDivide() {
        assertTrue(ArithmeticUtility.Operator.DIVISION.isTermOperator());
    }

    @Test
    public void testIsTermOperatorPow() {
        assertFalse(ArithmeticUtility.Operator.EXPONENTIATION.isTermOperator());
    }

    @Test
    public void testIsExprOperatorPlus() {
        assertTrue(ArithmeticUtility.Operator.ADDITION.isExprOperator());
    }

    @Test
    public void testIsExprOperatorMinus() {
        assertTrue(ArithmeticUtility.Operator.SUBTRACTION.isExprOperator());
    }

    @Test
    public void testIsExprOperatorMultiply() {
        assertFalse(ArithmeticUtility.Operator.MULTIPLICATION.isExprOperator());
    }

    @Test
    public void testIsExprOperatorDivide() {
        assertFalse(ArithmeticUtility.Operator.DIVISION.isExprOperator());
    }

    @Test
    public void testIsExprOperatorPow() {
        assertFalse(ArithmeticUtility.Operator.EXPONENTIATION.isExprOperator());
    }

    @Test
    public void testIsPowerOperatorPlus() {
        assertFalse(ArithmeticUtility.Operator.ADDITION.isPowerOperator());
    }

    @Test
    public void testIsPowerOperatorMinus() {
        assertFalse(ArithmeticUtility.Operator.SUBTRACTION.isPowerOperator());
    }

    @Test
    public void testIsPowerOperatorMultiply() {
        assertFalse(ArithmeticUtility.Operator.MULTIPLICATION.isPowerOperator());
    }

    @Test
    public void testIsPowerOperatorDivide() {
        assertFalse(ArithmeticUtility.Operator.DIVISION.isPowerOperator());
    }

    @Test
    public void testIsPowerOperatorPow() {
        assertTrue(ArithmeticUtility.Operator.EXPONENTIATION.isPowerOperator());
    }

    @Test
    public void testCharToOperatorPlus() {
        assertTrue(ArithmeticUtility.charToOperator('+').equals(ArithmeticUtility.Operator.ADDITION));
    }

    @Test
    public void testCharToOperatorMinus() {
        assertTrue(ArithmeticUtility.charToOperator('-').equals(ArithmeticUtility.Operator.SUBTRACTION));
    }

    @Test
    public void testCharToOperatorMultiply() {
        assertTrue(ArithmeticUtility.charToOperator('*').equals(ArithmeticUtility.Operator.MULTIPLICATION));
    }

    @Test
    public void testCharToOperatorDivide() {
        assertTrue(ArithmeticUtility.charToOperator('/').equals(ArithmeticUtility.Operator.DIVISION));
    }

    @Test
    public void testCharToOperatorPow() {
        assertTrue(ArithmeticUtility.charToOperator('^').equals(ArithmeticUtility.Operator.EXPONENTIATION));
    }
}