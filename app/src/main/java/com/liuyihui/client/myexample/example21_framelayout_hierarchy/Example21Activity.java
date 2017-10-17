package com.liuyihui.client.myexample.example21_framelayout_hierarchy;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.liuyihui.client.myexample.R;

/**
 * 在一个frameLayout中，将被遮挡的控件放到上层显示
 */
public class Example21Activity extends AppCompatActivity {
    
    Button button1;
    Button button2;
    FrameLayout f1;
    FrameLayout f2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example21);
        
        button1 = (Button) findViewById(R.id.button_1);
        button2 = (Button) findViewById(R.id.button_2);
        f1 = (FrameLayout) findViewById(R.id.f1);
        f2 = (FrameLayout) findViewById(R.id.f2);
    }
    
    /**
     * 上移一层按钮事件
     * @param view
     */
    public void move1Up(View view) {
        f1.bringToFront();
        
    }
    
    public void move2Up(View view) {
        
        f2.bringToFront();
        
    }
    
}
