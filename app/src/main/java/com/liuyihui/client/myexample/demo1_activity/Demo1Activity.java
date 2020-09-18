package com.liuyihui.client.myexample.demo1_activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.TextView;

import com.liuyihui.client.myexample.R;

/**
 * 研究finish关闭activity，返回按钮 返回上一层activity
 */
public class Demo1Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_demo1);
        getSupportActionBar().setTitle(getClass().getSimpleName());
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        getSupportParentActivityIntent();

        TextView tv=findViewById(R.id.log);
        tv.append("fjkdsjaf\n");
        tv.append("fd\n");
        tv.append("sdfv\n");
        tv.append("fz\n");
        tv.append("hfhs\n");
        tv.append("vccc\n");
        tv.append("hhwwq\n");

        tv.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    public void startNextActivity(View view) {
        Intent intent = new Intent(this, Demo2Activity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);
        //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
    }
    public void close(View view) {
        finish();
    }
}
