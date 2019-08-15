package com.liuyihui.client.myexample.example4_use_camera;

import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.liuyihui.client.myexample.R;

import java.io.IOException;

/**
 * 相机调用拍照实例
 */
public class Example4Activity extends AppCompatActivity {
    private final String TAG = "Example4Activity";
    private Camera camera;
    private SurfaceView surfaceView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example4);
        surfaceView = (SurfaceView) findViewById(R.id.sv_sv);

        //surfaceHolder是surface联系到surfaceView对象的纽带
        SurfaceHolder surfaceHolder = surfaceView.getHolder();


        //surfaceHolder设置回调对象。activity加载完后，会自动回调？
        surfaceHolder.addCallback(new SurfaceHolder.Callback() {

            //surface创建回调
            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                try {
                    //设置相机对象往哪里绘制，即surfaceHolder，而surfaceHolder又作为联系到surface的纽带。说往surface绘制也无问题。
                    camera.setPreviewDisplay(surfaceHolder);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            //surface改变回调
            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
                //camera开始在surface对象上绘制帧
                camera.startPreview();
            }

            //surface销毁回调
            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                //camera停止在surfa上绘制帧
                //                activity_capture.stopPreview();
                if (camera != null) {
                    camera.stopPreview();
                }
                camera.release();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        camera = Camera.open(0);

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (camera != null) {
            camera.release();
        }

    }
}
