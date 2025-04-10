package toad.commands.math;

import org.junit.jupiter.api.Test;

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
}
