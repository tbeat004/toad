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

    @Override
    public double evaluate() {
        return 0;
    }
}
