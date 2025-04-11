package toad.commands.math.token;

public enum MathTokenType {
    NUMBER, OPERATOR, VARIABLE, FUNCTION, PAREN_OPEN, PAREN_CLOSE, COMMA, EQUALS, EXPONENT,
    //Post-processing steps to determine
    UNARY_MINUS, BINARY_MINUS,
}
