package toad.commands.math.AbstractSyntaxTree;

public class VariableNode extends MathNode{
    private String name;

    public VariableNode(String variable) {
        this.name = variable;
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public double evaluate() {
        return 0;
    }

    public String getName() {
        return name;
    }
}
