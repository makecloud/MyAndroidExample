package com.liuyihui.client.myexample.demo20_activityGroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.liuyihui.client.myexample.R;

public class TheFirstActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("the first activity");
        setContentView(R.layout.activity_the_first);
    }
}
