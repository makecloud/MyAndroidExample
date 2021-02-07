package com.oohlink.fragmentdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

/**
 * 从fragmentA 到 fragmentB的共享元素变换动画
 */
public class MainActivity extends AppCompatActivity {
    private final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //创建A
        final FragmentA fragmentA = FragmentA.newInstance(null, null);
        //初始时，将fragmentA 显示到 activity
        getSupportFragmentManager().beginTransaction().add(R.id.fragContainer, fragmentA).commit();


    }


}
