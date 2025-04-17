package toad.commands.math.token;

import toad.commands.math.MathSyntaxException;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ExpressionTokenizer {
    
    private final String expression;
    private int position = 0;

    final Pattern numberPattern = Pattern.compile("^(\\d*\\.\\d+|\\d+)");
    final Pattern variablePattern = Pattern.compile("^(\\w)");
    final Pattern operatorPattern = Pattern.compile("^([-+/*%])");
    final Pattern functionPattern = Pattern.compile("^(sin|cos|tan|sqrt|asin|acos|atan|log|log10|abs)");

    //Whitespace
    final Pattern whitespacePattern = Pattern.compile("^\\s+");

    public ExpressionTokenizer(String expression) {
        this.expression = expression;
    }

    public ArrayList<MathToken> tokenize() throws MathSyntaxException {
        ArrayList<MathToken> tokens = new ArrayList<>();

        while (position < expression.length()) {
            String current = expression.substring(position);
            Match match = match(current);

            if (match == null) {
                throw new MathSyntaxException("Unexpected Input at position " + position + ": " + current.charAt(0));
            }

            if (match.type() != null) {
                tokens.add(new MathToken(match.type(), match.value(), position));
            }

            position += match.length();
        }

        return tokens;
    }

    /**
     * @return position (will always be at last position after tokenize() but will halt during exception
     */
    public int getPosition() {
        return position + 1;
    }

    private Match match(String input) {
        Matcher m;


        // 1. Whitespace
        m = whitespacePattern.matcher(input);
        if (m.find()) return new Match(null, "", m.end()); // skip

        // 2. Number
        m = numberPattern.matcher(input);
        if (m.find()) return new Match(MathTokenType.NUMBER, m.group(), m.end());

        // 3. Function
        m = functionPattern.matcher(input);
        if (m.find()) return new Match(MathTokenType.FUNCTION, m.group(), m.end());

        // 4. Parentheses
        if (input.startsWith("(")) return new Match(MathTokenType.PAREN_OPEN, "(", 1);
        if (input.startsWith(")")) return new Match(MathTokenType.PAREN_CLOSE, ")", 1);

        // 5. Exponent
        if (input.startsWith("^")) return new Match(MathTokenType.EXPONENT, "^", 1);

        // 6. Operators
        m = operatorPattern.matcher(input);
        if (m.find()) return new Match(MathTokenType.OPERATOR, m.group(), m.end());

        // 7. Equals
        if (input.startsWith("=")) return new Match(MathTokenType.EQUALS, "=", 1);

        // 8. Comma
        if (input.startsWith(",")) return new Match(MathTokenType.COMMA, ",", 1);

        // 9. Variable (single character)
        m = variablePattern.matcher(input);
        if (m.find()) return new Match(MathTokenType.VARIABLE, m.group(), m.end());

        // 10. Nothing matched
        return null;
    }
}



