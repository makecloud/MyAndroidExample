package com.liuyihui.requestpermission;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * android获取权限实践
 */
public class MainActivity extends AppCompatActivity {
    private TextView msg1;
    private TextView msg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        msg1 = findViewById(R.id.msg1);
        msg2 = findViewById(R.id.msg2);

        checkSdcardDirectoryAccessable();

    }

    /**
     * 向sdcard写文件测试
     *
     * @return
     */
    public boolean checkSdcardDirectoryAccessable() {
        String dir = "- - - ";
        if (isSDCardEnable()) {

            // dir = "/mnt/sdcard/oohlink/player/.offlinePlay";//太平鸟电视无法访问的目
            // dir = "/mnt/sdcard";//太平鸟电视无法访问的目
            // dir = "/mnt/sdcard/com.oohlink.saas.debug/temp";

            dir = Environment.getExternalStorageDirectory().getPath();
            msg2.setText("sd卡启用：" + dir);
        } else {
            dir = getFilesDir().getPath();
            msg2.setText("sd卡未启用fileDir：" + dir);
        }


        File fDir = new File(dir);

        if (!fDir.exists()) {
            msg1.setText("目录不存在");
            return false;
        }

        //write test
        File testFile = new File(dir + "/test.txt");
        try {
            FileOutputStream fos = new FileOutputStream(testFile);
            fos.write(dir.getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            msg1.setText(e.getMessage());
            return false;
        } catch (IOException e) {
            msg1.setText(e.getMessage());
            return false;
        }

        msg1.setText("成功写文件：" + dir);
        return true;

    }

    /**
     * 判断SD卡是否可用
     *
     * @return true : 可用<br>false : 不可用
     */
    public static boolean isSDCardEnable() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    public void getPermission(View view) {
        getPermission(this,
                      android.Manifest.permission.READ_EXTERNAL_STORAGE,
                      android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                      Manifest.permission.ACCESS_COARSE_LOCATION);
    }


    /**
     * 以下4个方法，使用PermissionGen框架，针对android 6.x sdk 获取系统某些权限
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
        Toast.makeText(this, "已获取权限", Toast.LENGTH_SHORT).show();
        checkSdcardDirectoryAccessable();
    }

    @PermissionFail(requestCode = 100)
    public void onFail() {
        Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT).show();
    }
}
