package toad.commands.math;


import java.util.ArrayList;

public abstract class MathProcessor {
    protected final ArrayList<MathToken> MathTokens;
    ExpressionTokenizer tokenizer;
    public MathProcessor(String expression) throws MathSyntaxException {
        if (expression == null || expression.isBlank()) {
            throw new MathSyntaxException("I can't evaluate a blank expression (I guess you could");
        }
        this.tokenizer = new ExpressionTokenizer(expression);
        this.MathTokens =  tokenizer.tokenize();
        ExpressionValidator.validateExpression(MathTokens);
    }
}
