package com.liuyihui.mylibrary.customview;


import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TimePicker;

import com.liuyihui.client.mylibrary.R;

/**
 * 原生时间选择控件，做成对话框
 *
 * @deprecated 目前没必要自定义
 */
public class CustomNativeTimePickerDialog extends DialogFragment {

    private final String TAG = getClass().getSimpleName();

    private TimePicker timePicker;
    private Button cancelButton;
    private Button confirmButton;

    private long minTime = 0;
    private long maxTime = 0;
    private TimePickerDialog.OnTimeSetListener onTimeSetListener;
    private TimePicker.OnTimeChangedListener onTimeChangedListener;

    public CustomNativeTimePickerDialog() {
        // Required empty public constructor
    }

    public static CustomNativeTimePickerDialog newInstance() {
        return new CustomNativeTimePickerDialog();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View v = inflater.inflate(R.layout.fragment_native_time_picker_dialog,
                                        container,
                                        false);
        timePicker = v.findViewById(R.id.timePicker);
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
                if (onTimeSetListener != null) {
                    onTimeSetListener.onTimeSet(timePicker,
                                                timePicker.getCurrentHour(),
                                                timePicker.getCurrentMinute());
                }
                dismiss();
            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                // TODO: 2020-02-08
            }
        });

        return v;
    }

    public void setOnTimeSetListener(TimePickerDialog.OnTimeSetListener onTimeSetListener) {
        this.onTimeSetListener = onTimeSetListener;
    }

    public void setOnTimeChangedListener(TimePicker.OnTimeChangedListener onTimeChangedListener) {
        this.onTimeChangedListener = onTimeChangedListener;
    }

    public void setMinTime(long minTime) {
        this.minTime = minTime;
    }

    public void setMaxTime(long maxTime) {
        this.maxTime = maxTime;
    }

}
