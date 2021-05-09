package com.oohlink.wifiprobe.realmdemo;

import android.app.Application;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        //初始化realm
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().name("realmdemo.realm")
                                                                    .schemaVersion(1)
                                                                    .deleteRealmIfMigrationNeeded()
                                                                    .build();
        Realm.setDefaultConfiguration(config);
    }
}
