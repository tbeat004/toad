package toad.commands.math.AbstractSyntaxTree;


public class NumberNode extends MathNode{
    private double value;
    private String constant;

    public NumberNode(double v) {
        this.value = v;
    }
    public NumberNode(String c) {
        
        switch(c) {
            case "pi":

            case "e":
                retu
        }

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
