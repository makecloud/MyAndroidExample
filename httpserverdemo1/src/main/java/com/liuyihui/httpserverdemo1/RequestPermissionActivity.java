package com.liuyihui.httpserverdemo1;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * 不采用框架，获取权限demo
 */
public class RequestPermissionActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    private static final String[] PERMISSIONS = new String[]{Manifest.permission.READ_PHONE_STATE,
                                                             Manifest.permission.ACCESS_NETWORK_STATE,
                                                             Manifest.permission.INTERNET,
                                                             Manifest.permission.RECEIVE_BOOT_COMPLETED,
                                                             Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private static final int PERMISSION_CODES = 1;
    /**
     * 点击触发
     *
     * @param view
     */
    public void requestPermission(View view) {
        List<String> notGrantedPermissions = new ArrayList<>();
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this,
                                                  permission) != PackageManager.PERMISSION_GRANTED) {
                notGrantedPermissions.add(permission);
            }
        }
        if (notGrantedPermissions.size() > 0) {
            requestPermissions(notGrantedPermissions.toArray(new String[notGrantedPermissions.size()]),
                               PERMISSION_CODES);
        }
    }

    private boolean hasAllPermissionsGranted(@NonNull int[] grantResults) {
        for (int grantResult : grantResults) {
            //PERMISSION_GRANTED 授予
            if (grantResult == PackageManager.PERMISSION_DENIED) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODES) {
            if (hasAllPermissionsGranted(grantResults)) {
                //有权限
                Toast.makeText(this, "get", Toast.LENGTH_SHORT)
                     .show();

            } else {
                // 没有获取权限
                Toast.makeText(this, "no get permission", Toast.LENGTH_SHORT)
                     .show();
            }
        }
    }
}
