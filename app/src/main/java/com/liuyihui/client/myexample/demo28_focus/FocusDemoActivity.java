package com.liuyihui.client.myexample.demo28_focus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.liuyihui.client.myexample.R;

public class FocusDemoActivity extends AppCompatActivity {
    private final String TAG = "FocusDemoActivity";
    LinearLayout containerFramelayout;
    Button button1;
    Button button2;
    Button button3;

    private View.OnKeyListener childViewOnKeyListener = new View.OnKeyListener() {
        @Override
        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                //这样一个键，就不会调用多次了
                if (KeyEvent.ACTION_UP == event.getAction()) {
                    containerFramelayout.requestFocus();
                }
                Log.d(TAG, "child View onKey: KEYCODE_BACK");
                return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_focus_demo);

        containerFramelayout = findViewById(R.id.containerFramelayout);
        button1 = findViewById(R.id.btn1);
        button2 = findViewById(R.id.btn2);
        button3 = findViewById(R.id.btn3);

        containerFramelayout.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "containerFramelayout onFocusChange: " + hasFocus);
            }
        });
        containerFramelayout.setNextFocusDownId(R.id.btn1);
        containerFramelayout.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    Log.d(TAG, "containerFramelayout onKey: KEYCODE_BACK");
                    return true;
                }
                return false;
            }
        });

        button1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "button1 onFocusChange: " + hasFocus);
            }
        });
        button1.setOnKeyListener(childViewOnKeyListener);

        button2.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "button2 onFocusChange: " + hasFocus);
            }
        });
        button2.setOnKeyListener(childViewOnKeyListener);

        button3.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "button3 onFocusChange: " + hasFocus);
            }
        });
        button3.setOnKeyListener(childViewOnKeyListener);
    }


    /**
     * activity处理按键事件
     *
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.d(TAG, "activity onKeyDown: " + keyCode);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


}
