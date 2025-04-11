package toad.commands.math;

import java.util.ArrayList;
import java.util.Set;

public class ExpressionValidator {

    private static final MathTokenType PAREN_OPEN = MathTokenType.PAREN_OPEN;
    private static final MathTokenType NUMBER = MathTokenType.NUMBER;
    private static final MathTokenType OPERATOR = MathTokenType.OPERATOR;
    private static final MathTokenType VARIABLE = MathTokenType.VARIABLE;
    private static final MathTokenType FUNCTION = MathTokenType.FUNCTION;
    private static final MathTokenType PAREN_CLOSE = MathTokenType.PAREN_CLOSE;
    private static final MathTokenType COMMA = MathTokenType.COMMA;
    private static final MathTokenType EQUALS = MathTokenType.EQUALS;
    private static final MathTokenType EXPONENT = MathTokenType.EXPONENT;
    private static final MathTokenType BINARY_MINUS = MathTokenType.BINARY_MINUS;
    private static final MathTokenType UNARY_MINUS = MathTokenType.UNARY_MINUS;

    public static void validate(ArrayList<MathToken> tokens) throws MathSyntaxException {
        int equalsIndex = -1;
        for (int i = 0; i < tokens.size(); i++) {
            if (tokens.get(i).type() == MathTokenType.EQUALS) {
                if (equalsIndex != -1)
                    throw new MathSyntaxException("Only one '=' allowed in equation");
                equalsIndex = i;
            }
        }

        if (equalsIndex == -1) {
            validateExpression(tokens); // expression only
        } else {
            // Split into two halves
            ArrayList<MathToken> left = new ArrayList<>(tokens.subList(0, equalsIndex));
            ArrayList<MathToken> right = new ArrayList<>(tokens.subList(equalsIndex + 1, tokens.size()));

            if (left.isEmpty() || right.isEmpty())
                throw new MathSyntaxException("Invalid equation format around '='");

            validateExpression(left);
            validateExpression(right);
        }
    }

    private static void validateExpression(ArrayList<MathToken> tokens) throws MathSyntaxException {
        int parenthesisDepth = 0;
        MathToken prevToken = null;
        MathTokenType prevType = null;
        MathTokenType currType;
        // Position of original token BEFORE tokenization (since it ignores whitespaces it won't give accurate position)
        int currPos;
        // Position of token in array (Ensures that internally we can check its position based on the tokens themselves)
        int currArrayPos = 0;



        for (MathToken currToken : tokens) {

            currType = currToken.type();
            currPos = currToken.position();
            if (prevToken != null) {
                prevType = prevToken.type();
            }

            // DISALLOW COMMA USE FOR NOW UNTIL I NEED IT (MAYBE)
            if (currType == COMMA) throw new MathSyntaxException("Unexpected Input at position " + currPos + ": " + currToken.lexeme());

            // Handle parenthesis (counter)
            if (currType == PAREN_OPEN) {
                parenthesisDepth++;
            } else if (currType == PAREN_CLOSE) {
                parenthesisDepth--;
                if (parenthesisDepth < 0) throw new MathSyntaxException
                        ("Unmatched closing parenthesis ')' at position: " + currPos);
            }

            // Makes sure functions have an opening parenthesis after
            if (prevToken != null && prevType == FUNCTION) {
                if (currType != PAREN_OPEN) {
                    throw new MathSyntaxException("Function not followed by '(' at position: " + currPos);
                }
            }

            // Empty ()
            if (prevToken != null && prevType == PAREN_OPEN && currType == PAREN_CLOSE) {
                throw new MathSyntaxException("Empty parentheses '()' at position " + currPos);
            }

            // Invalid sequences
            if (prevToken != null) {
                // NUMBER, NUMBER
                if (prevType == NUMBER && currType == NUMBER) throw new MathSyntaxException("Invalid Syntax: NUMBER NUMBER at position: " + currPos);

                // OPERATOR LOGIC
                if (prevType == OPERATOR) {
                    // OPERATOR, OPERATOR error (Unless '-, -', Handled separately within)
                    if (currType == OPERATOR) {
                        if (prevToken.lexeme().equals("-") && currToken.lexeme().equals("-")) {
                            if (currArrayPos == 1) throw new MathSyntaxException("Expression can't start with '--'");
                            if (currArrayPos + 1 == tokens.size()) throw new MathSyntaxException("Expression can't end with '--'");
                            MathTokenType nextType = tokens.get(currArrayPos + 1).type();
                            if (!Set.of(NUMBER, VARIABLE, PAREN_OPEN, FUNCTION).contains(nextType)) {
                                throw new MathSyntaxException("Invalid Syntax: Expecting NUMBER, VARIABLE, '(' or FUNCTION after '--' at position: " + currPos);
                            }

                        } else
                            throw new MathSyntaxException("Invalid Syntax: OPERATOR OPERATOR at position: " + currPos);
                    }

                    if (Set.of(COMMA, EQUALS, PAREN_CLOSE).contains(currType)) throw new MathSyntaxException("Invalid Syntax: OPERATOR " + currType + " at position: " + currPos);
                }

                // Exponent LOGIC
                if (prevType == EXPONENT && Set.of(OPERATOR, PAREN_CLOSE, COMMA, EQUALS, EXPONENT).contains(currType)) throw new MathSyntaxException("Invalid Syntax: EXPONENT " + currType + " at position: " + currPos);

            }
            prevToken = currToken;
            ++currArrayPos;
        }
        // Makes sure we have no extra '('
        if (parenthesisDepth != 0) {
            throw new MathSyntaxException("Unmatched opening parenthesis");
        }
        // Makes sure it starts and ends with valid tokens
        MathToken first = tokens.get(0);
        MathToken last = tokens.get(tokens.size() - 1);

        if (Set.of(OPERATOR, COMMA, EQUALS, PAREN_CLOSE).contains(first.type())) {
            throw new MathSyntaxException("Expression cannot start with: " + first.lexeme());
        }
        if (Set.of(OPERATOR, COMMA, EQUALS, FUNCTION, PAREN_OPEN).contains(last.type())) {
            throw new MathSyntaxException("Expression cannot end with: " + last.lexeme());
        }


    }

}
