package com.liuyihui.networkcontrol;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;

import com.liuyihui.networkcontrol.retrofitCommon.MyGsonConverterFactory;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

public class MyApplication extends Application {
    public static Retrofit retrofit;
    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();

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
        retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL + "/")
                                         .client(okHttpClient.build())
                                         .addConverterFactory(MyGsonConverterFactory.create())
                                         .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                         .build();

    }

}
