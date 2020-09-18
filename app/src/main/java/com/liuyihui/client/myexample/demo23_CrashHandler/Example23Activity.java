package com.liuyihui.client.myexample.demo23_CrashHandler;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.liuyihui.client.myexample.R;

public class Example23Activity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example23);

        CrashHandler crashHandler=CrashHandler.getInstance();

        crashHandler.init(this);



        tv.setText("空指针");
    }
}
