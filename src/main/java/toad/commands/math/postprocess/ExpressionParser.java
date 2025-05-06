package toad.commands.math.postprocess;

import toad.commands.math.AbstractSyntaxTree.*;
import toad.commands.math.token.MathToken;
import toad.commands.math.token.MathTokenType;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Set;

import static toad.commands.math.token.MathTokenType.UNARY_MINUS;

public class ExpressionParser {

    private final ArrayList<MathToken> tokens;
    private int current = 0;

    public ExpressionParser(ArrayList<MathToken> tokens) {
        this.tokens = tokens;
    }

    public MathNode parse() {
        return parseEquality();
    }

    private MathNode parsePrimary() {
        if (isAtEnd()) return null;
        MathToken token = peek();

        return switch (token.type()) {
            case NUMBER -> {
                advance();
                if (Objects.equals(token.lexeme(), "pi")) {yield new NumberNode(Math.PI);}
                if (Objects.equals(token.lexeme(), "e")) {yield new NumberNode(Math.E);}

                yield new NumberNode(Double.parseDouble(token.lexeme()));
            }
            case VARIABLE -> {
                advance();
                yield new VariableNode(token.lexeme());
            }
            case FUNCTION -> {
                String name = token.lexeme();
                advance(); // skip function name

                if (!match(MathTokenType.PAREN_OPEN)) {
                    throw new IllegalStateException("Expected '(' after function name: " + name);
                }

                MathNode arg = parseEquality(); // fully parse argument

                if (!match(MathTokenType.PAREN_CLOSE)) {
                    throw new IllegalStateException("Expected ')' after function argument to: " + name);
                }

                yield new FunctionNode(name, arg);
            }
            case PAREN_OPEN -> {
                advance(); // skip '('
                MathNode inner = parseEquality(); // parse entire grouped expr
                if (!match(MathTokenType.PAREN_CLOSE)) {
                    throw new IllegalStateException("Expected ')' to close grouped expression.");
                }
                yield inner; // this preserves grouping by making it an atomic subtree
            }
            default -> null;
        };
    }

    private MathNode parseUnary() {
        if (match(UNARY_MINUS)) {
            MathToken minus = previous();
            MathNode right = parseUnary(); // keep this recursive
            return new UnaryOpNode(minus.lexeme(), right);
        }
        return parsePrimary(); // just call primary
    }

    private MathNode parseExponent() {
        MathNode base = parseUnary();

        while (!isAtEnd() && peek().type() == MathTokenType.EXPONENT) {
            MathToken op = advance(); // '^'
            MathNode exponent = parseExponent(); // right-associative
            base = new BinaryOpNode(base, op, exponent);
        }

        return base;
    }

    private MathNode parseMultiplicative() {
        MathNode left = parseExponent();
        while (!isAtEnd() &&
                peek().type() == MathTokenType.OPERATOR &&
                Set.of("*", "/", "%").contains(peek().lexeme())) {

            MathToken op = advance();
            MathNode right = parseExponent();
            left = new BinaryOpNode(left, op, right);
        }
        return left;
    }

    private MathNode parseAdditive() {
        MathNode left = parseMultiplicative();
        while (!isAtEnd()) {
            MathToken token = peek();
            if ((token.type() == MathTokenType.OPERATOR && Set.of("+", "-").contains(token.lexeme())) ||
                    token.type() == MathTokenType.BINARY_MINUS) {

                MathToken op = advance();
                MathNode right = parseMultiplicative();
                left = new BinaryOpNode(left, op, right);
            } else break;
        }
        return left;
    }

    private MathNode parseEquality() {
        MathNode left = parseAdditive();
        if (!isAtEnd() && peek().type() == MathTokenType.EQUALS) {
            advance();
            MathNode right = parseEquality();
            return new EqualityNode(left, right);
        }
        return left;
    }

    // ---------- Helpers ----------

    private boolean isAtEnd() {
        return current >= tokens.size();
    }

    private MathToken peek() {
        return tokens.get(current);
    }

    private MathToken advance() {
        return tokens.get(current++);
    }

    private MathToken previous() {
        return tokens.get(current - 1);
    }

    private boolean match(MathTokenType expected) {
        if (isAtEnd()) return false;
        if (peek().type() == expected) {
            advance();
            return true;
        }
        return false;
    }

    public int getCurrent() {
        return current;
    }
}
