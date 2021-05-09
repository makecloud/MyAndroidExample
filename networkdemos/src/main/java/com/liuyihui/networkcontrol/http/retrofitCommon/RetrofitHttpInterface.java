package com.liuyihui.networkcontrol.http.retrofitCommon;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
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


    @GET
    Observable<ApiResponse> getMyInfo(@Url String url);

    @POST
    @FormUrlEncoded
    Call<ResponseBody> getTGT(@Url String url,
                              @Field("username") String userName,
                              @Field("password") String password);

    @POST
    @FormUrlEncoded
    Observable<String> getST(@Url String url, @Field("service") String service);


}
