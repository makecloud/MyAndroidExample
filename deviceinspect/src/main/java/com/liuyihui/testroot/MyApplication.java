package com.liuyihui.testroot;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.liuyihui.mylibrary.util.FileUtil;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class MyApplication extends Application {
    private final String TAG = "MyApplication";
    public static Retrofit retrofit;
    /** sdcard/Android/data/com.xx.xx/files */
    public static String EXTERNAL_FILE_DIR;
    /** 素材文件夹 sdcard/Android/data/com.xx.xx/files/material/ */
    public static String MAT_DIR;
    /** 素材列表json 文件夹:sdcard/Android/data/com.xx.xx/files/material_list */
    public static String MAT_LIST_DIR;
    public static Context context;
    public static long s1;

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: " + (s1 = System.currentTimeMillis()));
        MyApplication.context = this;
        //创建带日志的okhttp client
        //声明日志类
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        //设定日志级别
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY :
                                                HttpLoggingInterceptor.Level.NONE);
        //自定义OkHttpClient
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder();
        //添加拦截器
        okHttpClient.addInterceptor(httpLoggingInterceptor);

        //初始化retrofit
        retrofit = new Retrofit.Builder().baseUrl("http://res.oohlink.com" + "/")
                                         .client(okHttpClient.build())
                                         .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                         .build();


        //return: sdcard/Android/data/com.xx.xx/files/
        EXTERNAL_FILE_DIR = context.getExternalFilesDir(null).getAbsolutePath();
        FileUtil.createDirIfNotExist(EXTERNAL_FILE_DIR);
        MAT_DIR = EXTERNAL_FILE_DIR + "/material";
        FileUtil.createDirIfNotExist(MAT_DIR);
        MAT_LIST_DIR = EXTERNAL_FILE_DIR + "/material_list";
        FileUtil.createDirIfNotExist(MAT_LIST_DIR);
    }

}
