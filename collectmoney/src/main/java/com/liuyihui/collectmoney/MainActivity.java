package com.liuyihui.collectmoney;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.TextureView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.liuyihui.collectmoney.zxing.activity.CaptureActivity;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    private final static String TAG = "MainActivity";
    private final static int SCANNIN_GREQUEST_CODE = 1;
    Button scanButton;
    EditText ipEt;
    EditText totalAmountEt;
    OkHttpClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        scanButton = findViewById(R.id.scan_button);
        ipEt = findViewById(R.id.server_ip);
        totalAmountEt = findViewById(R.id.totalAmount);
        client = new OkHttpClient();

        scanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, CaptureActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivityForResult(intent, SCANNIN_GREQUEST_CODE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String authCode;
        final String host = "";
        switch (requestCode) {
            case SCANNIN_GREQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    Bundle bundle = data.getExtras();
                    authCode = bundle.getString("result");
                }
                break;
        }
        final String outTradeNo = "liuyihuiOrder" + System.currentTimeMillis();
        final String totalAmount = "0.01";
        final String discountableAmount = "0";
        final String undiscountableAmount = "0.01";
        final String timeoutExpress = "2m";
        final String subject = "测试";
        final String body = "测试啊";
        final String operatorId = "liuyihuiaa";
        final String storeId = "lyhstore1";

        new Thread(new Runnable() {
            @Override
            public void run() {
                //请求url
                try {
                    String url = "http://192.168.0.117:8080/OOHLinkPay/zhifubao/tradePay";

                    MultipartBody multipartBody = new MultipartBody.Builder()
                            .addFormDataPart("outTradeNo ", outTradeNo)
                            .addFormDataPart("totalAmount ", totalAmount)
                            .addFormDataPart("discountableAmount ", discountableAmount)
                            .addFormDataPart("undiscountableAmount ", undiscountableAmount)
                            .addFormDataPart("timeoutExpress ", timeoutExpress)
                            .addFormDataPart("subject ", subject)
                            .addFormDataPart("body ", body)
                            .addFormDataPart("sellerId", "2018030802336539")
                            .addFormDataPart("operatorId ", operatorId)
                            .addFormDataPart("storeId ", storeId)
                            .build();

                    Request request = new Request.Builder().url(url).post(multipartBody).build();
                    Response response = client.newCall(request).execute();
                    String responseBody = response.body().string();
                    Log.i(TAG, responseBody);
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
            }
        }).start();
    }
}
