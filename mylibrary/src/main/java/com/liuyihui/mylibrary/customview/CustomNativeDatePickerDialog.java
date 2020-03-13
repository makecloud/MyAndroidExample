package com.liuyihui.mylibrary.customview;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;

import com.liuyihui.client.mylibrary.R;

import java.util.Calendar;

/**
 * 使用原生日期选择控件，自定义对话框。
 * <p>
 * 自定义目的功能1：只能选择今日之后的日期。如果不需此功能直接用原生即可。
 */
public class CustomNativeDatePickerDialog extends DialogFragment {

    private final String TAG = getClass().getSimpleName();

    private DatePicker datePicker;
    private Button cancelButton;
    private Button confirmButton;

    private long minDate = 0;
    private long maxDate = 0;
    private DatePickerDialog.OnDateSetListener onDateSetListener;
    private DatePicker.OnDateChangedListener onDateChangedListener;

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
        final View v = inflater.inflate(R.layout.fragment_native_date_picker_dialog,
                                        container,
                                        false);
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
                if (onDateChangedListener != null) {
                    onDateChangedListener.onDateChanged(view, year, monthOfYear, dayOfMonth);
                }
            }
        });

        datePicker.setMinDate(minDate == 0 ? calendar.getTimeInMillis() : minDate);
        return v;
    }

    public void setOnDateSetListener(DatePickerDialog.OnDateSetListener onDateSetListener) {
        this.onDateSetListener = onDateSetListener;
    }

    public void setOnDateChangedListener(DatePicker.OnDateChangedListener onDateChangedListener) {
        this.onDateChangedListener = onDateChangedListener;
    }

    public void setMinDate(long minDate) {
        this.minDate = minDate;
    }

    public void setMaxDate(long maxDate) {
        this.maxDate = maxDate;
    }

}
