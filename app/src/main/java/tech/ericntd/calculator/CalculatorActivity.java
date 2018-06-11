package tech.ericntd.calculator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import tech.ericntd.githubsearch.R;

public class CalculatorActivity extends AppCompatActivity {
    Calculator calculator;
    @BindView(R.id.tv_number)
    TextView tvNumber;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);
        calculator = new Calculator();

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNumber();
            }
        };
        // Set the OnClickListener for a button

        doSomeOtherSetup();
    }

    void doSomeOtherSetup() {
    }

    @OnClick(R.id.cta)
    void updateNumber() {
        int newNumber = calculator.timesTwo(extractNumberFromTextView());
        updateTextView(newNumber);
    }

    void updateTextView(int newNumber) {
        tvNumber.setText(newNumber);
    }

    int extractNumberFromTextView() {
        return Integer.valueOf(tvNumber.toString());
    }
}
