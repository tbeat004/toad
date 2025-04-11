package toad.commands.math.token;

import org.junit.jupiter.api.Test;
import toad.commands.math.MathSyntaxException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestExpressionTokenizer {

    @Test
    public void testBasicExpression() throws MathSyntaxException {
        String expr = "3 + 4 * x - sin(90)";
        ExpressionTokenizer tokenizer = new ExpressionTokenizer(expr);
        List<MathToken> tokens = tokenizer.tokenize();

        assertEquals(10, tokens.size());

        assertEquals(MathTokenType.NUMBER, tokens.get(0).type());
        assertEquals("3", tokens.get(0).lexeme());

        assertEquals(MathTokenType.OPERATOR, tokens.get(1).type());
        assertEquals("+", tokens.get(1).lexeme());

        assertEquals(MathTokenType.NUMBER, tokens.get(2).type());
        assertEquals("4", tokens.get(2).lexeme());

        assertEquals(MathTokenType.OPERATOR, tokens.get(3).type());
        assertEquals("*", tokens.get(3).lexeme());

        assertEquals(MathTokenType.VARIABLE, tokens.get(4).type());
        assertEquals("x", tokens.get(4).lexeme());

        assertEquals(MathTokenType.OPERATOR, tokens.get(5).type());
        assertEquals("-", tokens.get(5).lexeme());

        assertEquals(MathTokenType.FUNCTION, tokens.get(6).type());
        assertEquals("sin", tokens.get(6).lexeme());

        assertEquals(MathTokenType.PAREN_OPEN, tokens.get(7).type());
        assertEquals("(", tokens.get(7).lexeme());

        assertEquals(MathTokenType.NUMBER, tokens.get(8).type());
        assertEquals("90", tokens.get(8).lexeme());

        // Optional: Add assert for closing paren
        assertEquals(MathTokenType.PAREN_CLOSE, tokens.get(9).type());
        assertEquals(")", tokens.get(9).lexeme());
    }

    @Test
    public void testComplexExpression() throws MathSyntaxException {
        String expr = "2sin(x) + 3.5*(4 - y)^2";
        ExpressionTokenizer tokenizer = new ExpressionTokenizer(expr);
        List<MathToken> tokens = tokenizer.tokenize();

        assertEquals(15, tokens.size());

        assertEquals(MathTokenType.NUMBER, tokens.get(0).type());
        assertEquals("2", tokens.get(0).lexeme());

        assertEquals(MathTokenType.FUNCTION, tokens.get(1).type());
        assertEquals("sin", tokens.get(1).lexeme());

        assertEquals(MathTokenType.PAREN_OPEN, tokens.get(2).type());
        assertEquals("(", tokens.get(2).lexeme());

        assertEquals(MathTokenType.VARIABLE, tokens.get(3).type());
        assertEquals("x", tokens.get(3).lexeme());

        assertEquals(MathTokenType.PAREN_CLOSE, tokens.get(4).type());

        assertEquals(MathTokenType.OPERATOR, tokens.get(5).type());
        assertEquals("+", tokens.get(5).lexeme());

        assertEquals(MathTokenType.NUMBER, tokens.get(6).type());
        assertEquals("3.5", tokens.get(6).lexeme());

        assertEquals(MathTokenType.OPERATOR, tokens.get(7).type());
        assertEquals("*", tokens.get(7).lexeme());

        assertEquals(MathTokenType.PAREN_OPEN, tokens.get(8).type());

        assertEquals(MathTokenType.NUMBER, tokens.get(9).type());
        assertEquals("4", tokens.get(9).lexeme());

        assertEquals(MathTokenType.OPERATOR, tokens.get(10).type());
        assertEquals("-", tokens.get(10).lexeme());

        assertEquals(MathTokenType.VARIABLE, tokens.get(11).type());
        assertEquals("y", tokens.get(11).lexeme());

        assertEquals(MathTokenType.PAREN_CLOSE, tokens.get(12).type());

        assertEquals(MathTokenType.EXPONENT, tokens.get(13).type());
        assertEquals("^", tokens.get(13).lexeme());

        assertEquals(MathTokenType.NUMBER, tokens.get(14).type());
        assertEquals("2", tokens.get(14).lexeme());
    }

    @Test
    public void testIllegalCharacter() {
        String expr = "4 + 5@";
        ExpressionTokenizer tokenizer = new ExpressionTokenizer(expr);

        MathSyntaxException ex = assertThrows(MathSyntaxException.class, tokenizer::tokenize);
        assertTrue(ex.getMessage().contains("@"));
        assertEquals(6, tokenizer.getPosition());
    }
}
