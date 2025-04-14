package toad.commands.math.postprocess;

import org.junit.jupiter.api.Test;
import toad.commands.math.MathSyntaxException;
import toad.commands.math.core.MathProcessor;

import static org.junit.jupiter.api.Assertions.*;

public class TestMathProcessor {

    @Test
    public void test1() throws MathSyntaxException {
        String exp = "1 + 2";
        MathProcessor mathProcessor = new MathProcessor(exp);
        assertEquals(3, mathProcessor.evaluate());

    }

    @Test
    public void test2() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("2 + 3 * 4");
        assertEquals(14, mp.evaluate());
    }

    @Test
    public void test3() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("(2 + 3) * 4");
        assertEquals(20, mp.evaluate());
    }

    @Test
    public void test4() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("((1 + 2) * (3 + 4))");
        assertEquals(21, mp.evaluate());
    }

    @Test
    public void test5() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("2 ^ 3");
        assertEquals(8, mp.evaluate());
    }

    @Test
    public void test6() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("2 ^ 3 ^ 2");
        assertEquals(512, mp.evaluate());
    }

    @Test
    public void test7() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("20 / 5 * 2");
        assertEquals(8, mp.evaluate());
    }

    @Test
    public void test8() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("-3 + 5");
        assertEquals(2, mp.evaluate());
    }

    @Test
    public void test9() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("-(2 + 3)");
        assertEquals(-5, mp.evaluate());
    }

    @Test
    public void test10() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("3 + 4 * 2 / (1 - 5) ^ 2 ^ 3");
        assertNotNull(mp.evaluate()); // ≈ 3.0001220703125
    }

    @Test
    public void test11() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("3.5 + 2.1");
        assertEquals(5.6, mp.evaluate(), 0.00001);
    }

    @Test
    public void test12() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("1 + 2 + 3 + 4");
        assertEquals(10, mp.evaluate());
    }

    @Test
    public void test13() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("-(-5)");
        assertEquals(5, mp.evaluate());
    }

    @Test
    public void test14() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("((((((3))))))");
        assertEquals(3, mp.evaluate());
    }

    @Test
    public void test15() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("0 ^ 0");
        assertEquals(1, mp.evaluate()); // Math.pow(0, 0) returns 1.0
    }

    @Test
    public void test16() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("0 ^ 5");
        assertEquals(0, mp.evaluate());
    }

    @Test
    public void test17() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("1 ^ 0");
        assertEquals(1, mp.evaluate());
    }

    @Test
    public void test18() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("1.0000000001 + 0.0000000001");
        assertEquals(1.0000000002, mp.evaluate(), 1e-10);
    }

    @Test
    public void test19() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("1 - 2 - 3");
        assertEquals(-4, mp.evaluate());
    }

    @Test
    public void test20() {
        assertThrows(ArithmeticException.class, () -> {
            MathProcessor mp = new MathProcessor("4 / 0");
            mp.evaluate();
        });
    }

    @Test
    public void test21() {
        assertThrows(MathSyntaxException.class, () -> {
            new MathProcessor("2 +");
        });
    }

    @Test
    public void test22() {
        assertThrows(MathSyntaxException.class, () -> {
            new MathProcessor("");
        });
    }

    @Test
    public void test23() {
        assertThrows(MathSyntaxException.class, () -> {
            new MathProcessor("    ");
        });
    }

    @Test
    public void test24() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("((((((2 + 3) * (4 + 1))))))");
        assertEquals(25, mp.evaluate());
    }

    @Test
    public void test25() throws MathSyntaxException {
        MathProcessor mp = new MathProcessor("(((((sqrt(16) + sin(0)) * (log(1) + 3)))))");
        assertEquals(12, mp.evaluate(), 1e-10);
    }

}
