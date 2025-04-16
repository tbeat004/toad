package toad.commands.math.AbstractSyntaxTree;

import java.util.ArrayList;

public class FunctionNode extends MathNode{
    private String name;
    private ArrayList<MathNode> arguments = new ArrayList<>();

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
        double result = arguments.get(0).evaluate();

        switch (getName()) {
            case "sin":
                return Math.sin(result);
            case "cos":
                return Math.cos(result);
            case "tan":
                return Math.tan(result);
            case "sqrt":
                return Math.sqrt(result);
            case "asin":
                return Math.asin(result);
            case "acos":
                return Math.acos(result);
            case "atan":
                return Math.atan(result);
            case "log":
                return Math.log(result);
            case "log10":
                return Math.log10(result);
            case "abs":
                return Math.abs(result);

        }
        return 0;
    }

    public String getName() {
        return name;
    }

    public MathNode argument() {
        return arguments.get(0);
    }

    @Override
    public MathNode simplify() {
        MathNode argument = argument().simplify();
        return super.simplify();
    }
}
