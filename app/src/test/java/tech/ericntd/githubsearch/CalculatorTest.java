package tech.ericntd.githubsearch;

import org.junit.Assert;
import org.junit.Test;

public class CalculatorTest {
    private Calculator calculator = new Calculator();

    @Test
    public void plusTwo() {
        Assert.assertEquals(0, calculator.timesTwo(0));
        Assert.assertEquals(2, calculator.timesTwo(1));
        Assert.assertEquals(-2, calculator.timesTwo(-1));
        Assert.assertEquals(-1, calculator.timesTwo(Integer.MAX_VALUE));
        Assert.assertEquals(-1, calculator.timesTwo(Integer.MIN_VALUE));
        Assert.assertEquals(2147483646, calculator.timesTwo(Integer.MAX_VALUE / 2));
        Assert.assertEquals(-1, calculator.timesTwo(Integer.MAX_VALUE / 2 + 1));
        Assert.assertEquals(-2147483648, calculator.timesTwo(Integer.MIN_VALUE / 2));
        Assert.assertEquals(-1, calculator.timesTwo(Integer.MIN_VALUE / 2 - 1));
    }
}
