package tech.ericntd.calculator;

import android.os.Bundle;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class CalculatorActivityTest {
    private CalculatorActivity activity;

    @Before
    public void setUp() {
        activity = Mockito.spy(new CalculatorActivity());
        activity.calculator = Mockito.mock(Calculator.class);// can't do new Calculator() due to
        // dependency on stuffs like SharedPrefs for example
    }

    @Test
    public void doOnCreate() {
        activity.onCreate(Mockito.mock(Bundle.class));

        Mockito.verify(activity).doSomeOtherSetup();
    }

    @Test
    public void updateNumber() {
        Mockito.doReturn(1).when(activity).extractNumberFromTextView();
        Mockito.doReturn(3).when(activity.calculator).timesTwo(1);
        Mockito.doNothing().when(activity).updateTextView(3);

        // Trigger
        activity.updateNumber();

        // Validation
        Mockito.verify(activity).updateTextView(3);
    }
}
