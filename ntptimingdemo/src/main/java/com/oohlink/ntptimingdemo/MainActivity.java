package com.oohlink.ntptimingdemo;

import android.Manifest;
import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

public class MainActivity extends AppCompatActivity {
    private static String tag = "MainActivity";
    private static Timer mCycleTimer;
    private static TimerTask mCycleTask;
    private static String mNtpServer = "s1a.time.edu.cn";
    private static int MSG_NTP_SEARCH_FAILED = 1;
    private static int MSG_NTP_SEARCH_OK = 2;
    private static SntpClient mNtpClient;
    /**
     * NTP获取时间失败时,每隔30s周期性重新获取，直至成功，成功后恢复正常计时
     */
    private final static int CYCLE_TIME_ERROR = 20000;
    private final static String DATETIME_SNTP_SERVER = "s1a.time.edu.cn";
    private final static String DATETIME_NTP_RESYNC_TIME = "1";
    private static boolean isTurnToSuccess = false;
    private static boolean isInErrorCycle = false;

    private TextView timeTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timeTextView = findViewById(R.id.time);

        if (Build.VERSION.SDK_INT >= 23) {//sdk23以上申请权限
            getPermission(this, Manifest.permission.SET_TIME, Manifest.permission.SET_TIME_ZONE);
        }
    }

    public void start(View view) {
        Toast.makeText(this, "start", Toast.LENGTH_SHORT).show();
        startCalibrateTime(DATETIME_SNTP_SERVER, 1 * 20 * 1000);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    final String timestr = new SimpleDateFormat("hh:mm:ss.SSS").format(new Date());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            timeTextView.setText(timestr);
                        }
                    });
                    try {
                        Thread.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        t.setDaemon(false);
        t.start();
    }

    static class MyHandler extends Handler {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (msg.what == MSG_NTP_SEARCH_FAILED) {
                String mhostAddress = DATETIME_SNTP_SERVER;
                if (TextUtils.isEmpty(mhostAddress)) {
                    mhostAddress = mNtpServer;
                }
                startCalibrateTime(mhostAddress, CYCLE_TIME_ERROR);
            } else if (msg.what == MSG_NTP_SEARCH_OK) {
                String mhostAddress = DATETIME_SNTP_SERVER;
                //项目中保存的ntp服务器地址
                if (TextUtils.isEmpty(mhostAddress)) {
                    mhostAddress = mNtpServer;
                }
                String timeStr = DATETIME_NTP_RESYNC_TIME;
                //项目中设置的系统默认隔多久同步时间
                int time = 168 * 60 * 60 * 1000;
                if (!TextUtils.isEmpty(timeStr) && TextUtils.isDigitsOnly(timeStr)) {
                    time = Integer.parseInt(timeStr) * 20 * 1000;
                }
                startCalibrateTime(mhostAddress, time);
            }
        }
    }

    private static Handler mHandler = new MyHandler();


    /**
     * 校准时间
     * 从ntp服务器中获取时间
     *
     * @param ntpHost ntp服务器域名地址
     * @return 如果失败返回-1，否则返回当前的毫秒数
     */
    public static void startCalibrateTime(final String mhostAddress, final int cycleTime) {
        Log.d(tag, "startCalibrateTime()");
        if (mCycleTimer != null) {
            mCycleTask.cancel();
            mCycleTimer.cancel();
            mCycleTimer.purge();
            mCycleTask = null;
            mCycleTimer = null;
        }
        mCycleTask = new TimerTask() {

            @Override
            public void run() {
                Log.d(tag, "run()");
                long time = getTimeFromNtpServer(mhostAddress);//从获取ntp服务器上获取时间
                if (time == -1) {
                    Log.e(tag, "async time failed.");
                } else {
                    SystemClock.setCurrentTimeMillis(time);//设置系统时间
                }
                if (isTurnToSuccess) {
                    isTurnToSuccess = false;
                    mHandler.sendEmptyMessage(MSG_NTP_SEARCH_OK);
                }
            }

            @Override
            public boolean cancel() {
                Log.d(tag, "cancel()");
                return super.cancel();
            }
        };
        //        mCycleTimer = new NgnTimer();
        mCycleTimer = new Timer();
        mCycleTimer.schedule(mCycleTask, 0, cycleTime);
        Log.d(tag, "start ntp timer  time:" + cycleTime / 1000);
    }

    public static void stopCalibrateTimer() {
        if (mCycleTimer != null) {
            mCycleTask.cancel();
            mCycleTimer.cancel();
            mCycleTimer.purge();
            mCycleTask = null;
            mCycleTimer = null;
        }
    }

    public static long getTimeFromNtpServer(String hostAddress) {
        Log.d(tag, "getTimeFromNtpServer()");
        if (TextUtils.isEmpty(hostAddress)) {
            Log.e(tag, "Ntp host is null.");
            return -1;
        }
        if (mNtpClient == null) {
            mNtpClient = new SntpClient();
        }
        boolean isSuccessful = mNtpClient.requestTime(hostAddress, 20000);
        Log.d(tag, "requestTime:" + isSuccessful);
        if (isSuccessful) {
            long now = mNtpClient.getNtpTime();//now就是获取的时间
            if (isInErrorCycle) {
                if (!isTurnToSuccess) {
                    isTurnToSuccess = true;
                }
                isInErrorCycle = false;
            }
            return now;
        } else {
            if (!isInErrorCycle) {
                isInErrorCycle = true;
                isTurnToSuccess = false;
                mHandler.sendEmptyMessage(MSG_NTP_SEARCH_FAILED);
            }

        }
        return -1;
    }

    public static class SntpClient {
        private static final String TAG = "SntpClient";

        private static final int REFERENCE_TIME_OFFSET = 16;
        private static final int ORIGINATE_TIME_OFFSET = 24;
        private static final int RECEIVE_TIME_OFFSET = 32;
        private static final int TRANSMIT_TIME_OFFSET = 40;
        private static final int NTP_PACKET_SIZE = 48;

        private static final int NTP_PORT = 123;
        private static final int NTP_MODE_CLIENT = 3;
        private static final int NTP_VERSION = 3;

        // Number of seconds between Jan 1, 1900 and Jan 1, 1970
        // 70 years plus 17 leap days
        private static final long OFFSET_1900_TO_1970 = ((365L * 70L) + 17L) * 24L * 60L * 60L;

        // system time computed from NTP server response
        private long mNtpTime;

        // value of SystemClock.elapsedRealtime() corresponding to mNtpTime
        private long mNtpTimeReference;

        // round trip time in milliseconds
        private long mRoundTripTime;

        /**
         * Sends an SNTP request to the given host and processes the response.
         *
         * @param host    host name of the server.
         * @param timeout network timeout in milliseconds.
         * @return true if the transaction was successful.
         */
        public boolean requestTime(String host, int timeout) {
            DatagramSocket socket = null;
            try {
                socket = new DatagramSocket();
                socket.setSoTimeout(timeout);
                InetAddress address = InetAddress.getByName(host);
                byte[] buffer = new byte[NTP_PACKET_SIZE];
                DatagramPacket request = new DatagramPacket(buffer,
                                                            buffer.length,
                                                            address,
                                                            NTP_PORT);

                // set mode = 3 (client) and version = 3
                // mode is in low 3 bits of first byte
                // version is in bits 3-5 of first byte
                buffer[0] = NTP_MODE_CLIENT | (NTP_VERSION << 3);

                // get current time and write it to the request packet
                long requestTime = System.currentTimeMillis();
                Log.d(TAG, "RequestTime:" + new Date(requestTime));
                long requestTicks = SystemClock.elapsedRealtime();
                writeTimeStamp(buffer, TRANSMIT_TIME_OFFSET, requestTime);

                socket.send(request);

                // read the response
                DatagramPacket response = new DatagramPacket(buffer, buffer.length);
                socket.receive(response);
                long responseTicks = SystemClock.elapsedRealtime();
                long responseTime = requestTime + (responseTicks - requestTicks);

                // extract the results
                long originateTime = readTimeStamp(buffer, ORIGINATE_TIME_OFFSET);
                long receiveTime = readTimeStamp(buffer, RECEIVE_TIME_OFFSET);
                long transmitTime = readTimeStamp(buffer, TRANSMIT_TIME_OFFSET);
                long roundTripTime = responseTicks - requestTicks - (transmitTime - receiveTime);
                // receiveTime = originateTime + transit + skew
                // responseTime = transmitTime + transit - skew
                // clockOffset = ((receiveTime - originateTime) + (transmitTime
                // - responseTime))/2
                // = ((originateTime + transit + skew - originateTime) +
                // (transmitTime - (transmitTime + transit - skew)))/2
                // = ((transit + skew) + (transmitTime - transmitTime - transit
                // + skew))/2
                // = (transit + skew - transit + skew)/2
                // = (2 * skew)/2 = skew
                long clockOffset =
                        ((receiveTime - requestTime) + (transmitTime - System.currentTimeMillis())) / 2;
                // if (false) Log.d(TAG, "round trip: " + roundTripTime +
                // " ms");
                // if (false) Log.d(TAG, "clock offset: " + clockOffset +
                // " ms");

                // save our results - use the times on this side of the network
                // latency
                // (response rather than request time)
                mNtpTime = System.currentTimeMillis() + clockOffset;
                //              mNtpTime = transmitTime;
                mNtpTimeReference = responseTicks;
                mRoundTripTime = roundTripTime;
            } catch (Exception e) {
                if (false)
                    Log.d(TAG, "request time failed:" + e);
                e.printStackTrace();
                return false;
            } finally {
                if (socket != null) {
                    socket.close();
                }
            }

            return true;
        }

        /**
         * Returns the time computed from the NTP transaction.
         *
         * @return time value computed from NTP server response.
         */
        public long getNtpTime() {
            return mNtpTime;
        }

        /**
         * Returns the reference clock value (value of
         * SystemClock.elapsedRealtime()) corresponding to the NTP time.
         *
         * @return reference clock corresponding to the NTP time.
         */
        public long getNtpTimeReference() {
            return mNtpTimeReference;
        }

        /**
         * Returns the round trip time of the NTP transaction
         *
         * @return round trip time in milliseconds.
         */
        public long getRoundTripTime() {
            return mRoundTripTime;
        }

        /**
         * Reads an unsigned 32 bit big endian number from the given offset in
         * the buffer.
         */
        private long read32(byte[] buffer, int offset) {
            byte b0 = buffer[offset];
            byte b1 = buffer[offset + 1];
            byte b2 = buffer[offset + 2];
            byte b3 = buffer[offset + 3];

            // convert signed bytes to unsigned values
            int i0 = ((b0 & 0x80) == 0x80 ? (b0 & 0x7F) + 0x80 : b0);
            int i1 = ((b1 & 0x80) == 0x80 ? (b1 & 0x7F) + 0x80 : b1);
            int i2 = ((b2 & 0x80) == 0x80 ? (b2 & 0x7F) + 0x80 : b2);
            int i3 = ((b3 & 0x80) == 0x80 ? (b3 & 0x7F) + 0x80 : b3);

            return ((long) i0 << 24) + ((long) i1 << 16) + ((long) i2 << 8) + (long) i3;
        }

        /**
         * Reads the NTP time stamp at the given offset in the buffer and
         * returns it as a system time (milliseconds since January 1, 1970).
         */
        private long readTimeStamp(byte[] buffer, int offset) {
            long seconds = read32(buffer, offset);
            long fraction = read32(buffer, offset + 4);
            return ((seconds - OFFSET_1900_TO_1970) * 1000) + ((fraction * 1000L) / 0x100000000L);
        }

        /**
         * Writes system time (milliseconds since January 1, 1970) as an NTP
         * time stamp at the given offset in the buffer.
         */
        private void writeTimeStamp(byte[] buffer, int offset, long time) {
            long seconds = time / 1000L;
            long milliseconds = time - seconds * 1000L;
            seconds += OFFSET_1900_TO_1970;

            // write seconds in big endian format
            buffer[offset++] = (byte) (seconds >> 24);
            buffer[offset++] = (byte) (seconds >> 16);
            buffer[offset++] = (byte) (seconds >> 8);
            buffer[offset++] = (byte) (seconds >> 0);

            long fraction = milliseconds * 0x100000000L / 1000L;
            // write fraction in big endian format
            buffer[offset++] = (byte) (fraction >> 24);
            buffer[offset++] = (byte) (fraction >> 16);
            buffer[offset++] = (byte) (fraction >> 8);
            // low order bits should be random data
            buffer[offset++] = (byte) (Math.random() * 255.0);
        }
    }


    /**
     * 以下4个方法，使用PermissionGen 框架，针对android 6.x sdk 获取系统某些权限
     * by liuyihui
     *
     * @param activity    活动实例
     * @param permissions 不定长权限数组
     */
    public void getPermission(Activity activity, String... permissions) {
        PermissionGen.with(activity).addRequestCode(100).permissions(permissions).request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(MainActivity.this,
                                                 requestCode,
                                                 permissions,
                                                 grantResults);

    }

    @PermissionSuccess(requestCode = 100)
    public void onSuccess() {
        //ToastUtil.toast("已获取权限");
    }

    @PermissionFail(requestCode = 100)
    public void onFail() {
        Toast.makeText(this, "获取设备读写权限失败", Toast.LENGTH_SHORT).show();
    }


}
