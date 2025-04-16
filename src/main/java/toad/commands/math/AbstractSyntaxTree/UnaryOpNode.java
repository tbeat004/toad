package toad.commands.math.AbstractSyntaxTree;

import toad.commands.math.token.MathToken;

public class UnaryOpNode extends MathNode{
    private String op;
    private MathNode operand;

    public UnaryOpNode(MathToken opToken, MathNode operand) {
        this.op = opToken.lexeme();       // Capture the operator symbol like "-"
        this.operand = operand;           // The operand (right side of unary expression)
    }

    @Override
    public String toString() {
        return op + " " + operand;
    }

    @Override
    public double evaluate() {
        return -operand.evaluate();
    }

    public String op() {
        return op;
    }
    public MathNode child() {
        return operand;
    }

    @Override
    public MathNode simplify() {
        operand = operand.simplify();
        return super.simplify();
    }
}
