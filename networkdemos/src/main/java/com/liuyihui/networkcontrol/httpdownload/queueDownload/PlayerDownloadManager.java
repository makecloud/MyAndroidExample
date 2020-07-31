package com.liuyihui.networkcontrol.httpdownload.queueDownload;

import android.support.annotation.NonNull;
import android.util.Log;


import java.io.File;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by gaowen on 2017/7/10.
 */

public class PlayerDownloadManager {

    private static final String TAG = "PlayerDownloadManager";
    private ExecutorService mExecutorService;
    private DataRepository mDataRepository;
    private List<String> fileDownErrorList = new ArrayList<>();
    private List<String> fileDownloadList = new ArrayList<>();
    private int maxRequests = 3;
    /** 准备下载中的任务 */
    private final Deque<DownloadTask> readyDownInfoCalls = new ArrayDeque<>();
    /** 正在下载中的任务 */
    private final Deque<DownloadTask> runningDownInfoCalls = new ArrayDeque<>();
    public static final String FILE_TEMP = ".tmp";

    private PlayerDownloadManager() {
        mExecutorService = new ThreadPoolExecutor(0, Integer.MAX_VALUE, 60, TimeUnit.SECONDS,
                                                  new SynchronousQueue<Runnable>());
    }

    public static PlayerDownloadManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final PlayerDownloadManager INSTANCE = new PlayerDownloadManager();
    }

    public void setPlayerDataRepository(DataRepository playerDataRepository) {
        this.mDataRepository = playerDataRepository;
    }

    public synchronized void startDown(@NonNull DownInfo info) {
        if (info == null) {
            return;
        }
        if (!fileDownloadList.contains(info.getMatUrl())) {
            fileDownloadList.add(info.getMatUrl());
            DownloadTask downloadTask = getDownloadTask(info);
            boolean highPriority = DownInfo.FileType.REVERSE_MATERIAL.equals(info.getType())
                    || DownInfo.FileType.APK.equals(info.getType());
            startDownTask(downloadTask, highPriority);
        }
    }

    private void startDownTask(@NonNull DownloadTask downloadTask, boolean highPriority) {
        if (runningDownInfoCalls.size() < maxRequests || highPriority) {
            runningDownInfoCalls.add(downloadTask);
            mExecutorService.execute(downloadTask);
        } else {
            readyDownInfoCalls.add(downloadTask);
        }
    }

    private DownloadTask getDownloadTask(DownInfo info) {
        Log.d(TAG, "startDown:"
                + info.getMatUrl()
                + "："
                + info.getMatMd5()
                + ":"
                + info.getSavePathName());
        File file = new File(info.getSavePathName() + FILE_TEMP);
        boolean isFileExist = file.exists();
        if (isFileExist) {
            DownInfo downInfo = mDataRepository.getDownInfoFromDB(info.getMatMd5());
            if (downInfo != null) {
                info.setMatSize(downInfo.getMatSize());
                info.setReadSize(downInfo.getReadSize());
            }
        }
        return new DownloadTask(info);
    }

    private void promoteCalls() {
        if (runningDownInfoCalls.size() >= maxRequests) {
            return;
        }
        if (readyDownInfoCalls.isEmpty()) {
            return;
        }
        for (Iterator<DownloadTask> i = readyDownInfoCalls.iterator(); i.hasNext(); ) {
            DownloadTask downloadTask = i.next();
            i.remove();
            runningDownInfoCalls.add(downloadTask);
            mExecutorService.execute(downloadTask);
            if (runningDownInfoCalls.size() >= maxRequests) {
                return;
            }
        }
    }

    public void finished(DownloadTask downloadTask) {
        synchronized (this) {
            runningDownInfoCalls.remove(downloadTask);
            fileDownloadList.remove(downloadTask.getDownloadInfo().getMatUrl());
            promoteCalls();
        }
    }

    public void fileDownError(DownInfo downInfo) {
        if (!fileDownErrorList.contains(downInfo.getMatUrl())) {
            fileDownErrorList.add(downInfo.getMatUrl());
            mDataRepository.reportAlertInfo(1, downInfo.getMatUrl());
        }
    }

    public void saveDownInfoToDB(DownInfo downInfo) {
        Log.d(TAG, "saveDownInfoToDB:"
                + downInfo.getMatUrl()
                + ","
                + downInfo.getReadSize()
                + ","
                + downInfo.getDownloadState());
        mDataRepository.saveDownInfoToDB(downInfo);
    }

    public void deleteDownInfoToDB(DownInfo downInfo) {
        fileDownErrorList.remove(downInfo.getMatUrl());
        mDataRepository.deleteDownInfoToDB(downInfo);
    }

    /**
     * 停止下载
     */
    public void stopDown(DownloadTask downloadTask) {
        if (downloadTask == null) {
            return;
        }
        downloadTask.cancel();
    }

    /**
     * 停止全部下载
     */
    public void stopAllDown() {
        for (DownloadTask downloadTask : runningDownInfoCalls) {
            stopDown(downloadTask);
        }

        for (DownloadTask downloadTask : readyDownInfoCalls) {
            stopDown(downloadTask);
        }
    }
}
