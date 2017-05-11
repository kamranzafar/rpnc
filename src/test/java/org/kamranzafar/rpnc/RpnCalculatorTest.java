package org.kamranzafar.rpnc;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Created by kamran on 11/05/17.
 */
@RunWith(JUnit4.class)
public class RpnCalculatorTest {
    @Test(expected = InvalidTokenException.class)
    public void evaluateInvalidInput() {
        RpnCalculator calculator = new RpnCalculator();
        calculator.evaluate("1 2 3 )");
    }

    @Test(expected = InsufficientParametersException.class)
    public void evaluateInsufficientParameters() {
        //Example 8
        RpnCalculator calculator = new RpnCalculator();
        calculator.evaluate("1 2 3 * 5 + * * 6 5");
    }

    @Test
    public void evaluateInsufficientParametersCheckStack() {
        //Example 8
        RpnCalculator calculator = new RpnCalculator();

        try {
            calculator.evaluate("1 2 3 * 5 + * * 6 5");
        } catch (InsufficientParametersException e) {
            Assert.assertEquals(calculator.getValueStack().get(0), new Double(11));
            Assert.assertEquals(calculator.getValueStack().size(), 1);
        }
    }

    @Test
    public void evaluate() {
        RpnCalculator calculator = new RpnCalculator();

        //Example 1
        calculator.evaluate("5 2");
        Assert.assertEquals(calculator.getValueStack().size(), 2);
        calculator.evaluate("clear");

        //Example 2
        calculator.evaluate("2 sqrt");
        Assert.assertEquals(calculator.getValueStack().get(0), new Double("1.414213562373095"));
        calculator.evaluate("clear 9 sqrt");
        Assert.assertEquals(calculator.getValueStack().get(0), new Double(3));
        calculator.evaluate("clear");

        //Example 3
        calculator.evaluate("5 2 -");
        Assert.assertEquals(calculator.getValueStack().get(0), new Double(3));
        calculator.evaluate("3 -");
        Assert.assertEquals(calculator.getValueStack().get(0), new Double(0));
        calculator.evaluate("clear");
        Assert.assertEquals(calculator.getValueStack().size(), 0);

        //Example 4
        calculator.evaluate("5 4 3 2");
        calculator.evaluate("undo undo *");
        Assert.assertEquals(calculator.getValueStack().get(0), new Double(20));
        calculator.evaluate("5 *");
        Assert.assertEquals(calculator.getValueStack().get(0), new Double(100));
        calculator.evaluate("undo");
        Assert.assertEquals(calculator.getValueStack().get(0), new Double(20));
        Assert.assertEquals(calculator.getValueStack().get(1), new Double(5));
        calculator.evaluate("clear");

        //Example 5
        calculator.evaluate("7 12 2 /");
        Assert.assertEquals(calculator.getValueStack().get(0), new Double(7));
        Assert.assertEquals(calculator.getValueStack().get(1), new Double(6));
        calculator.evaluate("*");
        Assert.assertEquals(calculator.getValueStack().get(0), new Double(42));
        calculator.evaluate("4 /");
        Assert.assertEquals(calculator.getValueStack().get(0), new Double(10.5));
        calculator.evaluate("clear");

        //Example 6
        calculator.evaluate("1 2 3 4 5");
        calculator.evaluate("*");
        calculator.evaluate("clear 3 4 -");
        Assert.assertEquals(calculator.getValueStack().get(0), new Double(-1));
        calculator.evaluate("clear");

        //Example 7
        calculator.evaluate("1 2 3 4 5");
        calculator.evaluate("* * * *");
        Assert.assertEquals(calculator.getValueStack().get(0), new Double(120));
    }
}
