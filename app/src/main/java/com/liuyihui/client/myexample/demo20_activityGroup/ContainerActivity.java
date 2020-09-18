package com.liuyihui.client.myexample.demo20_activityGroup;

import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.liuyihui.client.myexample.R;

/**
 * 从这个activity中启动另一个apk的activity（未成功demo，在first_app中成功了）
 */
public class ContainerActivity extends AppCompatActivity {
    private static final String TAG = "ContainerActivity";
    private LocalActivityManager am;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);
        
        am = new LocalActivityManager(this, true);
        am.dispatchCreate(savedInstanceState);
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.parent_linearlayout);
       
        //start user activity
        Intent intent2 = new Intent();
        intent2.setClassName("com.liuyihui.first_app.activity", "com.liuyihui.first_app.activity.EmptyActivity");//第一个app那种
        Window window2 = am.startActivity("id2", intent2);
        View view2 = window2.getDecorView();
        linearLayout.addView(view2);
        
        //start The first activity
        Intent intent1 = new Intent(this, TheFirstActivity.class);
        Window window1 = am.startActivity("id1", intent1);
        View view1 = window1.getDecorView();
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(200,400);
        linearLayout.addView(view1, layoutParams);
        
        //start The first activity
        Intent intent1_2 = new Intent(this, TheSecondActivity.class);
        intent1_2.setClassName("com.liuyihui.client.myexample","com.liuyihui.client.myexample.example20_activityGroup.TheSecondActivity");
        Window window1_2 = am.startActivity("id1_2", intent1_2);
        View view1_2 = window1_2.getDecorView();
        linearLayout.addView(view1_2, new FrameLayout.LayoutParams(400,800));
        
        
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        am.dispatchResume();
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        am.dispatchPause(isFinishing());
    }
}
