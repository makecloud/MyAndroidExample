package com.liuyihui.client.myexample.example2_system_action;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.liuyihui.client.myexample.R;


/**
 * Activity 例子
 * <p>
 * Created by liuyh on 2016/11/1.
 */
public class Example2Activity extends Activity {

    private final String TAG = "Example2Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);
    }

    public void startSystemActionActivity(View view) {
        String systemAction = Intent.ACTION_SENDTO;
        String data="";
        Intent intent = new Intent();
        intent.setAction(systemAction);
        startActivity(intent);
    }


}
