package com.liuyihui.networkcontrol.httpdownload.queueDownload;

import android.os.Process;
import android.util.Log;

import com.liuyihui.mylibrary.util.MD5Util;
import com.liuyihui.networkcontrol.http.retrofitCommon.RetryWhenNetworkException;

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
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;

/**
 * 可执行下载任务
 */

public class DownloadTask implements Runnable, DownloadProgressListener {

    private static final String TAG = "DownloadTask";
    private static final int DEFAULT_TIMEOUT = 50;
    private static final long THRESHOLD = 800 * 1024 * 1024;
    public static final String FILE_TEMP = ".tmp";
    private final DownInfo mDownloadInfo;

    private Subscription subscription;

    public DownloadTask(DownInfo info) {
        this.mDownloadInfo = info;
    }

    public DownInfo getDownloadInfo() {
        return mDownloadInfo;
    }

    public void checkDowninfoBreakResume() {
        Log.d(TAG,
              String.format("checkDowninfoBreakResume: %s : %s :%s",
                            mDownloadInfo.getFileUrl(),
                            mDownloadInfo.getFileMd5(),
                            mDownloadInfo.getSavePathName()));
        //断点续传。判断是否为续传
        File file = new File(mDownloadInfo.getSavePathName() + FILE_TEMP);
        boolean isFileExist = file.exists();
        if (isFileExist) {
            DownInfo downInfo = DataRepository.getInstance()
                                              .getDownInfoFromDB(mDownloadInfo.getFileUrl());
            if (downInfo != null) {
                mDownloadInfo.setFileSize(downInfo.getFileSize());
                mDownloadInfo.setReadSize(downInfo.getReadSize());

            }
        }
    }

    @Override
    public void run() {
        Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
        DownloadInterceptor interceptor = new DownloadInterceptor(this);
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                         .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                         .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                         .addInterceptor(interceptor)
                         .retryOnConnectionFailure(true);
        if (false) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            httpClientBuilder.addInterceptor(httpLoggingInterceptor);
        }
        Retrofit mRetrofit = new Retrofit.Builder().client(httpClientBuilder.build())
                                                   .addConverterFactory(GsonConverterFactory.create())
                                                   .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                                   .baseUrl("http://101.201.30" + ".129:8201" +
                                                                    "/platform-mobile/")
                                                   .build();

        RetrofitHttpInterface mDownloadApi = mRetrofit.create(RetrofitHttpInterface.class);
        Log.d(TAG,
              "run() start download" + mDownloadInfo.getFileUrl() + " exist bytes=" + mDownloadInfo.getReadSize());


        // 调用接口返回responseBody。可以将此时返回的responseBody理解为只是可以打开去读取的一个输入流对象，要下
        // 载数据还没有落到本地。只是你可以从这个responseBody读数据了。
        subscription = mDownloadApi.sustainDownload("bytes=" + mDownloadInfo.getReadSize() + "-",
                                                    mDownloadInfo.getFileUrl())
                                   .subscribeOn(Schedulers.io())
                                   .unsubscribeOn(Schedulers.io())
                                   .retryWhen(new RetryWhenNetworkException())
                                   .observeOn(Schedulers.io())
                                   .subscribe(new Subscriber<ResponseBody>() {
                                       @Override
                                       public void onCompleted() {
                                           mDownloadInfo.setDownloadState(DownState.FINISH.getState());
                                           DownloadManager.getInstance()
                                                          .deleteDownInfoToDB(mDownloadInfo);
                                           DownloadManager.getInstance()
                                                          .onDownloadTaskFinished(DownloadTask.this);

                                           Log.d(TAG, "onCompleted:" + mDownloadInfo.getFileUrl());
                                           File oldFile =
                                                   new File(mDownloadInfo.getSavePathName() + DownloadManager.FILE_TEMP);
                                           File file = new File(mDownloadInfo.getSavePathName());

                                           //
                                           if (oldFile.exists()) {
                                               String fileMd5 = MD5Util.getFileMD5String(oldFile);
                                               Log.d(TAG,
                                                     "onCompleted: " + String.format(
                                                             "file md5:%s,should md5:%s",
                                                             fileMd5,
                                                             mDownloadInfo.getFileMd5()));
                                               Log.d(TAG,
                                                     "onCompleted: contentType " + mDownloadInfo.getContentType());

                                               //MD5相等,下载成功,重命名
                                               if (mDownloadInfo.getFileMd5()
                                                                .equalsIgnoreCase(fileMd5)) {
                                                   Log.d(TAG,
                                                         "onCompleted: rename " + oldFile.renameTo(
                                                                 file));
                                                   mDownloadInfo.downLoadCompleted();
                                                   Log.d(TAG, "onCompleted: md5相等，执行重命名去掉.tmp");
                                               } else {
                                                   //删除文件
                                                   Log.d(TAG,
                                                         "onCompleted: delete file, " + oldFile.delete());
                                               }
                                           }
                                       }

                                       @Override
                                       public void onError(Throwable e) {
                                           Log.e(TAG, "onError:" + mDownloadInfo.getFileUrl(), e);
                                           mDownloadInfo.setDownloadState(DownState.ERROR.getState());
                                           DownloadManager.getInstance()
                                                          .saveDownInfoToDB(mDownloadInfo);
                                           DownloadManager.getInstance()
                                                          .onDownloadTaskFinished(DownloadTask.this);
                                           DownloadManager.getInstance()
                                                          .fileDownError(mDownloadInfo);
                                       }

                                       @Override
                                       public void onNext(ResponseBody responseBody) {
                                           //存储文件数据，即下载
                                           File file =
                                                   new File(mDownloadInfo.getSavePathName() + DownloadManager.FILE_TEMP);
                                           Log.d(TAG,
                                                 "onNext: " + "file exists = " + file.exists());
                                           if (responseBody.contentLength() > THRESHOLD) {
                                               //将responseBody里的数据以字节流方式读写到本地文件
                                               writeDataFromStream(responseBody,
                                                                   file,
                                                                   mDownloadInfo);
                                           } else {
                                               //将responseBody里的数据以MappedBuffer方式映射到本地文件
                                               writeCaches(responseBody, file, mDownloadInfo);
                                           }
                                       }
                                   });
    }

    private void writeDataFromStream(ResponseBody response, File file, DownInfo info) {
        InputStream is = null;
        RandomAccessFile savedFile = null;
        try {
            if (response != null) {
                is = response.byteStream();
                savedFile = new RandomAccessFile(file, "rw");
                //跳过已经下载的字节
                savedFile.seek(info.getReadSize());
                byte[] b = new byte[1024];
                int len;

                //循环写文件
                while ((len = is.read(b)) != -1) {
                    savedFile.write(b, 0, len);
                    info.setDownloadSize(info.getDownloadSize() + len);
                    info.onDownLoadProgress();
                }
                response.close();
            }
        } catch (IOException e) {
            Log.e(TAG, "writeDataFromStream: ", e);
            throw new ApiException(e.getMessage(), -1);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (savedFile != null) {
                    savedFile.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                allLength = 0 == info.getFileSize() ? responseBody.contentLength() :
                        info.getReadSize() + responseBody
                        .contentLength();
                inputStream = responseBody.byteStream();
                randomAccessFile = new RandomAccessFile(file, "rwd");
                channelOut = randomAccessFile.getChannel();
                MappedByteBuffer mappedBuffer = channelOut.map(FileChannel.MapMode.READ_WRITE,
                                                               info.getReadSize(),
                                                               allLength - info.getReadSize());
                //info.setDownloadSize(info.getReadSize());
                byte[] buffer = new byte[1024 * 4];
                int len;
                Log.d(TAG, "writeCaches: " + file.getAbsolutePath());
                //循环读+写。读的时候走自定义responseBody内的source read。
                while ((len = inputStream.read(buffer)) != -1) {
                    //更新downinfo对象
                    info.setDownloadSize(info.getDownloadSize() + len);
                    info.onDownLoadProgress();
                    //映射数据至文件
                    mappedBuffer.put(buffer, 0, len);
                }
            } catch (IOException e) {
                Log.e(TAG, "writeCaches: ", e);
                throw new ApiException(e.getMessage(), -1);
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
            throw new ApiException(e.getMessage(), -1);
        }
    }

    @Override
    public void onUpdate(long bytesRead, long contentLength, boolean done) {
        //自定义的ResponseBody里的source读取回调
        Log.d(TAG, "onUpdate: " + bytesRead);
        if (mDownloadInfo.getFileSize() > contentLength) {
            bytesRead = mDownloadInfo.getFileSize() - contentLength + bytesRead;
        } else {
            mDownloadInfo.setFileSize(contentLength);
        }
        mDownloadInfo.setReadSize(bytesRead);

        //TODO 通知进度条更新
    }

    public void cancel() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }
}
