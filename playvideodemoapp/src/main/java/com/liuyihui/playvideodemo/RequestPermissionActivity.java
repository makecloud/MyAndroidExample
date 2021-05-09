package com.liuyihui.playvideodemo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 不采用框架，获取权限demo
 */
public abstract class RequestPermissionActivity extends AppCompatActivity {


    private static final String[] PERMISSIONS = new String[]{Manifest.permission.ACCESS_NETWORK_STATE,
                                                             Manifest.permission.INTERNET,
                                                             Manifest.permission.READ_EXTERNAL_STORAGE,
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
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
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
                Toast.makeText(this, "get", Toast.LENGTH_SHORT).show();

            } else {
                // 没有获取权限
                Toast.makeText(this, "no get permission", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
