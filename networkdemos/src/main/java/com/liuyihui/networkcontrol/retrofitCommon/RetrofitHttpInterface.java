package com.liuyihui.networkcontrol.retrofitCommon;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

public interface RetrofitHttpInterface {

    // option 2: using a dynamic URL
    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);


    /*断点续传下载接口*/
    @GET
    @Streaming
    Observable<ResponseBody> sustainDownload(@Header("Range") String range, @Url String url);
}
