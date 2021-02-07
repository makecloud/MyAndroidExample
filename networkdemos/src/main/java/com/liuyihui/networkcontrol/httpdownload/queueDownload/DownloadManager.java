package com.liuyihui.networkcontrol.httpdownload.queueDownload;

import android.support.annotation.NonNull;
import android.util.Log;

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
 * 下载管理类
 * <p>
 * Created by gaowen on 2017/7/10.
 */
public class DownloadManager {

    private static final String TAG = "DownloadManager";
    private ExecutorService mExecutorService;
    private List<String> fileDownErrorList = new ArrayList<>();
    private List<String> fileDownloadUrlList = new ArrayList<>();
    private int maxRequests = 3;
    /** 准备下载的任务队列 */
    private final Deque<DownloadTask> readyDownTaskDeque = new ArrayDeque<>();
    /** 下载中的任务的队列 */
    private final Deque<DownloadTask> runningDownTaskDeque = new ArrayDeque<>();
    public static final String FILE_TEMP = ".tmp";

    private DownloadManager() {
        mExecutorService = new ThreadPoolExecutor(0,
                                                  Integer.MAX_VALUE,
                                                  60,
                                                  TimeUnit.SECONDS,
                                                  new SynchronousQueue<Runnable>());
    }

    public static DownloadManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final DownloadManager INSTANCE = new DownloadManager();
    }


    /**
     * 入队下载
     *
     * @param info
     */
    public synchronized void enqueneDownTaskByInfo(@NonNull DownInfo info) {
        if (info == null) {
            return;
        }

        //过滤已经加入下载的url
        if (!fileDownloadUrlList.contains(info.getFileUrl())) {
            fileDownloadUrlList.add(info.getFileUrl());

            //生成DownloadTask 对象
            DownloadTask downloadTask = new DownloadTask(info);
            downloadTask.checkDowninfoBreakResume();

            //判断 具有高优先权
            boolean highPriority =
                    DownInfo.FileType.REVERSE_MATERIAL.equals(info.getType()) || DownInfo.FileType.APK
                    .equals(info.getType());

            //入队列
            if (runningDownTaskDeque.size() < maxRequests || highPriority) {
                runningDownTaskDeque.add(downloadTask);
                mExecutorService.execute(downloadTask);
            } else {
                readyDownTaskDeque.add(downloadTask);
            }

        }
    }

    public void onDownloadTaskFinished(DownloadTask downloadTask) {
        synchronized (this) {
            runningDownTaskDeque.remove(downloadTask);
            fileDownloadUrlList.remove(downloadTask.getDownloadInfo()
                                                   .getFileUrl());
            //处理等待下载的任务
            if (runningDownTaskDeque.size() >= maxRequests) {
                return;
            }
            if (readyDownTaskDeque.isEmpty()) {
                return;
            }
            for (Iterator<DownloadTask> i = readyDownTaskDeque.iterator(); i.hasNext(); ) {
                DownloadTask readyDownloadTask = i.next();
                i.remove();
                runningDownTaskDeque.add(readyDownloadTask);
                mExecutorService.execute(readyDownloadTask);
                if (runningDownTaskDeque.size() >= maxRequests) {
                    return;
                }
            }
        }
    }

    public void fileDownError(DownInfo downInfo) {
        if (!fileDownErrorList.contains(downInfo.getFileUrl())) {
            fileDownErrorList.add(downInfo.getFileUrl());
            DataRepository.getInstance().reportAlertInfo(1, downInfo.getFileUrl());
        }
    }

    public void saveDownInfoToDB(DownInfo downInfo) {
        Log.d(TAG,
              String.format("saveDownInfoToDB: %s, %s, %s",
                            downInfo.getFileUrl(),
                            downInfo.getReadSize(),
                            downInfo.getDownloadState()));
        DataRepository.getInstance().saveDownInfoToDB(downInfo);
    }

    public void deleteDownInfoToDB(DownInfo downInfo) {
        fileDownErrorList.remove(downInfo.getFileUrl());
        DataRepository.getInstance().deleteDownInfoToDB(downInfo);
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
        for (DownloadTask downloadTask : runningDownTaskDeque) {
            stopDown(downloadTask);
        }

        for (DownloadTask downloadTask : readyDownTaskDeque) {
            stopDown(downloadTask);
        }
    }

    /**
     * 测试下载一个文件
     */
    public void testDownloadMaterialItem() {
        DownInfo downInfo = new DownInfo();
        downInfo.setFileUrl("");// TODO: 2020-09-27
        downInfo.setFileMd5("");
        downInfo.setContentType("");
        downInfo.setSavePathName("");
        downInfo.setDownLoadingListener(new DownInfo.DownLoadingListener() {
            @Override
            public void onCompleted() {
                Log.d(TAG, "onCompleted: ");
            }

            @Override
            public void onProgress(DownInfo downInfo) {
                Log.d(TAG, "onProgress: ");
            }
        });
        enqueneDownTaskByInfo(downInfo);
    }
}
