package com.liuyihui.networkcontrol.retrofit2convertor;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 重写的converter
 *
 * @param <T>
 */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final static String TAG = "GsonResBodyConverter";
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        JsonReader jsonReader = gson.newJsonReader(value.charStream());
        try {
            //强转防止接口返回不同类型结构抛异常
            ApiResponse apiResponse = (ApiResponse) adapter.read(jsonReader);
            if (apiResponse.getCode() != 0) {
                if (apiResponse.getCode() == ExceptionCode.TOKEN_INVALID.getValue()) {
                    handleTokenInvalid(apiResponse);
                } else {
                    throw new ApiException(apiResponse.getCode(), apiResponse.getMessage());
                }
            }
            return (T) apiResponse;
        } finally {
            value.close();
        }
    }

    /**
     * token失效处理
     *
     * @param apiResponse
     */
    public void handleTokenInvalid(ApiResponse apiResponse) throws IOException {
        throw new IOException("登录失效,请重新登录");
    }

}