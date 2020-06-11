package com.liuyihui.client.myexample.example4_use_camera;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.liuyihui.client.myexample.R;

/**
 * 相机调用拍照实例
 */
public class Example4Activity extends AppCompatActivity {
    private final String TAG = "Example4Activity";
    FrameLayout cameraPreviewContainer;
    CameraPreviewView cameraPreviewView;

    private Button recordButton;
    private Button stopRecordButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example4);
        cameraPreviewContainer = findViewById(R.id.camerapreview_container);
        recordButton = findViewById(R.id.record);
        stopRecordButton = findViewById(R.id.stopRecord);

        cameraPreviewView = new CameraPreviewView(this);
        cameraPreviewContainer.addView(cameraPreviewView);


        recordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("录制".equals(recordButton.getText().toString())) {
                    cameraPreviewView.record();
                    recordButton.setText("停止");
                } else {
                    cameraPreviewView.stopRecord();
                    recordButton.setText("录制");
                }
            }
        });
        stopRecordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cameraPreviewView.stopRecord();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        recordButton.setText("录制");
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
