package tech.ericntd.githubsearch;

import android.widget.TextView;

import org.junit.Test;
import org.mockito.Mockito;

import tech.ericntd.githubsearch.models.CalculatorActivity;

public class CalculatorActivityTest {
    private CalculatorActivity activity = new CalculatorActivity();

    @Test
    public void updateNumber() {
        TextView tvNumber = Mockito.mock(TextView.class);
        Mockito.doReturn("1").when(tvNumber).getText();

        activity.updateNumber(tvNumber);

        Mockito.verify(tvNumber).setText(2);
    }
}
