package toad.commands.math.AbstractSyntaxTree;

import toad.commands.math.token.MathToken;

public class BinaryOpNode extends MathNode{
    private String op;
    private MathNode left;
    private MathNode right;

    public BinaryOpNode(MathNode left, MathToken token, MathNode right) {
        this.left = left;
        this.right = right;
        this.op = token.lexeme();
    }

    @Override
    public String toString() {
        return left + " " + op + " " + right;
    }

    public String op() {
        return op;
    }

    public MathNode left() {
        return left;
    }

    public MathNode right() {
        return right;
    }

    @Override
    public double evaluate() {
        double leftVal = left.evaluate();
        double rightVal = right.evaluate();

        switch(op){
            case "+":
                return leftVal + rightVal;
            case "-":
                return leftVal - rightVal;
            case "/":
                return leftVal / rightVal;
            case "*":
                return leftVal * rightVal;
            case "%":
                return leftVal % rightVal;
            case "^":
                return Math.pow(leftVal, rightVal);
        }

        return -0.00000000000000000001;
    }
}
