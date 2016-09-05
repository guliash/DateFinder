package com.guliash.datefinder;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements DatePickerFragment.Callbacks {

    private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat.forPattern("dd.MM.yy");
    private static final String CHOSEN_DATE = "chosen_date";

    @BindView(R.id.choose_date)
    Button mChooseDateBtn;

    @BindView(R.id.result)
    TextView mResultField;

    @BindView(R.id.day_count)
    EditText mDayCountField;

    private LocalDate mChosenDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState != null) {
            mChosenDate = (LocalDate) savedInstanceState.getSerializable(CHOSEN_DATE);
        }

        ButterKnife.bind(this);

        mResultField.setInputType(InputType.TYPE_NULL);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(CHOSEN_DATE, mChosenDate);
    }

    @Override
    protected void onResume() {
        super.onResume();
        displayChosenDate();
    }

    private void displayChosenDate() {
        if (mChosenDate == null) {
            mChooseDateBtn.setText("");
        } else {
            mChooseDateBtn.setText(mChosenDate.toString(DATE_TIME_FORMAT));
        }
    }

    @OnClick(R.id.choose_date)
    void onChooseDateClick() {
        DatePickerFragment.newInstance().show(getSupportFragmentManager(), null);
    }

    @Override
    public void onDateChosen(int year, int month, int day) {
        mChosenDate = new LocalDate(year, month, day);
        displayChosenDate();
        tryToCalculateEndDate(false);
    }

    @OnClick(R.id.calc)
    public void onCalculateClick() {
        tryToCalculateEndDate(true);
    }

    private void tryToCalculateEndDate(boolean showWarnings) {
        if (mChosenDate == null) {
            return;
        }
        int toAdd;
        try {
            toAdd = Integer.parseInt(mDayCountField.getText().toString());
        } catch (NumberFormatException e) {
            if (showWarnings) {
                Toast.makeText(this, getString(R.string.bad_number_warning), Toast.LENGTH_LONG).show();
            }
            return;
        }
        LocalDate resultDate = mChosenDate.plusDays(toAdd);
        mResultField.setText(resultDate.toString(DATE_TIME_FORMAT));
    }
}
