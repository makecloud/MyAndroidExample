package com.liuyihui.facedetect_quividi_demo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class DetectService extends Service {
    public DetectService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
