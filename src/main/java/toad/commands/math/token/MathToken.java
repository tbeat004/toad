package toad.commands.math.token;

public record MathToken(MathTokenType type, String lexeme, int position) {}
