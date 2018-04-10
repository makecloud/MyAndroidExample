package util;

import android.text.BoringLayout;
import android.util.Log;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * 到目前为止,真正能检查是否获得root权限的代码
 * <p>
 * Created by liuyi on 2018/1/4.
 */

public class ReallyShellUtil {
    private static String TAG = "ReallyShellUtil";

    /**
     * 检查能否执行需要root权限的命令
     * <p>
     * 原理:看当前用户是不是属于root用户.
     * 拿到当前用户的uid,看uid值是不是root用户的值(测试发现应该uid=0是root用户的uid).
     * 所以方法执行su后,取当前用户的uid,看这个uid
     *
     * @param useXbinSu su是否在/system/xbin下?
     * @return 是否能执行需要root权限的命令
     */
    private static boolean canRunRootCommands(boolean useXbinSu) {
        boolean retval = false;
        Process suProcess;

        try {
            if (useXbinSu) {
                suProcess = Runtime.getRuntime().exec("/system/xbin/su");
            } else {
                suProcess = Runtime.getRuntime().exec("su\n");
            }
            DataOutputStream dos = new DataOutputStream(suProcess.getOutputStream());
            DataInputStream dis = new DataInputStream(suProcess.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));
            if (null != dos && null != dis) {
                // Getting the id of the current user to check if this is root
                dos.writeBytes("id\n");
                dos.flush();


//                String currUid = dis.readLine();
                String currUid = br.readLine();
                boolean exitSu = false;
                if (null == currUid) {
                    retval = false;
                    exitSu = false;
                    Log.d(TAG, "Can't get root access or denied by user");
                } else if (true == currUid.contains("uid=0")) {
                    retval = true;
                    exitSu = true;
                    Log.d(TAG, "Root access granted");
                } else {
                    retval = false;
                    exitSu = true;
                    Log.d(TAG, "Root access rejected: " + currUid);
                }
                if (exitSu) {
                    dos.writeBytes("exit\n");
                    dos.flush();
                }
            }
        } catch (Exception e) {
            // Can't get root !
            // Probably broken pipe exception on trying to write to output stream (dos) after su failed, meaning that the device is not rooted
            retval = false;
            Log.d(TAG, "Root access rejected [" + e.getClass().getName() + "] : " + e.getMessage());
        }
        return retval;
    }

    /**
     * 检查root权限, 适合su在system/bin下时
     *
     * @return
     */
    public static boolean canRunRootCommands() {
        return canRunRootCommands(false);
    }

    /**
     * 检查root权限, 适合su在/system/xbin下时
     *
     * @return
     */
    public static boolean canRunRootCommands_inXbin() {
        return canRunRootCommands(true);
    }

    /**
     * 执行shell命令
     *
     * @param commands 命令列表
     * @return
     */
    public static final boolean execute(ArrayList<String> commands) {
        boolean retval = false;

        try {
//            ArrayList<String> commands = getCommandsToExecute();
            if (null != commands && commands.size() > 0) {
                Process suProcess = Runtime.getRuntime().exec("su");

                DataOutputStream dos = new DataOutputStream(suProcess.getOutputStream());

                // Execute commands that require root access
                for (String currCommand : commands) {
                    dos.writeBytes(currCommand + "\n");
                    dos.flush();
                }

                dos.writeBytes("exit\n");
                dos.flush();

                try {
                    int suProcessRetval = suProcess.waitFor();
                    if (255 != suProcessRetval) {
                        // Root access granted
                        retval = true;
                    } else {
                        // Root access denied
                        retval = false;
                    }
                } catch (Exception ex) {
                    Log.e(TAG, "Error executing root action", ex);
                }
            }
        } catch (IOException ex) {
            Log.w(TAG, "Can't get root access", ex);
        } catch (SecurityException ex) {
            Log.w(TAG, "Can't get root access", ex);
        } catch (Exception ex) {
            Log.w(TAG, "Error executing internal operation", ex);
        }
        return retval;
    }
}

