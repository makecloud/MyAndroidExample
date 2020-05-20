package com.liuyihui.networkcontrol.queueDownload;

import android.os.Process;
import android.util.Log;

import com.liuyihui.networkcontrol.retrofitCommon.RetrofitHttpInterface;
import com.liuyihui.networkcontrol.retrofitCommon.RetryWhenNetworkException;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscription;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by gaowen on 2017/7/17.
 */

public class DownloadTask implements Runnable {

    private static final String TAG = "DownloadTask";
    private static final int DEFAULT_TIMEOUT = 50;
    private final DownInfo mDownloadInfo;

    private ProgressDownSubscriber mSubscriber;
    private Subscription subscription;

    public DownloadTask(DownInfo info) {
        this.mDownloadInfo = info;
    }

    public DownInfo getDownloadInfo() {
        return mDownloadInfo;
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        DownloadInterceptor interceptor = new DownloadInterceptor(mSubscriber);
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        httpClientBuilder.addInterceptor(interceptor);
        httpClientBuilder.retryOnConnectionFailure(true);
        if (false) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(httpLoggingInterceptor);
        }
        Retrofit mRetrofit = new Retrofit.Builder().client(httpClientBuilder.build())
                                                   .addConverterFactory(GsonConverterFactory.create())
                                                   .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                                   .baseUrl("http://101.201.30.129:8201" +
                                                                    "/platform-mobile/")
                                                   .build();

        RetrofitHttpInterface mDownloadApi = mRetrofit.create(RetrofitHttpInterface.class);
        Log.d(TAG, mDownloadInfo.getMatUrl() + ":bytes=" + mDownloadInfo.getReadSize());
        mSubscriber = new ProgressDownSubscriber(this);

        //execute subscribe
        subscription = mDownloadApi.sustainDownload("bytes=" + mDownloadInfo.getReadSize() + "-",
                                                    mDownloadInfo.getMatUrl())
                                   /*指定线程*/
                                   .subscribeOn(Schedulers.io())
                                   .unsubscribeOn(Schedulers.io())
                                   /*失败后的retry配置*/
                                   .retryWhen(new RetryWhenNetworkException())
                                   .map(new Func1<ResponseBody, Object>() {
                                       @Override
                                       public Object call(ResponseBody responseBody) {
                                           File file =
                                                   new File(mDownloadInfo.getSavePathName() + PlayerDownloadManager.FILE_TEMP);
                                           Log.d(TAG, "map call: " + "exists = " + file.exists());
                                           writeCaches(responseBody, file, mDownloadInfo);
                                           return mDownloadInfo;
                                       }
                                   })
                                   .observeOn(Schedulers.io())
                                   .subscribe(mSubscriber);
    }

    /**
     * @param responseBody
     * @param file
     * @param info
     */
    public void writeCaches(ResponseBody responseBody, File file, DownInfo info) {
        try {
            RandomAccessFile randomAccessFile = null;
            FileChannel channelOut = null;
            InputStream inputStream = null;
            try {
                long allLength;
                allLength = 0 == info.getMatSize() ? responseBody.contentLength() :
                        info.getReadSize() + responseBody
                        .contentLength();
                inputStream = responseBody.byteStream();
                randomAccessFile = new RandomAccessFile(file, "rwd");
                channelOut = randomAccessFile.getChannel();
                MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                                                               info.getReadSize(),
                                                               allLength - info.getReadSize());
                info.setDownloadSize(info.getReadSize());
                byte[] buffer = new byte[1024 * 4];
                int len;
                Log.d(TAG, "writeCaches: " + "download file" + file.getAbsolutePath());
                while ((len = inputStream.read(buffer)) != -1) {
                    info.setDownloadSize(info.getDownloadSize() + len);
                    info.downLoadProgress();
                    mappedBuffer.put(buffer, 0, len);
                }
            } catch (IOException e) {
                Log.d(TAG, "writeCaches: " + "error" + e.getMessage());
                throw new HttpException(e.getMessage(), -1);
            } finally {
                Log.d(TAG, "writeCaches: finally");
                if (inputStream != null) {
                    inputStream.close();
                }
                if (channelOut != null) {
                    channelOut.close();
                }
                if (randomAccessFile != null) {
                    randomAccessFile.close();
                }
            }
        } catch (IOException e) {
            throw new HttpException(e.getMessage(), -1);
        }
    }

    public void cancel() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}
