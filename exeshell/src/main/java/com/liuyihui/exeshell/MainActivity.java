package com.liuyihui.exeshell;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import util.ReallyShellUtil;
import util.ShellUtils;
import util.TopActivityUtil;

public class MainActivity extends BaseActivity {
    private final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = findViewById(R.id.checkRootResultText);
        seeTopActivity(null);
    }

    /**
     * 启动vpn 按钮
     *
     * @param view
     */
    public void startOpenVpn(View view) {
        try {
            //            Toast.makeText(this, "root权限:" + ReallyShellUtil.canRunRootCommands(),
            //            Toast.LENGTH_SHORT).show();

            //            String startOOHLinkOpenvpnCmd = "cd /sdcard/oohlink/player/.vpn;export
            //            PATH=/data/data/com.oohlink.player/files:$PATH;
            //            LD_LIBRARY_PATH=/system/lib busybox nohup /data/data/com.oohlink
            //            .player/files/openvpn --config client.conf > /dev/null &";
            String startOOHLinkOpenvpnCmd = " cd /sdcard/oohlink/player/.vpn;export " + "PATH" +
                    "=/data/data/com.oohlink.player/files:$PATH;LD_LIBRARY_PATH=/system/lib " +
                    "busybox nohup /data/data/com.oohlink.player/files/openvpn --config client" + ".conf > /sdcard/vpn.log 2>/sdcard/vpnerr.log &";
            //            String startOOHLinkOpenvpnCmd = "cd /mnt/sdcard/oohlink/player/.vpn;
            //            busybox nohup /data/data/com.oohlink.player/files/openvpn --config
            //            client.conf > /sdcard/vpn.log 2>/sdcard/vpnerr.log &";

            //分开命令试试
            String cmd0 = "cd /mnt/sdcard/oohlink/player/.vpn";
            String cmd1 = "export PATH=/data/data/com.oohlink.player/files:$PATH";
            String cmd2 = "LD_LIBRARY_PATH=/system/lib busybox nohup /data/data/com.oohlink" +
                    ".player/files/openvpn --config client.conf > /sdcard/vpn.log " + "2>/sdcard" + "/vpnerr.log &";

            //方式1
            /*ArrayList<String> cmds = new ArrayList<>();
            cmds.add(startOOHLinkOpenvpnCmd);
            ReallyShellUtil.execute(cmds);*/

            //方式2
            ShellUtils.CommandResult commandResult = ShellUtils.execCommand(startOOHLinkOpenvpnCmd,
                                                                            true,
                                                                            true);
            //            ShellUtils.CommandResult commandResult = ShellUtils.execCommand(new
            //            String[]{cmd0, cmd1, cmd2}, true);
            showCommandResult(commandResult);

        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 执行:reboot 按钮
     *
     * @param view
     */
    public void exeReboot(View view) {
        try {
            ShellUtils.execCommand("reboot", true);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * 执行:whoami 按钮
     *
     * @param view
     */
    public void whoami(View view) {
        try {
            ShellUtils.CommandResult commandResult = ShellUtils.execCommand("busybox whoami", true);
            showCommandResult(commandResult);
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 检查root权限 按钮
     *
     * @param view
     */
    public void checkRoot(View view) {
        boolean isroot = ReallyShellUtil.canRunRootCommands();
        showString(isroot + "");
    }

    /**
     * 检查root权限_su在xbin下 按钮
     *
     * @param view
     */
    public void checkRoot_byXbinSu(View view) {
        boolean isroot = ReallyShellUtil.canRunRootCommands_inXbin();
        showString("" + isroot);
    }

    /**
     * 执行:screencap -p 按钮
     */
    public void invokeScreencap(View view) {
        try {
            ShellUtils.CommandResult commandResult = ShellUtils.execCommand(
                    "screencap -p /sdcard/lyh.png",
                    true);
            showCommandResult(commandResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行:pm uninstall 按钮
     *
     * @param view
     */
    public void pminstall(View view) {
        ShellUtils.CommandResult commandResult = null;
        try {
            commandResult = ShellUtils.execCommand("pm install -r /mnt/internal_sd/alisport" +
                                                           "/player/" + ".apk" +
                                                           "/3FBBFCCF28DBB340500FAAC109B32B9E.apk",
                                                   true);
            showCommandResult(commandResult);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 执行:pm uninstall 按钮
     *
     * @param view
     */
    public void pmuninstall(View view) {
        ShellUtils.CommandResult commandResult = null;
        try {
            commandResult = ShellUtils.execCommand("pm uninstall com.oohlink.player", true);
            showCommandResult(commandResult);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void installSu(View view) {
        ShellUtils.CommandResult commandResult = null;
        try {

            // 让/system可写
            String[] cmds = {"busybox mount -o remount rw /system"};
            commandResult = ShellUtils.execCommand(cmds, false);
            showCommandResult(commandResult);
            //写到bin
            //            FileUtils.transferInputStreamToFile(this.getResources().openRawResource
            //            (R.raw.su));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void seeTopActivity(View view) {
        //use runtime
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String topActivityInfo = TopActivityUtil.getTopActivityInfo(MainActivity.this);
                    //showString(topActivityInfo);
                    System.out.println(topActivityInfo);
                    try {
                        Thread.sleep(1500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();


        //use shell
        /*new Thread(new Runnable() {
            @Override
            public void run() {
                ShellUtils.CommandResult commandResult;
                while (true) {
                    try {
                        commandResult = ShellUtils.execCommand("dumpsys activity |grep
                        mFocusedActivity", true);
                        System.out.println(commandResult.successMsg);//com.oohlink.player/
                        .MainActivity
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
        }).start();*/
    }

    public void rdateTime(View view) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final ShellUtils.CommandResult result = ShellUtils.execCommand(
                            "busybox rdate -s 193.228.143.24",
                            true);

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            showCommandResult(result);
                        }
                    });
                } catch (Exception e) {
                    Log.e(TAG, "rdateTime: ", e);
                }
            }
        }).start();

    }

}
