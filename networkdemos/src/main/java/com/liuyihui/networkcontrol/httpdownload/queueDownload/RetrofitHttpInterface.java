package com.liuyihui.networkcontrol.httpdownload.queueDownload;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

public interface RetrofitHttpInterface {


    /*断点续传下载接口*/
    @GET
    @Streaming
    Observable<ResponseBody> sustainDownload(@Header("Range") String range, @Url String url);

}
