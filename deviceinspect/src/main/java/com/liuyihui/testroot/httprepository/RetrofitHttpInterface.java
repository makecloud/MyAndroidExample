package com.liuyihui.testroot.httprepository;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface RetrofitHttpInterface {

    // option 2: using a dynamic URL
    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);
}
