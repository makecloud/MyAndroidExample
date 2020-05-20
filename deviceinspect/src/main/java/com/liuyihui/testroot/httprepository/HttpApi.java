package com.liuyihui.testroot.httprepository;

import android.support.annotation.NonNull;
import android.util.Log;


import com.liuyihui.mylibrary.util.FileUtil;
import com.liuyihui.testroot.MyApplication;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HttpApi {
    private static final String TAG = "HttpApi";
    private RetrofitHttpInterface retrofitHttpItf;

    private HttpApi() {
        Log.d(TAG, "new HttpApi: ");
        retrofitHttpItf = MyApplication.retrofit.create(RetrofitHttpInterface.class);
    }

    private static class InstanceHolder {
        private static HttpApi positionHttpApi = new HttpApi();
    }

    public static HttpApi getInstance() {
        return InstanceHolder.positionHttpApi;
    }


    /**
     * 下载file
     *
     * @param fileUrl   文件url
     * @param storePath 存储的文件名路径
     */
    public void downFile(String fileUrl, String storePath) {

        //检查
        final File file = new File(storePath);
        if (!file.getParentFile().exists()) {
            FileUtil.createDirIfNotExist(file.getParent());
        }

        //启动下载
        Call<ResponseBody> call = retrofitHttpItf.downloadFileWithDynamicUrlSync(fileUrl);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call,
                                   @NonNull Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Log.d(TAG, "server contacted and has file");

                    boolean writtenToDisk = writeResponseBodyToDisk(response.body(), file);

                    Log.d(TAG, "file download was a success? " + writtenToDisk);
                } else {
                    Log.d(TAG, "server contact failed");
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "error");
            }
        });
    }


    /**
     * 写如文件
     *
     * @param responseBody
     * @param outFile
     * @return
     */
    private boolean writeResponseBodyToDisk(final ResponseBody responseBody, final File outFile) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(outFile);
                    fos.write(responseBody.bytes());
//                    return true;
                } catch (IOException e) {
                    Log.e(TAG, "writeResponseBodyToDisk: ", e);
//                    return false;
                } finally {
                    if (fos != null) {
                        try {
                            fos.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }).start();
        return true;
    }


}
