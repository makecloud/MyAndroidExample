package com.liuyihui.client.myexample.example20_activityGroup;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.liuyihui.client.myexample.R;
import com.liuyihui.client.myexample.example3.DayConsome;

public class TheSecondActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("the second activity");
        setContentView(R.layout.activity_the_second);
        
        
    }
    
    public void secondActivityButtonFunc(View view) {
        new DayConsome();
        Toast.makeText(this, "ThesecondeActivity make a toast .", Toast.LENGTH_SHORT).show();
    }
}
