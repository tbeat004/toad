package toad.commands.math.postprocess;

import org.junit.jupiter.api.Test;
import toad.commands.math.AbstractSyntaxTree.MathNode;
import toad.commands.math.MathSyntaxException;
import toad.commands.math.token.ExpressionTokenizer;
import toad.commands.math.token.MathToken;
import toad.commands.math.util.TreePrinter;
import toad.commands.math.validate.ExpressionValidator;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestExpressionParser {

    @Test
    public void test1() throws MathSyntaxException {
        String expr = "sin(2(x + 1))";

        ExpressionTokenizer tokenizer = new ExpressionTokenizer(expr);
        ArrayList<MathToken> tokens = tokenizer.tokenize();

        ExpressionValidator.validate(tokens);

        ArrayList<MathToken> processed = MathPostProcessor.postProcess(tokens);

        ExpressionParser parser = new ExpressionParser(processed);
        MathNode root = parser.parse();

        assertNotNull(root); // Basic safety
        System.out.println("AST for expression: " + expr);
        TreePrinter.print(root); // Prints the AST to console
    }
    @Test
    public void test2() throws MathSyntaxException {
        String expr = "2x^2 + 3x - 5 = 0";

        ExpressionTokenizer tokenizer = new ExpressionTokenizer(expr);
        ArrayList<MathToken> tokens = tokenizer.tokenize();

        ExpressionValidator.validate(tokens);

        ArrayList<MathToken> processed = MathPostProcessor.postProcess(tokens);
        for (MathToken t : processed) {
            System.out.println(t.type() + ": " + t.lexeme());
        }

        ExpressionParser parser = new ExpressionParser(processed);
        MathNode root = parser.parse();

        assertNotNull(root); // Basic safety
        System.out.println("AST for expression: " + expr);
        TreePrinter.print(root); // Prints the AST to console
    }
    @Test
    public void test3() throws MathSyntaxException {
        String expr = "sin(x)^2 + cos(x)^2";

        ExpressionTokenizer tokenizer = new ExpressionTokenizer(expr);
        ArrayList<MathToken> tokens = tokenizer.tokenize();

        ExpressionValidator.validate(tokens);

        ArrayList<MathToken> processed = MathPostProcessor.postProcess(tokens);

        ExpressionParser parser = new ExpressionParser(processed);
        MathNode root = parser.parse();

        assertNotNull(root); // Basic safety
        System.out.println("AST for expression: " + expr);
        TreePrinter.print(root); // Prints the AST to console
    }
    @Test
    public void test4() throws MathSyntaxException {
        String expr = "-3x^2 + (4x - sqrt(x^2 + 1))(x + 2) = sin(x)^2 + cos(x)^2";

        ExpressionTokenizer tokenizer = new ExpressionTokenizer(expr);
        ArrayList<MathToken> tokens = tokenizer.tokenize();

        ExpressionValidator.validate(tokens);

        ArrayList<MathToken> processed = MathPostProcessor.postProcess(tokens);

        ExpressionParser parser = new ExpressionParser(processed);
        MathNode root = parser.parse();

        assertNotNull(root); // Basic safety
        System.out.println("AST for expression: " + expr);
        TreePrinter.print(root); // Prints the AST to console
    }

    @Test
    public void test5() throws MathSyntaxException {
        String expr = "2^3^4";

        ExpressionTokenizer tokenizer = new ExpressionTokenizer(expr);
        ArrayList<MathToken> tokens = tokenizer.tokenize();
        ExpressionValidator.validate(tokens);
        ArrayList<MathToken> processed = MathPostProcessor.postProcess(tokens);

        ExpressionParser parser = new ExpressionParser(processed);
        MathNode root = parser.parse();

        assertNotNull(root);
        System.out.println("AST for expression: " + expr);
        TreePrinter.print(root);
    }

    @Test
    public void test6() throws MathSyntaxException {
        String expr = "(3+4)*2";

        ExpressionTokenizer tokenizer = new ExpressionTokenizer(expr);
        ArrayList<MathToken> tokens = tokenizer.tokenize();
        ExpressionValidator.validate(tokens);
        ArrayList<MathToken> processed = MathPostProcessor.postProcess(tokens);

        ExpressionParser parser = new ExpressionParser(processed);
        MathNode root = parser.parse();

        assertNotNull(root);
        System.out.println("AST for expression: " + expr);
        TreePrinter.print(root);
    }
}
