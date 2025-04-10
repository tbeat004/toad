package toad.commands.math;

public record MathToken(MathTokenType type, String lexeme, int position) {}
