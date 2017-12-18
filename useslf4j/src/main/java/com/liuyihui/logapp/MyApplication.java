package com.liuyihui.logapp;

import android.app.Application;

import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.android.AndroidManifestPropertiesUtil;
import ch.qos.logback.core.joran.spi.JoranException;

/**
 * Created by liuyi on 2017/11/21.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        LoggerContext loggercontext = (LoggerContext) LoggerFactory.getILoggerFactory();
        try {
            AndroidManifestPropertiesUtil.setAndroidProperties(loggercontext);
        } catch (JoranException e) {
            e.printStackTrace();
        }
    }
}
