package toad.commands.math.postprocess;

import toad.commands.math.token.MathToken;
import toad.commands.math.token.MathTokenType;

import java.util.ArrayList;
import java.util.Set;

public class MathPostProcessor {

    private static final MathTokenType BINARY_MINUS = MathTokenType.BINARY_MINUS;
    private static final MathTokenType UNARY_MINUS = MathTokenType.UNARY_MINUS;
    private static final MathTokenType PAREN_OPEN = MathTokenType.PAREN_OPEN;
    private static final MathTokenType NUMBER = MathTokenType.NUMBER;
    private static final MathTokenType OPERATOR = MathTokenType.OPERATOR;
    private static final MathTokenType VARIABLE = MathTokenType.VARIABLE;
    private static final MathTokenType FUNCTION = MathTokenType.FUNCTION;
    private static final MathTokenType PAREN_CLOSE = MathTokenType.PAREN_CLOSE;
    private static final MathTokenType COMMA = MathTokenType.COMMA;
    private static final MathTokenType EQUALS = MathTokenType.EQUALS;
    private static final MathTokenType EXPONENT = MathTokenType.EXPONENT;


    public static ArrayList<MathToken> postProcess(ArrayList<MathToken> tokens) {
        MathToken prevToken = null;
        ArrayList<MathToken> postProcessResult = new ArrayList<>();
        for (MathToken token : tokens) {
            if (prevToken != null) {
                if (token.lexeme().equals("-")) {
                    // If it follows any of these, it's binary (e.g., 5 - 3)
                    if (Set.of(NUMBER, VARIABLE, PAREN_CLOSE).contains(prevToken.type())) {
                        postProcessResult.add(new MathToken(BINARY_MINUS, token.lexeme(), token.position()));
                    } else {
                        postProcessResult.add(new MathToken(UNARY_MINUS, token.lexeme(), token.position()));
                    }

                } else {
                    // Implicit-Multiplication Check
                    if (Set.of(NUMBER, VARIABLE, PAREN_CLOSE).contains(prevToken.type()) &&
                        Set.of(VARIABLE, PAREN_OPEN, FUNCTION, NUMBER).contains(token.type())) {
                        postProcessResult.add(new MathToken(OPERATOR, "*", token.position()));
                    }

                    postProcessResult.add(token);
                }

            } else if (token.lexeme().equals("-")) {
                postProcessResult.add(new MathToken(UNARY_MINUS, token.lexeme(), token.position()));
            } else postProcessResult.add(token);

            prevToken = token;
        }


        return postProcessResult;
    }
}
