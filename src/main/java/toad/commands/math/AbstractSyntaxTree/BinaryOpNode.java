package toad.commands.math.AbstractSyntaxTree;

import toad.commands.math.token.MathToken;

public class BinaryOpNode extends MathNode{
    String op;
    MathNode left;
    MathNode right;

    public BinaryOpNode(MathNode left, MathToken op, MathNode right) {
        this.left = left;
        this.right = right;
        this.op = op.lexeme();
    }

    @Override
    public String toString() {
        return left + " " + op + " " + right;
    }

    public String op() {
        return op;
    }

    public MathNode left() {
        return left;
    }

    public MathNode right() {
        return right;
    }
}
