package com.example.webviewapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.JavascriptInterface;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private WebView webView;
    String execution = "";
    String eventId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = findViewById(R.id.webView);

        String testUrl = "https://itsdev.fiberhome.com/cas-fiberhome";
        WebViewClient webViewClient = new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                Log.d(TAG, "onPageFinished: url:" + url);

                view.loadUrl("javascript:window.local_obj.getExecution(document.getElementsByName(\"execution\")[0].value)");
                view.loadUrl("javascript:window.local_obj.getEventId(document.getElementsByName(\"_eventId\")[0].value)");

                super.onPageFinished(view, url);
            }
        };


        webView.setWebViewClient(webViewClient);
        webView.addJavascriptInterface(new InJavaScriptLocalObj(), "local_obj");

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setLoadWithOverviewMode(true);
        webSettings.setUseWideViewPort(true);
    }

    public void buttonClick(View view) {
        loginCas();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //webView.loadUrl("http:www.163.com");
        //webView.loadUrl("http://101.201.30.129:9005/yunge-website/index.html");

        //office 文档
        /*webView.loadUrl(
                "https://view.officeapps.live.com/op/view.aspx?src=http://test.yungeshidai" +
                        ".com/material/cd64ed028e9ac5883f70c6da4e1b4948.pptx");*/

        //官网
        //webView.loadUrl("http://www.oohlink.com");
        //cas index
        webView.loadUrl("https://itsdev.fiberhome.com/cas-fiberhome");

        //webView.postUrl();
    }

    public void loginCas() {
        String formData = String.format("execution=%s&_eventId=%s", execution, eventId);
        //设置新的webviewClient
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return false;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                Log.d(TAG, "onPageStarted: url:" + url);
                CookieManager cookieManager = CookieManager.getInstance();
                String cookieStr = cookieManager.getCookie(url);
                Log.d(TAG, "onPageStarted: cookie:" + cookieStr);
            }


            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "onPageFinished: url:" + url);
                CookieManager cookieManager = CookieManager.getInstance();
                String cookieStr = cookieManager.getCookie(url);
                Log.d(TAG, "onPageFinished: cookie:" + cookieStr);

            }
        });
        Log.d(TAG, "loginCas: " + formData);
        webView.postUrl("https://itsdev.fiberhome.com/cas-fiberhome/login", Base64.encode(formData.getBytes(), Base64.DEFAULT));

    }

    /**
     * android sdk api >= 17 时需要加@JavascriptInterface
     *
     * @author fei
     */
    final class InJavaScriptLocalObj {
        @JavascriptInterface
        public void getExecution(String html) {
            System.out.println("====>html=" + html);
            execution = html;
        }

        @JavascriptInterface
        public void getEventId(String html) {
            System.out.println("====>html=" + html);
            eventId = html;
        }

    }

}
