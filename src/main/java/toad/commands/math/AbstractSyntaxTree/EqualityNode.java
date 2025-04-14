package toad.commands.math.AbstractSyntaxTree;

public class EqualityNode extends MathNode {
    private MathNode left;
    private MathNode right;

    public EqualityNode(MathNode left, MathNode right) {
        this.left = left;
        this.right = right;
    }


    @Override
    public String toString() {
        return left + " = " + right;
    }

    /**
     * Evaluates both sides of an '=' node recursively
     * @return 1 if both sides are equal are returns 0 if both sides are NOT equal
     */
    @Override
    public double evaluate() {
        return (left.evaluate() == right.evaluate() ? 1 : 0);
    }

    public MathNode left() {
        return left;
    }

    public MathNode right() {
        return right;
    }
}
