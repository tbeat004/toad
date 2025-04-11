package toad.commands.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestExpressionValidator {

    @Test
    public void testValidExpressionWithFunctions() throws MathSyntaxException {
        String expr = "2 * (x + 3) + sin(x)";
        var tokens = new ExpressionTokenizer(expr).tokenize();
        assertDoesNotThrow(() -> ExpressionValidator.validate(tokens));
    }

    @Test
    public void testValidEquation() throws MathSyntaxException {
        String expr = "3x^2 + 4x = x * (x + 1)";
        var tokens = new ExpressionTokenizer(expr).tokenize();
        assertDoesNotThrow(() -> ExpressionValidator.validate(tokens));
    }

    @Test
    public void testInvalidUnmatchedParentheses() throws MathSyntaxException {
        String expr = "2 * (x + 3";
        var tokens = new ExpressionTokenizer(expr).tokenize();
        MathSyntaxException ex = assertThrows(MathSyntaxException.class, () -> ExpressionValidator.validate(tokens));
        assertTrue(ex.getMessage().toLowerCase().contains("unmatched"));
    }

    @Test
    public void testInvalidMultipleEquals() throws MathSyntaxException {
        String expr = "x = y = z";
        var tokens = new ExpressionTokenizer(expr).tokenize();
        MathSyntaxException ex = assertThrows(MathSyntaxException.class, () -> ExpressionValidator.validate(tokens));
        assertTrue(ex.getMessage().contains("="));
    }

    @Test
    public void testValidDoubleNegative() throws MathSyntaxException {
        String expr = "x - -3";
        var tokens = new ExpressionTokenizer(expr).tokenize();
        assertDoesNotThrow(() -> ExpressionValidator.validate(tokens));
    }

    @Test
    public void testValidExponentChain() throws MathSyntaxException {
        String expr = "2 ^ x ^ 2";
        var tokens = new ExpressionTokenizer(expr).tokenize();
        assertDoesNotThrow(() -> ExpressionValidator.validate(tokens));
    }

    @Test
    public void testInvalidEmptyFunctionCall() throws MathSyntaxException {
        String expr = "sin()";
        var tokens = new ExpressionTokenizer(expr).tokenize();
        MathSyntaxException ex = assertThrows(MathSyntaxException.class, () -> ExpressionValidator.validate(tokens));
        assertTrue(ex.getMessage().contains("Empty parentheses"));
    }

    @Test
    public void testInvalidExponentFollowedByOperator() throws MathSyntaxException {
        String expr = "2 ^ * 3";
        var tokens = new ExpressionTokenizer(expr).tokenize();
        MathSyntaxException ex = assertThrows(MathSyntaxException.class, () -> ExpressionValidator.validate(tokens));
        assertTrue(ex.getMessage().contains("EXPONENT OPERATOR"));
    }



}
