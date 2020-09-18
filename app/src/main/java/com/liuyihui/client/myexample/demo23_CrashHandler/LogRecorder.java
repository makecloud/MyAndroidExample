package com.liuyihui.client.myexample.demo23_CrashHandler;

import android.util.Log;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 日志打入文件工具
 *
 * @author liuyi 2017年12月14日18:08:32
 */
public class LogRecorder {
    private final String TAG = "LogRecorder";
    /** 要写入的文件 */
    private File mLogFile;
    /** 限制日志文件的大小 */
    private final int mLimitSize = 20 * 1024 * 1024;//20MB,超过后另写一个文件
    /** 控写日志线程停止 */
    private boolean holdOn = true;
    /** 日志队列 */
    private ConcurrentLinkedQueue<LogContent> queue = new ConcurrentLinkedQueue<LogContent>();
    /** 写日志线程 */
    private Thread recordThread;
    /** 写满的文件数量 */
    private int fullFileCount = 0;

    /**
     * 构造方法
     * <p>
     * 指定要写入的文件夹(绝对路径),日志文件名, 即可
     *
     * @param logFileDir  存放日志文件的文件夹
     * @param logFileName 日志文件名,不带后缀
     */
    public LogRecorder(String logFileDir, String logFileName) {
        try {
            this.mLogFile = createFileIfNotExist(logFileDir + File.separator + logFileName + ".log");
            startWriteLog();
        } catch (IOException e) {
            Log.e(TAG, "", e);
        }
    }

    /**
     * 启动写文件线程
     */
    private void startWriteLog() {
        recordThread = new Thread(new Runnable() {
            @Override
            public void run() {
                LogContent event = null;
                while (holdOn) {
                    event = queue.poll();
                    if (null != event) {
                        writeOneLog(event);
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            Log.e(TAG, e.getMessage(), e);
                        }
                    }
                }
            }
        }, TAG);
        recordThread.start();
    }

    /**
     * 停止写日志线程
     */
    public void stopRecord() {
        holdOn = false;
        if (null != recordThread && recordThread.isAlive()) {
            recordThread.interrupt();
        }
    }

    /**
     * 程序中调用此重载方法记录一条日志
     * <p>
     * 此条日志将会被写入文件
     *
     * @param level
     * @param tag
     * @param msg
     * @param e
     */
    public void log(String level, String tag, String msg, Throwable e) {
        Log.i(tag, msg, e);//输出到控制台
        queue.add(new LogContent(level, tag, msg, e));
    }

    public void log(String tag, String msg) {
        Log.i(tag, msg);//输出到控制台
        queue.add(new LogContent("debug", tag, msg, null));
    }

    public void log(String tag, String msg, Throwable e) {
        Log.i(tag, msg, e);//输出到控制台
        queue.add(new LogContent("debug", tag, msg, e));
    }

    /**
     * 写一行日志到文件,追加
     *
     * @param event
     */
    private void writeOneLog(LogContent event) {
        FileWriter fw = null;

        //处理文件写满的情况
        try {
            if (mLogFile.length() > mLimitSize) {
                mLogFile = createFileIfNotExist(mLogFile.getAbsolutePath() + ++fullFileCount);
            }
        } catch (Exception e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }
        //追加写文件
        try {
            String now = new SimpleDateFormat("yyyy-MM-dd HH:mm:SS").format(new Date());
            fw = new FileWriter(mLogFile, true);
            fw.write(String.format("%s > %s > %s > %s\n", now, event.level, event.tag, event.msg));
            if (null != event.e) {
                event.e.printStackTrace(new PrintWriter(fw));
                fw.write("\n");
            }
            fw.flush();
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
        } finally {
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }
    }

    private File createFileIfNotExist(String absoluteFilePath) throws IOException {
        File file = new File(absoluteFilePath);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

}

class LogContent {
    String level;
    String tag;
    String msg;
    Throwable e;

    LogContent(String level, String tag, String msg, Throwable e) {
        this.level = level;
        this.tag = tag;
        this.msg = msg;
        this.e = e;
    }
}
