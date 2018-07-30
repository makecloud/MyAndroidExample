package com.wits.serialport;

import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by liuyi on 2018/7/4.
 */

public class AutoOnoffUtil {
    private String TAG = "AutoOnoffUtil";
    private static SerialPort mSerialPort;
    private static OutputStream mOutputStream;
    private static InputStream mInputStream;
    private static ReadThread mReadThread;
    private boolean mFlag;
    private Timer timer;
    long countDown;
    private SetSuccessListener setSuccessListener;

    public interface SetSuccessListener {
        void onSuccess();
    }


    /**
     * 设置定时开机
     *
     * @param times 格式 8:00:00
     * @param listener 结果监听对象
     */
    public void setOnTime(String times, SetSuccessListener listener) {
        this.setSuccessListener = listener;

        //计算现在到明天xx点剩余的秒数
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(times.split(":")[0]));
        calendar.set(Calendar.MINUTE, Integer.parseInt(times.split(":")[1]));
        calendar.set(Calendar.SECOND, Integer.parseInt(times.split(":")[2]));
        countDown = (calendar.getTimeInMillis() - System.currentTimeMillis()) / 1000;
        Log.i(TAG, "computed onTime countDown: " + countDown);

        //串口读写数据流
        try {
            mSerialPort = openSerialPort();
            mOutputStream = mSerialPort.getOutputStream();
            mInputStream = mSerialPort.getInputStream();
            mFlag = true;
            Log.i(TAG, "-----------------mReadThread.start");
        } catch (SecurityException e) {
            Log.e(TAG, "-----------------SecurityException");
        } catch (IOException e) {
            Log.e(TAG, "-----------------IOException");
        } catch (InvalidParameterException e) {
            Log.e(TAG, "-----------------InvalidParameterException");
        }

        //设置开机时间到芯片
        mFlag = true;
        writeOnTimeToMC(1, countDown);
    }

    /**
     * 设置定时关机
     *
     * @param context
     * @param time 格式 15:00:00
     */
    public void setOffTime(Context context, String time) {
        Intent intent = new Intent(context, ShutdownService.class);
        intent.putExtra("shutdownTime", time);
        context.startService(intent);

    }


    //打开串口
    private SerialPort openSerialPort() throws SecurityException, IOException,
            InvalidParameterException {
        // M0,M1,M2默认串口号为ttyS7,M3串口号是ttyS5
        mSerialPort = new SerialPort(new File("/dev/ttyS2"), 9600, 0);
        return mSerialPort;
    }

    //关闭串口
    private void closeSerialPort() {
        Log.i(TAG, "closeSerialPort");
        if (mSerialPort != null) {
            mSerialPort.close();
            mSerialPort = null;
        }
    }

    /**
     * 写入或取消自动开机时间到芯片
     *
     * @param flags 1 为开启自动开机,0为关闭
     * @param times 开机倒计时(单位秒) ,如果要取消自动开机,请将flags,times都设为0
     */
    private void writeOnTimeToMC(int flags, long times) {

        try {
            byte[] mBuffer = longToByteArray(flags, times);
            Log.e(TAG, "writeOnTimeToMC--------time=" + times);
            int i;
            for (i = 0; i < mBuffer.length; i++)
                Log.i(TAG, "BUFFER-----HASHCODE=" + mBuffer[i]);
            //读取写入结果线程
            mReadThread = new ReadThread();
            mReadThread.start();

            if (mOutputStream != null) {
                mOutputStream.write(mBuffer);
                Log.e(TAG, "send data 9 byte to serialport.ok");
            } else {
                Log.e(TAG, "mOutputStream:--------null");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //向单片机发送的数据9个byte
    private static byte[] longToByteArray(int flags, long times) {
        byte[] result = new byte[9];
        result[0] = (byte) 0x00;
        result[1] = (byte) 0xaa;
        result[2] = (byte) 0xff;
        result[3] = (byte) 0x55;
        result[4] = (byte) (flags);
        result[5] = (byte) ((times >> 16) & 0xFF);
        result[6] = (byte) ((times >> 8) & 0xFF);
        result[7] = (byte) (times & 0xFF);
        result[8] = (byte) 0x55;
        return result;
    }


    //读取串口返回数据
    private class ReadThread extends Thread {
        @Override
        public void run() {
            Looper.prepare();
            super.run();
            while (mFlag) {
                int size;
                try {

                    byte[] buffer = new byte[1];
                    if (mInputStream == null) {
                        break;
                    }
                    size = mInputStream.read(buffer);

                    if (size > 0) {
                        for (int i = 0; i < size; i++) {
                            //如果串口干扰返回错误数据,重新向单片机写入开机时间.
                            if (buffer[i] != 0x55) {
                                if (timer == null) {
                                    timer = new Timer();
                                    timer.schedule(new WriteTask(), 2000, 1000);
                                }
                                Log.i(TAG, "Data error app will resent data===============" + +buffer[i]);
                                wait(1000);
                            }
                            //串口返回0X55,说明单片机已经接收到正确开机数据.
                            else if (buffer[i] == 0x55) {
                                Log.i(TAG, " Set boot time ok!" + buffer[i]);
                                size = 0;
                                mFlag = false;
                                if (timer != null) {
                                    timer.cancel();
                                    timer = null;
                                    Log.i(TAG, "timer cancel!");
                                }
                                //关闭串口
                                closeSerialPort();
                                mSerialPort = null;
                                //回调成功接口
                                if (setSuccessListener != null) {
                                    setSuccessListener.onSuccess();
                                }
                            }
                        }
                        // flush_buffer
                        Arrays.fill(buffer, (byte) 0);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    return;
                }
            }
        }
    }

    private class WriteTask extends TimerTask {
        public void run() {
            writeOnTimeToMC(1, countDown);
        }
    }
}
