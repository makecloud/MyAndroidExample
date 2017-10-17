package util;

import android.os.Environment;
import android.util.Log;


/**
 * Created by bulreed on 6/30/16.
 */
public class Cmd {

    private static String TAG = "myexample.Cmd";

    private Cmd() {
    }

    /*public static boolean runShellRoot(String cmd) {
        DataOutputStream out = null;
        try {
            Log.i(TAG, "Run : " + cmd);
            Process p = Runtime.getRuntime().exec("su");
            out = new DataOutputStream(p.getOutputStream());
            out.write((cmd + "\n").getBytes());
            out.flush();

            WatchThread wt = new WatchThread(p);
            wt.start();
            int status = p.waitFor();
            wt.setOver(true);
            if (status == 0) {
                Log.i(TAG, "OK : " + p.exitValue());
                return true;
            }

            Log.w(TAG, "Fail : " + p.exitValue());
            return false;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }*/

    public static boolean runAsRoot(String cmd) {
        try {
            boolean isRoot = ShellUtils.checkRootPermission();
            Log.i(TAG, "RUN : " + cmd + " Permission : " + isRoot);
            ShellUtils.CommandResult result = ShellUtils.execCommand(cmd, isRoot);

            if (result.result == 0) {
                return true;
            } else {
                Log.e(TAG, "error Msg: " + result.errorMsg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void remountSystem() {
        runAsRoot("mount -o rw,remount /system");
    }

    public static void remountSdcard() {
        String sdcard = getSdcardMountPoint();
        runAsRoot("mount -o rw,remount " + sdcard);
    }

    public static String getSdcardMountPoint() {
        String sdcard = "/mnt/sdcard";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sdcard = Environment.getExternalStorageDirectory().toString();
        }
        return sdcard;
    }

}
