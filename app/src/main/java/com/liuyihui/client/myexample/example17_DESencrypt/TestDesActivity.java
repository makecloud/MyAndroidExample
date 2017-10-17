package com.liuyihui.client.myexample.example17_DESencrypt;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.liuyihui.client.myexample.R;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class TestDesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_des);

        Button encryptButton = (Button) findViewById(R.id.encrypt);
        Button decryptButton = (Button) findViewById(R.id.decrypt);
        final String fileName = "loginResult.json";//文件名

        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //加密字符串保存到文件
                try {
                    String test = "{\"balance\":0.0,\"roleName\":\"媒体主\",\"companyName\":\"oohlink-媒体主\",\"status\":1,\"username\":\"mtzywcs1117\",\"id\":103," +
                            "\"loginToken\":\"7468fcad0e40d2465daac1939712d6ec\",\"phone\":\"15801255741\",\"name\":\"范春荣\",\"companyId\":47}\n";
                    String encryptstr = DesUtil.encrypt(test);
                    Serializar.serializeJsonStrToFilename(encryptstr, fileName);
                    System.out.println("加密前的字符：" + test);
                    System.out.println("加密后的字符：" + encryptstr);

                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
            }
        });
        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {

                    //读取文件解密成字符串
                    String encryptedStr = Serializar.loadFileToStr(fileName);
                    String result = DesUtil.decrypt(encryptedStr);
                    Log.i("TestDesActivity", result);

                } catch (InvalidKeySpecException e) {
                    e.printStackTrace();
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (BadPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                } catch (InvalidKeyException e) {
                    e.printStackTrace();
                } catch (NoSuchPaddingException e) {
                    e.printStackTrace();
                } catch (NoSuchProviderException e) {
                    e.printStackTrace();
                } catch (IllegalBlockSizeException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}

