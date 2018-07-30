package com.liuyihui.requestpermission;

import android.Manifest;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import kr.co.namee.permissiongen.PermissionFail;
import kr.co.namee.permissiongen.PermissionGen;
import kr.co.namee.permissiongen.PermissionSuccess;

/**
 * android获取权限实践
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE,
                      android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                      Manifest.permission.ACCESS_COARSE_LOCATION);
    }


    /**
     * 以下4个方法，使用PermissionGen 框架，针对android 6.x sdk 获取系统某些权限
     * by liuyihui
     *
     * @param activity 活动实例
     * @param permissions 不定长权限数组
     */
    public void getPermission(Activity activity, String... permissions) {
        PermissionGen.with(activity).addRequestCode(100).permissions(permissions).request();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        PermissionGen.onRequestPermissionsResult(MainActivity.this, requestCode, permissions, grantResults);
    }

    @PermissionSuccess(requestCode = 100)
    public void onSuccess() {
        Toast.makeText(this, "已获取权限", Toast.LENGTH_SHORT).show();
    }

    @PermissionFail(requestCode = 100)
    public void onFail() {
        Toast.makeText(this, "获取权限失败", Toast.LENGTH_SHORT).show();
    }
}
