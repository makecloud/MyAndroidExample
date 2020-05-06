package com.liuyihui.wifip2pdemo;

import android.app.Service;
import android.content.Intent;
import android.net.wifi.p2p.nsd.WifiP2pServiceInfo;
import android.os.IBinder;
import android.os.Parcel;

public class WifiP2pDiscoverService extends Service implements WifiP2pServiceInfo.Creator {
    public WifiP2pDiscoverService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Object createFromParcel(Parcel source) {
        return null;
    }

    @Override
    public Object[] newArray(int size) {
        return new Object[0];
    }
}
