package com.oohlink.liuyihui.qrcode;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.oohlink.liuyihui.qrcode.qrcodebase.activity.CaptureActivity;

public class MainActivity extends AppCompatActivity {
    private final static int SCANNIN_GREQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void startQrcode(View view) {
        Intent intent = new Intent(this, CaptureActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
    }


    /**
     * 处理上一个页面关闭返回的结果
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    String QRcodeContentStr = bundle.getString("result");//得到二维码内容字符串
                    Bitmap qrPicture = (Bitmap) data.getParcelableExtra("bitmap");//得到二维码里的照片
                    Toast.makeText(this, "二维码无效", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

}
