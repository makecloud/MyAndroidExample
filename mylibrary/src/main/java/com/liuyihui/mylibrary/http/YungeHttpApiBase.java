package com.liuyihui.mylibrary.http;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.client.mylibrary.entitiy.system.ApiResponse;
import com.client.mylibrary.exception.ExceptionCode;
import com.client.mylibrary.exception.OohlinkException;
import com.client.mylibrary.io.OohlinkSerializer;
import com.client.mylibrary.util.ContextUtil;
import com.client.mylibrary.util.LogUtil;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * http接口类
 * Created by liuyh on 2016/10/26.
 */

public class YungeHttpApiBase {
    private final static String TAG = "YungeHttpApiBase";
    private final static boolean printUrlLog = true;//是否打印url日志标志
    private static OkHttpClient okHttpClient;

    /**
     * 构造方法
     */
    public YungeHttpApiBase() {
    }

    /**
     * 获取单例okhttpClient
     *
     * @return
     */
    public static OkHttpClient getOkHttpClient() {
        return OkHttpClientInstanceHolder.okHttpClient;
    }

    /**
     * 从url获取json响应
     * 使用 okhttp3 版本
     *
     * @param url get请求的url
     * @return 返回json
     * @throws OohlinkException
     */
    public static final String getJsonFromUrl(String url) throws OohlinkException {
        //打印url，测试查看
        LogUtil.d(TAG, url);
        //创建client，request,response
        OkHttpClient client = getOkHttpClient();
        Response response = null;
        Request request = new Request.Builder().url(url).build();
        //执行，处理响应
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new OohlinkException("http code " + response.code());
            }
            String responseJsonStr = response.body().string();
            if (TextUtils.isEmpty(responseJsonStr)) {
                throw new OohlinkException("服务器响应空内容");
            }
            return responseJsonStr;
        } catch (IOException e) {
            LogUtil.e(TAG, e.getMessage(), e);
            throw new OohlinkException("网络连接失败");
        } finally {
            if (response != null) {
                response.close();
            }
        }


    }

    /**
     * 从url获取输入流
     *
     * @param url 返回输入流的url
     * @return 输入流对象
     * @throws OohlinkException
     */
    public static final InputStream getInputStreamFromUrl(String url) throws OohlinkException {
        OkHttpClient client = getOkHttpClient();
        Request request = new Request.Builder().url(url).build();
        //执行，处理响应
        try {
            Response response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new OohlinkException("服务器错误" + response.code());
            }
            ResponseBody rb = response.body();
            InputStream is = rb.byteStream();
            if (is == null || is.available() == 0) {
                throw new OohlinkException("获取的输入流中字节数量为0");
            }
            rb.close();
            return is;
        } catch (IOException e) {
            LogUtil.e(TAG, e.getMessage(), e);
            throw new OohlinkException("网络连接失败");
        }
    }

    /**
     * 从url获取字节数组
     *
     * @param url
     * @return 字节数组
     * @throws OohlinkException
     */
    public static final byte[] getByteArrayFromUrl(String url) throws OohlinkException {
        if (printUrlLog) {
            LogUtil.d(TAG, url);
        }
        OkHttpClient client = getOkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Response response = null;
        //执行，处理响应
        try {
            response = client.newCall(request).execute();
            if (!response.isSuccessful()) {
                throw new OohlinkException("服务器错误" + response.code());
            }
            ResponseBody rb = response.body();
            byte[] byteArray = rb.bytes();
            return byteArray;
        } catch (Exception e) {
            throw new OohlinkException("获取验证码 网络连接失败");
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    /**
     * 从url获取字节数组输入流
     *
     * @param url
     * @return ByteArrayInputStream对象
     * @throws OohlinkException
     */
    public static final ByteArrayInputStream getByteArrayInputStreamFromUrl(String url) throws OohlinkException {
        byte[] byteArray = getByteArrayFromUrl(url);
        ByteArrayInputStream bais = new ByteArrayInputStream(byteArray);
        return bais;
    }

    /**
     * 从url获取apiResponse对象
     * 已处理apiResponse==null
     *
     * @param url 请求地址
     * @return 对data转换类型时注意：此时返回的apiresponse的data属性为JSONObject型
     */
    public static final ApiResponse getApiResponseObjectFromUrl(String url) throws OohlinkException {
        ApiResponse apiResponse;
        String jsonstr = getJsonFromUrl(url);
        apiResponse = JSON.parseObject(jsonstr, ApiResponse.class);
        if (apiResponse == null) {
            throw new OohlinkException("服务器响应内容为空");
        }
        //处理token失效
        if (apiResponse.getCode() == ExceptionCode.TOKEN_INVALID.getValue()) {
            //删除缓存中的登录用户缓存json
            try {
                OohlinkSerializer.saveLoginResultToFile("");
            } catch (OohlinkException e) {
                LogUtil.e(TAG, e.getMessage(), e);
                throw new OohlinkException("登录信息异常");
            }
            //跳转到登录页
            gotoLoginActivity();
            throw new OohlinkException("登录无效,请重新登录");
        }
        return apiResponse;
    }

    /**
     * 打开登录页.当前所在的activity会销毁吗?
     */
    public static void gotoLoginActivity() {
        Context ctx = ContextUtil.getContext();
        Intent i = new Intent();
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setClassName(ctx, "com.yunge.client.media.common.activity.LoginActivity");
        ctx.startActivity(i);
        LogUtil.i(TAG, "need re login , start login page");
    }

    /**
     * 从url 获取apiResponse对象的data对象（data为JSONObject型）。
     * <p>
     * 调用此方法前提条件：apiResponse的data值必须为单个对象.已经历过:响应中data为数字时("data":0),方法抛异常cannot cast to JSONObject
     *
     * @param url
     * @return
     * @throws OohlinkException
     */
    public static final JSONObject getJSONObjectDataFromUrl(String url) throws OohlinkException {
        ApiResponse apiResponse = getApiResponseObjectFromUrl(url);
        int code = apiResponse.getCode();
        if (code != 0) {
            throw new OohlinkException(apiResponse.getMessage());
        }
        JSONObject jsonObjectData = (JSONObject) apiResponse.getData();
        return jsonObjectData;
    }

    /**
     * 从url 获取apiResponse对象的data列表对象（JSONArray类型）。
     * 调用前提：apiResponse的data为列表时
     *
     * @param url
     * @return
     * @throws OohlinkException
     */
    public static final JSONArray getJSONArrayDataFromUrl(String url) throws OohlinkException {
        ApiResponse apiResponse = getApiResponseObjectFromUrl(url);
        int code = apiResponse.getCode();
        if (code != 0) {
            throw new OohlinkException("服务器:" + apiResponse.getMessage());
        }
        JSONArray jsonArrayData = (JSONArray) apiResponse.getData();
        if (jsonArrayData == null) {
            jsonArrayData = new JSONArray();
        }
        return jsonArrayData;
    }

    /**
     * 从url获取指定类型的对象（实体对象）。
     * apiResponse的data为单个对象时
     *
     * @param url
     * @param clazz 指定类型的class对象
     * @param <T>
     * @return 返回值可为能null
     * @throws OohlinkException
     */
    public static final <T> T getTDataFromUrl(String url, Class<T> clazz) throws OohlinkException {
        JSONObject jsonObject = getJSONObjectDataFromUrl(url);
        if (jsonObject == null) {
            return null;
        }
        T t = (T) jsonObject.toJavaObject(clazz);
        return t;
    }

    /**
     * 从url获取指定元素类型的列表。
     * 依赖apiResponse的data为列表时
     *
     * @param url
     * @param clazz list元素的class对象
     * @param <T>
     * @return
     * @throws OohlinkException
     */
    public static final <T> List<T> getTListDataFromUrl(String url, Class<T> clazz) throws OohlinkException {
        String arrayStr = getJSONArrayDataFromUrl(url).toJSONString();
        List<T> tList;
        tList = JSON.parseArray(arrayStr, clazz);
        return tList;
    }

    /**
     * 读取消息专用，post请求
     *
     * @param url
     * @param postJsonStr
     * @return
     * @throws OohlinkException
     */
    public static final ApiResponse postJsonToUrl(String url, String postJsonStr) throws OohlinkException {
        //创建httpclient
        OkHttpClient client = getOkHttpClient();
        //创建requestbuilder
        Request.Builder requestBuilder = new Request.Builder();
        //创建post请求体
        //        RequestBody multipartBody = RequestBody.create(MediaType.parse("application/json;charset=utf-8"), postJsonStr);

        //        MultipartBody multipartBody = new MultipartBody.Builder().setType(MediaType.parse("multipart/form-data"))
        //                .addFormDataPart("messageIdList", postJsonStr).build();

        RequestBody multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM).addFormDataPart("messageIdList", postJsonStr).build();

        //构建请求
        Request request = requestBuilder.url(url).post(multipartBody).build();
        //响应
        Response response;
        String responseStr;
        ApiResponse apiResponse;
        try {
            //得到响应
            response = client.newCall(request).execute();
            responseStr = response.body().string();
            apiResponse = JSON.parseObject(responseStr, ApiResponse.class);
            return apiResponse;
        } catch (IOException e) {
            LogUtil.e(TAG, e.getMessage(), e);
            throw new OohlinkException("网络连接失败");
        }

    }

    /**
     * 提交post请求到url，返回响应对象（已处理响应为空）
     *
     * @param activity
     * @param url      地址
     * @param formMap  请求表单（键值对）
     * @return ApiResponse
     * @throws OohlinkException
     */
    public static ApiResponse postFormDataToUrl(Activity activity, String url, final Map<String, String> formMap) throws OohlinkException {
        ApiResponse apiResponse = null;

        //创建http客户端
        OkHttpClient client = getOkHttpClient();
        //请求体builder
        MultipartBody.Builder multipartBodyBuilder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        //遍历map添加到请求体
        Iterator it = formMap.keySet().iterator();
        while (it.hasNext()) {
            String formKey = (String) it.next();
            String formValue = formMap.get(formKey);
            multipartBodyBuilder.addFormDataPart(formKey, formValue);
        }
        //构建请求体
        RequestBody multipartBody = multipartBodyBuilder.build();
        //构建请求
        Request request = new Request.Builder().url(url).post(multipartBody).build();
        //执行请求
        Response response = null;
        try {
            response = client.newCall(request).execute();
            apiResponse = JSON.parseObject(response.body().string(), ApiResponse.class);
        } catch (IOException e) {
            LogUtil.e(TAG, e.getMessage(), e);
            throw new OohlinkException("网络连接失败");
        }
        //处理响应为空
        if (apiResponse == null) {
            throw new OohlinkException("服务器响应内容为空");
        }

        return apiResponse;

    }

    /**
     * okhttpClient单例holder
     */
    private static class OkHttpClientInstanceHolder {
        private static OkHttpClient okHttpClient = new OkHttpClient();
    }

}
