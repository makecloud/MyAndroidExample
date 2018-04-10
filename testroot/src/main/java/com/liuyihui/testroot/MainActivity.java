package com.liuyihui.testroot;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {
    private TextView isRootTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        isRootTextView = findViewById(R.id.is_root);

        boolean isroot = ReallyShellUtil.canRunRootCommands();

        if (isroot) {
            isRootTextView.setTextColor(Color.GREEN);
            isRootTextView.setText("app可获得root权限!");
            Toast.makeText(this, "设备已被root", Toast.LENGTH_LONG).show();
        } else {
            isRootTextView.setTextColor(Color.RED);
            isRootTextView.setText("app无法获得root权限 --");
        }
    }
}
