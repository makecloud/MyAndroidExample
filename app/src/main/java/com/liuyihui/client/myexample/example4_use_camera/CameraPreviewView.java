package com.liuyihui.client.myexample.example4_use_camera;

import android.content.Context;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import java.io.IOException;
import java.util.List;

/**
 * 定义相机预览view。封装surfaceView和surfaceHolder
 */
public class CameraPreviewView extends SurfaceView implements SurfaceHolder.Callback {
    private final String TAG = getClass().getSimpleName();
    private SurfaceHolder holder;
    private Camera camera;
    private MediaRecorder recorder;
    private boolean recording;


    public CameraPreviewView(Context context) {
        super(context);

        holder = getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        recorder = new MediaRecorder();
    }

    private Camera.PreviewCallback previewCallback = new Camera.PreviewCallback() {
        @Override
        public void onPreviewFrame(byte[] data, Camera camera) {

        }
    };

    //
    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated: ");
        try {
            camera = Camera.open(0);
            //设置相机对象往哪里绘制，即surfaceHolder，而surfaceHolder又作为联系到surface的纽带。说往surface绘制也无问题。
            camera.setPreviewDisplay(holder);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged: ");

        Camera.Parameters parameters = camera.getParameters();

        //look size
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
        for (Camera.Size size : sizeList) {
            Log.d(TAG, "size:  " + size.width + "," + size.height);
        }
        //pick one size set to
        parameters.setPreviewSize(640, 480);

        //设置预览方向
        camera.setDisplayOrientation(90);

        //设置参数
        camera.setParameters(parameters);

        //cameraInfo
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        Camera.getCameraInfo(0, cameraInfo);

        //让surface开始更新预览
        camera.startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed: ");
        stopPreviewAndFreeCamera(camera);
        if (recording) {
            stopRecord();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    //camera停止在surfa上绘制帧
    //释放camera
    private void stopPreviewAndFreeCamera(Camera mCamera) {

        if (mCamera != null) {
            // Call stopPreview() to stop updating the preview surface.
            mCamera.stopPreview();

            // Important: Call release() to release the camera for use by other
            // applications. Applications should release the camera immediately
            // during onPause() and re-open() it during onResume()).
            mCamera.release();
        }
    }

    public void record() {
        camera.unlock();

        recorder = new MediaRecorder();
        recorder.setCamera(camera);
        recorder.setAudioSource(MediaRecorder.AudioSource.CAMCORDER);//没有recordAudio权限报native错
        recorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        CamcorderProfile cp = CamcorderProfile.get(0, CamcorderProfile.QUALITY_HIGH_SPEED_720P);
        recorder.setProfile(cp);

        recorder.setOutputFile(Environment.getExternalStorageDirectory() + "/liuyihui.mp4");
        recorder.setPreviewDisplay(holder.getSurface());

        try {
            recorder.prepare();
        } catch (IOException e) {
            Log.e(TAG, "record: ", e);
        }

        recorder.start();
        recording = true;
    }

    public void stopRecord() {
        if (recording) {
            recording = false;
            recorder.stop();
            recorder.reset();
            recorder.release();
            //camera.lock();
        }

    }
}
