package com.guliash.datefinder;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.widget.DatePicker;

import org.joda.time.LocalDate;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    private static final String DAY = "day";
    private static final String MONTH = "month";
    private static final String YEAR = "year";

    private int mDay, mMonth, mYear;

    private Callbacks mCallbacks;

    public interface Callbacks {
        void onDateChosen(int year, int month, int day);
    }

    public static DatePickerFragment newInstance() {
        LocalDate localDate = new LocalDate();
        return newInstance(localDate.getDayOfMonth(), localDate.getMonthOfYear(), localDate.getYear());
    }

    public static DatePickerFragment newInstance(int day, int month, int year) {
        Bundle args = new Bundle();

        args.putInt(DAY, day);
        args.putInt(MONTH, month);
        args.putInt(YEAR, year);

        DatePickerFragment fragment = new DatePickerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Callbacks) {
            mCallbacks = (Callbacks) context;
        } else {
            throw new RuntimeException("Must implement " + DatePickerFragment.class.getName());
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mYear = bundle.getInt(YEAR);
        mMonth = bundle.getInt(MONTH);
        mDay = bundle.getInt(DAY);

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new DatePickerDialog(getActivity(), this, mYear, mMonth, mDay);
    }

    @Override
    public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
        mCallbacks.onDateChosen(year, month, dayOfMonth);
    }
}
