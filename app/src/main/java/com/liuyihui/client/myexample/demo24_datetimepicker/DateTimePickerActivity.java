package com.liuyihui.client.myexample.demo24_datetimepicker;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.liuyihui.client.myexample.R;

/**
 * 日期选择
 */
public class DateTimePickerActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private DatePickerDialog datePickerDialog;
    private CustomNativeDatePickerDialog customDatePickerDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_date_time_picker);

        DatePickerDialog.OnDateSetListener onDateSetListener =
                new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Toast.makeText(DateTimePickerActivity.this,
                               String.format("%d-%d-%d", year, month, dayOfMonth),
                               Toast.LENGTH_SHORT).show();
            }
        };

        datePickerDialog = new DatePickerDialog(this, onDateSetListener, 2020, 11, 10);

        customDatePickerDialog = CustomNativeDatePickerDialog.newInstance();


    }

    public void dateTimePick(View view) {
        datePickerDialog.show();
    }

    public void nativeDatePicker(View view) {
        customDatePickerDialog.setOnDateSetListener(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Toast.makeText(DateTimePickerActivity.this,
                               String.format("%d-%d-%d", year, month, dayOfMonth),
                               Toast.LENGTH_SHORT).show();
            }
        });
        customDatePickerDialog.show(getSupportFragmentManager(), TAG);
    }
}
