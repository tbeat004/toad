package toad.commands.math.AbstractSyntaxTree;

import java.util.ArrayList;

public class FunctionNode extends MathNode{
    String name;
    ArrayList<MathNode> arguments = new ArrayList<>();

    public FunctionNode(String functionName, MathNode argument) {
        this.name = functionName.toLowerCase();
        this.arguments.add(argument);
    }

    @Override
    public String toString() {
        return name + " " + arguments.size();
    }

    @Override
    public double evaluate() {
        switch (getName()) {
            case "sin":
                return Math.sin(arguments.get(0).evaluate());
            case "cos":
                return Math.cos(arguments.get(0).evaluate());
            case "tan":
                return Math.tan(arguments.get(0).evaluate());
            case "sqrt":
                return Math.sqrt(arguments.get(0).evaluate());
            case "asin":
                return Math.asin(arguments.get(0).evaluate());
            case "acos":
                return Math.acos(arguments.get(0).evaluate());
            case "atan":
                return Math.atan(arguments.get(0).evaluate());
            case "log":
                return Math.log(arguments.get(0).evaluate());
            case "log10":
                return Math.log10(arguments.get(0).evaluate());
            case "abs":
                return Math.abs(arguments.get(0).evaluate());

        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public MathNode argument() {
        return arguments.get(0);
    }
}
