package toad.commands.math;

import java.util.ArrayList;

public class MathPostProcessor {
    ArrayList<MathToken> tokens = new ArrayList<>();
    public MathPostProcessor(ArrayList<MathToken> tokens) {
        this.tokens = tokens;
    }
}
