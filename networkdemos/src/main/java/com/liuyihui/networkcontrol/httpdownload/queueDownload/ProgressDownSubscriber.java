package com.liuyihui.networkcontrol.httpdownload.queueDownload;

import android.util.Log;

import com.liuyihui.mylibrary.util.MD5Util;

import java.io.File;

import rx.Observer;
import rx.Subscription;


/**
 * Created by gaowen on 2017/7/17.
 */

public class ProgressDownSubscriber<T> implements DownloadProgressListener, Observer<T> {
    private final String TAG = "ProgressDownSubscriber";
    private DownInfo downInfo;
    private DownloadTask mDownloadTask;
    private Subscription mDisposable;

    public ProgressDownSubscriber(DownloadTask downloadTask) {
        this.mDownloadTask = downloadTask;
        this.downInfo = downloadTask.getDownloadInfo();
    }

    @Override
    public void onError(Throwable e) {
        Log.e(TAG, "onError:" + downInfo.getFileUrl(), e);
        downInfo.setDownloadState(DownState.ERROR.getState());
        DownloadManager.getInstance().saveDownInfoToDB(downInfo);
        DownloadManager.getInstance().onDownloadTaskFinished(mDownloadTask);
        DownloadManager.getInstance().fileDownError(downInfo);
    }

    @Override
    public void onCompleted() {
        downInfo.setDownloadState(DownState.FINISH.getState());
        DownloadManager.getInstance().deleteDownInfoToDB(downInfo);
        DownloadManager.getInstance().onDownloadTaskFinished(mDownloadTask);

        Log.d(TAG, "onCompleted:" + downInfo.getFileUrl());
        File oldFile = new File(downInfo.getSavePathName() + DownloadManager.FILE_TEMP);
        File file = new File(downInfo.getSavePathName());

        //
        if (oldFile.exists()) {
            String fileMd5 = MD5Util.getFileMD5String(oldFile);
            Log.d(TAG,
                  "onCompleted: " + String.format("file md5:%s,should md5:%s",
                                                  fileMd5,
                                                  downInfo.getFileMd5()));
            Log.d(TAG, "onCompleted: contentType " + downInfo.getContentType());

            //MD5相等,下载成功,重命名
            if (downInfo.getFileMd5().equalsIgnoreCase(fileMd5)) {
                Log.d(TAG, "onCompleted: rename " + oldFile.renameTo(file));
                downInfo.downLoadCompleted();
                Log.d(TAG, "onCompleted: md5相等，执行重命名去掉.tmp");
            } else {
                //删除文件
                Log.d(TAG, "onCompleted: delete file, " + oldFile.delete());
            }
        }

    }


    @Override
    public void onNext(T t) {
        Log.d(TAG, "onNext: ");
    }

    @Override
    public void onUpdate(long bytesRead, long contentLength, boolean done) {
        //自定义的ResponseBody里的source读取回调
        Log.d(TAG, "onUpdate: " + bytesRead);
        if (downInfo.getFileSize() > contentLength) {
            bytesRead = downInfo.getFileSize() - contentLength + bytesRead;
        } else {
            downInfo.setFileSize(contentLength);
        }
        downInfo.setReadSize(bytesRead);

        //TODO 通知进度条更新
    }

    public void unsubscribe() {
        if (mDisposable != null) {
            mDisposable.unsubscribe();
        }
    }
}
