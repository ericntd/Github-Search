package tech.ericntd.githubsearch.models;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import tech.ericntd.githubsearch.Calculator;
import tech.ericntd.githubsearch.R;

public class CalculatorActivity extends AppCompatActivity {

    public Calculator calculator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        calculator = new Calculator();
        final TextView tvNumber = findViewById(R.id.tv_number);
        Button ctaTimesTwo = findViewById(R.id.cta_times_two);
        ctaTimesTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNumber(tvNumber);
            }
        });
    }

    public void updateNumber(TextView tvNumber) {
        tvNumber.setText(calculator.timesTwo(Integer.valueOf(tvNumber.getText().toString())));
    }
}
