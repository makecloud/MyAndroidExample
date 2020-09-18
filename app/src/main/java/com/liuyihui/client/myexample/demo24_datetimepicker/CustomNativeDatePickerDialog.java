package com.liuyihui.client.myexample.demo24_datetimepicker;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.liuyihui.client.myexample.R;

import java.util.Calendar;

/**
 * 原声日期选择控件，做成对话框
 */
public class CustomNativeDatePickerDialog extends DialogFragment {

    private final String TAG = getClass().getSimpleName();

    private DatePicker datePicker;
    private Button cancelButton;
    private Button confirmButton;

    public CustomNativeDatePickerDialog() {
        // Required empty public constructor
    }

    public static CustomNativeDatePickerDialog newInstance() {
        return new CustomNativeDatePickerDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_native_date_picker_dialog, container, false);
        datePicker = v.findViewById(R.id.datePicker);
        cancelButton = v.findViewById(R.id.cancel);
        confirmButton = v.findViewById(R.id.confirm);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onDateSetListener != null) {
                    onDateSetListener.onDateSet(datePicker,
                                                datePicker.getYear(),
                                                datePicker.getMonth(),
                                                datePicker.getDayOfMonth());
                }
                dismiss();
            }
        });
        Calendar calendar;
        calendar = Calendar.getInstance();
        int nowYear = calendar.get(Calendar.YEAR);
        int nowMonth = calendar.get(Calendar.MONTH);
        int nowDay = calendar.get(Calendar.DAY_OF_MONTH);
        datePicker.init(nowYear, nowMonth, nowDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

            }
        });
        datePicker.setMinDate(calendar.getTimeInMillis());


        return v;
    }

    private DatePickerDialog.OnDateSetListener onDateSetListener;

    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener) {
        this.onDateSetListener = onDateSetListener;
    }

    public void setMinDate(long minDate) {
        datePicker.setMinDate(minDate);
    }

    public void setMaxDate(long maxDate) {
        datePicker.setMaxDate(maxDate);
    }
}
