package toad.commands.math.core;


import toad.commands.math.AbstractSyntaxTree.MathNode;
import toad.commands.math.MathSyntaxException;
import toad.commands.math.postprocess.ExpressionParser;
import toad.commands.math.postprocess.MathPostProcessor;
import toad.commands.math.token.ExpressionTokenizer;
import toad.commands.math.token.MathToken;
import toad.commands.math.validate.ExpressionValidator;

import java.util.ArrayList;

public class MathProcessor {
    protected final ArrayList<MathToken> MathTokens;
    protected final MathNode root;
    ExpressionTokenizer tokenizer;
    public MathProcessor(String expression) throws MathSyntaxException {
        if (expression == null || expression.isBlank()) {
            throw new MathSyntaxException("I can't evaluate a blank expression (I guess you could");
        }
        this.tokenizer = new ExpressionTokenizer(expression);
        this.MathTokens =  tokenizer.tokenize();
        ExpressionValidator.validate(MathTokens);
        ArrayList<MathToken> processed = MathPostProcessor.postProcess(MathTokens);
        ExpressionParser parser = new ExpressionParser(processed);
        this.root = parser.parse();
    }

    public double evaluate() {
        return root.evaluate();
    }
}
