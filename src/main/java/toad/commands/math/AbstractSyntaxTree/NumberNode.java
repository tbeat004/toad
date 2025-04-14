package toad.commands.math.AbstractSyntaxTree;


public class NumberNode extends MathNode{
    private double value;

    public NumberNode(double v) {
        this.value = v;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public double evaluate() {
        return getValue();
    }

    public double getValue() {
        return value;
    }

}
