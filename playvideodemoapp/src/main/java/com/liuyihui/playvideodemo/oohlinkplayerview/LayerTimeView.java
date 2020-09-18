package com.liuyihui.playvideodemo.oohlinkplayerview;

import android.content.Context;
import android.util.Log;
import android.util.TypedValue;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 时间显示层view
 *
 * @author makecloudl
 */
public class LayerTimeView extends TextView  {
    private final String TAG = "LayerTimeView";
    private Context mContext;
    private SimpleDateFormat mSimpleDateFormat;
    private Thread timeThread;

    public LayerTimeView(Context context) {
        super(context);

        mSimpleDateFormat = new SimpleDateFormat("yyyy年MM月dd日 EEEE HH:mm", Locale.CHINA);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, 28F);

        timeThread = new Thread(new Runnable() {
            @Override
            public void run() {
                setText(mSimpleDateFormat.format(System.currentTimeMillis()));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        timeThread.start();
    }


    public void release() {
        if (timeThread != null) {
            timeThread.interrupt();
        }
        timeThread = null;
        mSimpleDateFormat = null;
        Log.d(TAG, "release: ");
    }

}
