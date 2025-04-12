package toad.commands.math.AbstractSyntaxTree;

import java.util.ArrayList;

public class FunctionNode extends MathNode{
    String name;
    ArrayList<MathNode> arguments = new ArrayList<>();

    public FunctionNode(String functionName, MathNode argument) {
        this.name = functionName;
        this.arguments.add(argument);
    }

    @Override
    public String toString() {
        return name + " " + arguments.size();
    }

    public String getName() {
        return name;
    }

    public MathNode argument() {
        return arguments.get(0);
    }
}
