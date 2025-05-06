package toad.commands.math.AbstractSyntaxTree;


public class NumberNode extends MathNode{
    private double value;
    private String constant;

    public NumberNode(double v) {
        if (v == Math.E) {
            this.value = v;
            this.constant = "e";
            return;
        }
        if (v == Math.PI) {
            this.value = v;
            this.constant = "pi";
            return;
        }
        this.value = v;
        this.constant = String.valueOf(v);
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

    public String getConstant() {
        return constant;
    }



}
