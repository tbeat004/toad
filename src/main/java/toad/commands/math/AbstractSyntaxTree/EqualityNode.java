package toad.commands.math.AbstractSyntaxTree;

public class EqualityNode extends MathNode {
    private final MathNode left;
    private final MathNode right;

    public EqualityNode(MathNode left, MathNode right) {
        this.left = left;
        this.right = right;
    }

    public MathNode getLeft() {
        return left;
    }

    public MathNode getRight() {
        return right;
    }

    @Override
    public String toString() {
        return left + " = " + right;
    }

    public MathNode left() {
        return left;
    }

    public MathNode right() {
        return right;
    }
}
