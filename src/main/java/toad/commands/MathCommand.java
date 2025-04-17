package toad.commands;

import picocli.CommandLine.*;
import toad.commands.math.MathSyntaxException;
import toad.commands.math.core.MathProcessor;

import java.util.List;
import java.util.concurrent.Callable;


@Command(name = "math", description = "A collection of tools Usage: tool tool <command>",
        subcommands = {
                MathCommand.Simplify.class,
                MathCommand.Evaluate.class


        })
public class MathCommand implements Callable<Integer> {

    @Override
    public Integer call() {
        System.out.println("Use a subcommand like 'simplify' or 'evaluate'");
        return 0;
    }

    @Command(name = "simplify", description = "Simplify a mathematical expression.")
    static class Simplify implements Runnable {

        @Parameters(index="0..*",description = "The expression to simplify.")
        String expression;

        @Override
        public void run() {

            System.out.println("I dont do anything yet!!!");
        }
    }

    @Command(name = "evaluate", description = "Evaluate a mathematical expression.")
    static class Evaluate implements Runnable {

        @Parameters(index="0..*",description = "The expression to evaluate.")
        List<String> input;



        @Override
        public void run() {
            try {
                String expression = String.join(" ", input);
                MathProcessor proc = new MathProcessor(expression);
                System.out.println("Expression: " + proc.getOriginalFormatted() + " = " + proc.evaluate());
            } catch (MathSyntaxException e) {
                System.err.println(e.getMessage());
            }
            // Add your expression parsing + simplify logic here
        }
    }
}


