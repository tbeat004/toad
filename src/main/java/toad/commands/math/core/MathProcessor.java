package toad.commands.math.core;


import toad.commands.math.MathSyntaxException;
import toad.commands.math.token.ExpressionTokenizer;
import toad.commands.math.token.MathToken;
import toad.commands.math.validate.ExpressionValidator;

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
        ExpressionValidator.validate(MathTokens);
    }
}
