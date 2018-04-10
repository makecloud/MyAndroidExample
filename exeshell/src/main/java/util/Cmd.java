package util;

import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;


/**
 * java程序执行命令工具
 *
 * @author liuyi 2018年1月2日10:34:14
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

    /**
     * 按有root权限执行命令
     *
     * @param ctx
     * @param cmd
     * @return
     */
    public static boolean runAsRoot(Context ctx, String cmd) {
        try {
            boolean isRoot = ShellUtils.checkRootPermission();
            Log.i(TAG, "RUN : " + cmd + " Permission : " + isRoot);
            ShellUtils.CommandResult result = ShellUtils.execCommand(cmd, isRoot);

            if (result.result == 0) {
                return true;
            } else {
                Log.e(TAG, "error Msg: " + result.errorMsg);
                Toast.makeText(ctx, "error Msg: " + result.errorMsg, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Log.e(TAG, "执行命令失败", e);
            Toast.makeText(ctx, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    /**
     * 重新挂载/system
     *
     * @param ctx
     */
    public static void remountSystem(Context ctx) {
        runAsRoot(ctx, "mount -o rw,remount /system");
    }

    /**
     * 重新挂在/sdcard
     *
     * @param ctx
     */
    public static void remountSdcard(Context ctx) {
        //获得sdcard挂载目录
        String sdcard = "/mnt/sdcard";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            sdcard = Environment.getExternalStorageDirectory().toString();
        }
        runAsRoot(ctx, "mount -o rw,remount " + sdcard);
    }
}
