package com.liuyihui.facedetect;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    static final String TAG = "MainActivity";
    SurfaceView surfaceView;
    SurfaceView behindSurfaceView;
    SurfaceHolder mSurfaceHolder;
    Button startButton;
    Paint paint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        behindSurfaceView = findViewById(R.id.behind_surfaceView);
        surfaceView = findViewById(R.id.use_surfaceView);
        startButton = findViewById(R.id.start_button);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(2);
        paint.setColor(Color.RED);

        mSurfaceHolder = surfaceView.getHolder();
        mSurfaceHolder.addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                Log.i(TAG, "surfaceCreated--");
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                Log.i(TAG, "surfaceChanged--");
            }

            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                Log.i(TAG, "surfaceDestroyed--");
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //画
                Canvas canvas = mSurfaceHolder.lockCanvas();
                canvas.drawRect(20, 20, 100, 100, paint);
                mSurfaceHolder.unlockCanvasAndPost(canvas);
            }
        });
    }
}
