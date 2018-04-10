package com.liuyihui.exeshell;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import util.ExecuteAsRootBase;
import util.FileUtils;
import util.ReallyShellUtil;
import util.ShellUtils;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resultTextView = findViewById(R.id.checkRootResultText);
    }

    /**
     * 启动vpn 按钮
     *
     * @param view
     */
    public void startOpenvpn(View view) {
        try {
//            Toast.makeText(this, "root权限:" + ReallyShellUtil.canRunRootCommands(), Toast.LENGTH_SHORT).show();

//            String startOOHLinkOpenvpnCmd = "cd /sdcard/oohlink/player/.vpn;export PATH=/data/data/com.oohlink.player/files:$PATH;LD_LIBRARY_PATH=/system/lib busybox nohup /data/data/com.oohlink.player/files/openvpn --config client.conf > /dev/null &";
            String startOOHLinkOpenvpnCmd = " cd /sdcard/oohlink/player/.vpn;export PATH=/data/data/com.oohlink.player/files:$PATH;LD_LIBRARY_PATH=/system/lib busybox nohup /data/data/com.oohlink.player/files/openvpn --config client.conf > /sdcard/vpn.log 2>/sdcard/vpnerr.log &";
//            String startOOHLinkOpenvpnCmd = "cd /mnt/sdcard/oohlink/player/.vpn;busybox nohup /data/data/com.oohlink.player/files/openvpn --config client.conf > /sdcard/vpn.log 2>/sdcard/vpnerr.log &";

            //分开命令试试
            String cmd0 = "cd /mnt/sdcard/oohlink/player/.vpn";
            String cmd1 = "export PATH=/data/data/com.oohlink.player/files:$PATH";
            String cmd2 = "LD_LIBRARY_PATH=/system/lib busybox nohup /data/data/com.oohlink.player/files/openvpn --config client.conf > /sdcard/vpn.log 2>/sdcard/vpnerr.log &";

            //方式1
            /*ArrayList<String> cmds = new ArrayList<>();
            cmds.add(startOOHLinkOpenvpnCmd);
            ReallyShellUtil.execute(cmds);*/

            //方式2
            ShellUtils.CommandResult commandResult = ShellUtils.execCommand(startOOHLinkOpenvpnCmd, true, true);
//            ShellUtils.CommandResult commandResult = ShellUtils.execCommand(new String[]{cmd0, cmd1, cmd2}, true);
            String info = "resultCode:" + commandResult.result +
                    ",successMsg:" + commandResult.successMsg +
                    ",errorMsg:" + commandResult.errorMsg;
            resultTextView.setText(info);

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
            String info = "resultCode:" + commandResult.result +
                    ",successMsg:" + commandResult.successMsg +
                    ",errorMsg:" + commandResult.errorMsg;
            resultTextView.setText(info);
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
        resultTextView.setText(isroot + "");
    }

    /**
     * 检查root权限_su在xbin下 按钮
     *
     * @param view
     */
    public void checkRoot_byXbinSu(View view) {
        boolean isroot = ReallyShellUtil.canRunRootCommands_inXbin();
        resultTextView.setText("" + isroot);
    }

    /**
     * 执行:screencap -p 按钮
     */
    public void invokeScreencap(View view) {
        try {
            ShellUtils.CommandResult commandResult = ShellUtils.execCommand("screencap -p /sdcard/lyh.png", true);
            String info = "resultCode:" + commandResult.result +
                    ",successMsg:" + commandResult.successMsg +
                    ",errorMsg:" + commandResult.errorMsg;
            resultTextView.setText(info);
        } catch (Exception e) {
            e.printStackTrace();
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
            String info = "resultCode:" + commandResult.result +
                    ",successMsg:" + commandResult.successMsg +
                    ",errorMsg:" + commandResult.errorMsg;
            resultTextView.setText(info);
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
            String info = "resultCode:" + commandResult.result +
                    ",successMsg:" + commandResult.successMsg +
                    ",errorMsg:" + commandResult.errorMsg;
            resultTextView.setText(info);

            //写到bin
//            FileUtils.transferInputStreamToFile(this.getResources().openRawResource(R.raw.su));


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
