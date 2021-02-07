package com.liuyihui.networkcontrol.http.retrofitCommon;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * okhttp 网络通信 拦截器
 */
public class NetInterceptor implements Interceptor {
    private final String TAG = "NetInterceptor";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Connection", "close").build();
        Log.d(TAG, "execute intercept: ");
        Response response = chain.proceed(request);
        Log.d(TAG, "execute intercept: " + response.toString());
        return response;
    }

}


