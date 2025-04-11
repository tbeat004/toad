package toad.commands.math.postprocess;
import org.junit.jupiter.api.Test;
import toad.commands.math.MathSyntaxException;
import toad.commands.math.token.ExpressionTokenizer;
import toad.commands.math.token.MathToken;
import toad.commands.math.token.MathTokenType;
import toad.commands.math.validate.ExpressionValidator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class TestMathPostProcessor {

    private List<String> extractLexemes(List<MathToken> tokens) {
        List<String> lexemes = new ArrayList<>();
        for (MathToken token : tokens) {
            if (token.type() == MathTokenType.UNARY_MINUS) {
                lexemes.add("-u");
            } else {
                lexemes.add(token.lexeme());
            }
        }
        return lexemes;
    }

    private void assertProcessed(String input, List<String> expectedLexemes) throws MathSyntaxException {
        ExpressionTokenizer tokenizer = new ExpressionTokenizer(input);
        ArrayList<MathToken> tokens = tokenizer.tokenize();
        ExpressionValidator.validate(tokens);
        List<MathToken> processed = MathPostProcessor.postProcess(tokens);
        assertEquals(expectedLexemes, extractLexemes(processed));
    }

    private void assertTokenTypes(List<MathToken> tokens, MathTokenType... expectedTypes) {
        assertEquals(expectedTypes.length, tokens.size());
        for (int i = 0; i < expectedTypes.length; i++) {
            assertEquals(expectedTypes[i], tokens.get(i).type());
        }
    }

    @Test
    public void testImplicitMultiplication_2x() throws Exception {
        assertProcessed("2x", List.of("2", "*", "x"));
    }

    @Test
    public void testImplicitMultiplication_Parens() throws Exception {
        assertProcessed("(x+1)(x-1)", List.of("(", "x", "+", "1", ")", "*", "(", "x", "-", "1", ")"));
    }

    @Test
    public void testImplicitMultiplication_NumberParen() throws Exception {
        assertProcessed("3(x+2)", List.of("3", "*", "(", "x", "+", "2", ")"));
    }

    @Test
    public void testImplicitMultiplication_x3() throws Exception {
        assertProcessed("x3", List.of("x", "*", "3"));
    }

    @Test
    public void testImplicitMultiplication_ParenNumber() throws Exception {
        assertProcessed("(x+1)2", List.of("(", "x", "+", "1", ")", "*", "2"));
    }

    @Test
    public void testImplicitMultiplication_Function() throws Exception {
        assertProcessed("2sin(x)", List.of("2", "*", "sin", "(", "x", ")"));
    }

    @Test
    public void testUnaryMinusAlone() throws Exception {
        ArrayList<MathToken> tokens = new ExpressionTokenizer("-x").tokenize();
        ExpressionValidator.validate(tokens);
        List<MathToken> processed = MathPostProcessor.postProcess(tokens);

        assertEquals(2, processed.size());
        assertEquals(MathTokenType.UNARY_MINUS, processed.get(0).type());
        assertEquals(MathTokenType.VARIABLE, processed.get(1).type());
    }

    @Test
    public void testDoubleNegative() throws Exception {
        ArrayList<MathToken> tokens = new ExpressionTokenizer("x - -3").tokenize();
        ExpressionValidator.validate(tokens);
        List<MathToken> processed = MathPostProcessor.postProcess(tokens);

        assertEquals(4, processed.size());
        assertEquals(MathTokenType.VARIABLE, processed.get(0).type());
        assertEquals(MathTokenType.BINARY_MINUS, processed.get(1).type());
        assertEquals(MathTokenType.UNARY_MINUS, processed.get(2).type());
        assertEquals(MathTokenType.NUMBER, processed.get(3).type());
    }

    @Test
    public void testUnaryWithImplicitMultiplication() throws Exception {
        ArrayList<MathToken> tokens = new ExpressionTokenizer("-2(x+1)").tokenize();
        ExpressionValidator.validate(tokens);
        List<MathToken> processed = MathPostProcessor.postProcess(tokens);

        assertTokenTypes(processed,
                MathTokenType.UNARY_MINUS,
                MathTokenType.NUMBER,
                MathTokenType.OPERATOR,
                MathTokenType.PAREN_OPEN,
                MathTokenType.VARIABLE,
                MathTokenType.OPERATOR,
                MathTokenType.NUMBER,
                MathTokenType.PAREN_CLOSE
        );
    }
}

