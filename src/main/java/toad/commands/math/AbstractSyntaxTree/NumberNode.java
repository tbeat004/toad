package toad.commands.math.AbstractSyntaxTree;


public class NumberNode extends MathNode{
    double value;

    public NumberNode(double v) {
        this.value = v;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
    public double getValue() {
        return value;
    }

}
