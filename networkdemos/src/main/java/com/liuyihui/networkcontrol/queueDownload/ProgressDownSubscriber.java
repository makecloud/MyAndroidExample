package com.liuyihui.networkcontrol.queueDownload;

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
    private Subscription subscription;

    public ProgressDownSubscriber(DownloadTask downloadTask) {
        this.mDownloadTask = downloadTask;
        this.downInfo = downloadTask.getDownloadInfo();
    }

    @Override
    public void onCompleted() {
        downInfo.setDownloadState(DownState.FINISH.getState());
        PlayerDownloadManager.getInstance().deleteDownInfoToDB(downInfo);
        PlayerDownloadManager.getInstance().finished(mDownloadTask);
        Log.d(TAG, "onCompleted: " + downInfo.getMatUrl());
        File oldFile = new File(downInfo.getSavePathName() + PlayerDownloadManager.FILE_TEMP);
        File file = new File(downInfo.getSavePathName());
        //如果下载的是vpn文件，重命名去掉.tmp
        if (downInfo.getType() == DownInfo.FileType.VPN) {
            oldFile.renameTo(file);
            downInfo.downLoadCompleted();
            return;
        }
        //
        if (oldFile.exists()) {
            String fileMd5 = MD5Util.getFileMD5String(oldFile);
            Log.d(TAG,
                  "onCompleted: " + String.format("file md5:%s,should md5:%s",
                                                  fileMd5,
                                                  downInfo.getMatMd5()));
            Log.d(TAG, "onCompleted: contentType" + downInfo.getConetntType());
            boolean webViewIsVideo = downInfo.getConetntType() != null && downInfo.getConetntType()
                                                                                  .contains(
                                                                                          "video");
            //contentType代表视频或MD5相等,重命名fixme
            if (webViewIsVideo || downInfo.getMatMd5().equalsIgnoreCase(fileMd5)) {
                Log.d(TAG, "onCompleted: rename " + oldFile.renameTo(file));
                downInfo.downLoadCompleted();
                Log.d(TAG, "onCompleted: webViewIsVideo或者md5相等，执行重命名去掉.tmp");
            } else {
                //删除文件
                Log.d(TAG, "onCompleted: delete file, " + oldFile.delete());
            }
        }
    }

    @Override
    public void onError(Throwable e) {
        Log.d(TAG, "onError: " + e.getMessage());
        Log.d(TAG, "onError: " + downInfo.getMatUrl());
        downInfo.setDownloadState(DownState.ERROR.getState());
        PlayerDownloadManager.getInstance().saveDownInfoToDB(downInfo);
        PlayerDownloadManager.getInstance().finished(mDownloadTask);
        PlayerDownloadManager.getInstance().fileDownError(downInfo);
    }

    @Override
    public void onNext(T t) {
    }

    @Override
    public void update(long bytesRead, long contentLength, boolean done) {
        Log.d(TAG, "update:" + bytesRead);

        if (downInfo.getMatSize() > contentLength) {
            bytesRead = downInfo.getMatSize() - contentLength + bytesRead;
        } else {
            downInfo.setMatSize(contentLength);
        }
        downInfo.setReadSize(bytesRead);
    }
}
