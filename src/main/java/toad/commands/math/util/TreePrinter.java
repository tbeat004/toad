package toad.commands.math.util;

import toad.commands.math.AbstractSyntaxTree.*;

public class TreePrinter {

    public static void print(MathNode root) {
        print(root, "", true);
    }

    private static void print(MathNode node, String prefix, boolean isTail) {
        if (node == null) {
            System.out.println(prefix + (isTail ? "└── " : "├── ") + "null");
            return;
        }

        String connector = isTail ? "└── " : "├── ";

        if (node instanceof NumberNode number) {
            System.out.println(prefix + connector + "Number: " + number.getValue());
        } else if (node instanceof VariableNode variable) {
            System.out.println(prefix + connector + "Variable: " + variable.getName());
        } else if (node instanceof UnaryOpNode unary) {
            System.out.println(prefix + connector + "UnaryOp: " + unary.op());
            print(unary.child(), prefix + (isTail ? "    " : "│   "), true);
        } else if (node instanceof BinaryOpNode binary) {
            System.out.println(prefix + connector + "BinaryOp: " + binary.op());
            print(binary.left(), prefix + (isTail ? "    " : "│   "), false);
            print(binary.right(), prefix + (isTail ? "    " : "│   "), true);
        } else if (node instanceof FunctionNode func) {
            System.out.println(prefix + connector + "Function: " + func.getName());
            print(func.argument(), prefix + (isTail ? "    " : "│   "), true);
        } else if (node instanceof EqualityNode eq) {
            System.out.println(prefix + connector + "Equality (=)");
            print(eq.left(), prefix + (isTail ? "    " : "│   "), false);
            print(eq.right(), prefix + (isTail ? "    " : "│   "), true);
        } else {
            System.out.println(prefix + connector + "Unknown node: " + node.getClass().getSimpleName());
        }
    }
}
