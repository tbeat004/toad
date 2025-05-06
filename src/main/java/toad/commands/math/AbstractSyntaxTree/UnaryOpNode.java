package toad.commands.math.AbstractSyntaxTree;

public class UnaryOpNode extends MathNode{
    private String op;
    private MathNode operand;

    public UnaryOpNode(String op, MathNode operand) {
        this.op = op;       // Capture the operator symbol like "-"
        this.operand = operand;           // The operand (right side of unary expression)
    }

    @Override
    public String toString() {
        return op + " " + operand;
    }

    @Override
    public double evaluate() {
        return -operand.evaluate();
    }

    public String op() {
        return op;
    }
    public MathNode child() {
        return operand;
    }

    @Override
    public MathNode simplify() {
        MathNode simpOperand = operand.simplify();

        if (simpOperand instanceof NumberNode number) return new NumberNode(-number.getValue());
        return new UnaryOpNode("-", simpOperand);
    }
}
