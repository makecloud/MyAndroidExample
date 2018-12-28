package com.liuyihui.dialog_example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //启动标题栏进度条示例
    public void startProgressBarActivity(View view) {
        startActivity(new Intent(this, ProgressBarActivity.class));
    }

}
