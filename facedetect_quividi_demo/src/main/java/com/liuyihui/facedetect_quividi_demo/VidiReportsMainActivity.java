package com.liuyihui.facedetect_quividi_demo;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceView;

import com.quividi.Manager;
import com.quividi.QuividiAPI;
import com.quividi.jni.APIMode;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

public class VidiReportsMainActivity extends Activity {
    private Manager m_Manager;

    private QuividiAPI m_API;
    static SoundPool soundPool;

    private class LocalManager extends Manager {
        public LocalManager(Activity activity, Settings settings) throws Manager.LicenseError, Manager.Error {
            super(activity, settings);
        }

        public LocalManager(Activity activity) throws Manager.LicenseError, Manager.Error {
            super(activity);
        }

        @Override
        public SurfaceView createDisplayView(Context context) {
            return new DrawingListener(context);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        soundPool.load(this, R.raw.qrcode_completed, 1);

            Manager.Settings settings = new Manager.Settings();

        try {
//            InputStream istream = getContentResolver().openInputStream(getIntent().getData());
            File licenseFile = new File(Environment.getExternalStorageDirectory() + "/Download/quividi_vidireports.lic");
            InputStream istream = new FileInputStream(licenseFile);
            try {
                byte[] bf = new byte[128];
                int length = istream.read(bf, 0, 128);
                String lic = new String(bf, 0, length, Charset.forName("UTF-8"));
                settings.setAuthenticationCode(lic);
                settings.setOverwriteAuthenticationCode(true);
            } finally {
                istream.close();
            }
        } catch (FileNotFoundException e) {
            // Yeah right
            Log.i("QuividiTest", "Could not load file: " + e);
        } catch (IOException e) {
            // You don't say
            Log.i("QuividiTest", "Could not load file: " + e);
        }

        // Choose front-facing camera if there is one.
        for (int i = 0; i < Camera.getNumberOfCameras(); ++i) {
            try {
                Camera camera = Camera.open(i);
                try {
                    Camera.CameraInfo info = new Camera.CameraInfo();
                    Camera.getCameraInfo(i, info);

                    if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                        try {
                            settings.setCamera(i, 640, 480, 0);
                        } catch (Manager.Settings.InvalidSettingValue e) {
                            // Whatever.
                        }
                        break;
                    }
                } finally {
                    camera.release();
                }
            } catch (RuntimeException e) {
                Log.e("Quividi", "Cannot open camera: " + e);
            }
        }

        try {
            Manager.initialize();
            m_Manager = new LocalManager(this, settings);
        } catch (final Manager.LicenseError e) {
            Log.e("Quividi", "License error: " + e.getMessage() + " (code: " + e.getCode() + ")");
            new AlertDialog.Builder(this)
                    .setTitle("License error")
                    .setMessage(e.getMessage())
                    .setPositiveButton("Visit Quividi support", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent browse = new Intent(Intent.ACTION_VIEW, Uri.parse(e.getActivationURL()));
                            startActivity(browse);
                        }
                    })
                    .show();
            return;
        } catch (Manager.Error e) {
            Log.e("Quividi", "Fatal error: " + e);
            return;
        }

//        setContentView(m_Manager.getDisplayView());
        m_Manager.getDisplayView();

        try {
            m_API = new QuividiAPI();
        } catch (RuntimeException e) {
            Log.e("Quividi", "Cannot create API: " + e.getMessage());
            return;
        }

        m_API.addListener((DrawingListener) m_Manager.getDisplayView());
        m_API.SetMode(APIMode.Motion);
    }
}
