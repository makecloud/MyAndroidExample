package com.example.webviewapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);

        String testUrl = "http://www.baidu.com";
        WebViewClient webViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }
        };

        webView.setWebViewClient(webViewClient);
    }

    public void buttonClick(View view) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        //        webView.loadUrl("http:www.163.com");
//        webView.loadUrl("http://101.201.30.129:9005/yunge-website/index.html");

        //office 文档
        webView.loadUrl(
                "https://view.officeapps.live.com/op/view.aspx?src=http://test.yungeshidai" +
                        ".com/material/cd64ed028e9ac5883f70c6da4e1b4948.pptx");
    }
}
