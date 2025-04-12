package toad.commands.math.AbstractSyntaxTree;

public class VariableNode extends MathNode{
    String name;

    public VariableNode(String variable) {
        this.name = variable;
    }

    @Override
    public String toString() {
        return name;
    }

    public String getName() {
        return name;
    }
}
