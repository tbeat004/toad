package toad.commands.math.AbstractSyntaxTree;

public class ConstantNode extends MathNode{

    private final String constant;
    private final int value;


    public ConstantNode(String Constant, int value) {
        this.constant = Constant;
        this.value = value;
    }
    @Override
    public String toString() {
        return "";
    }
    public String getConstant() {
        return constant;
    }
    public int getValue() {
        return value;
    }


    @Override
    public double evaluate() {
        return 0;
    }
}
