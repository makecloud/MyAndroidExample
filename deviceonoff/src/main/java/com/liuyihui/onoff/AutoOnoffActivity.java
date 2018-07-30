/*
 * Copyright 2009 Cedric Priscal
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License. 
 */

package com.liuyihui.onoff;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.oohlink.wifiprobe.deviceonoff.R;

/**
 * 测试顶似乎开关机的demo
 */
public class AutoOnoffActivity extends Activity {


    private String TAG = "SerialPortActivity";
    private Button mbtnSet = null;
    private Button mbtnOff = null;
    private TextView txtMsg = null;
    private EditText etxtTimes = null;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auto_onoff);
        etxtTimes = findViewById(R.id.etxtTimes);
        txtMsg = findViewById(R.id.textView1);
        mbtnSet = findViewById(R.id.btnSet);
        mbtnOff = findViewById(R.id.btnOff);

        mbtnSet.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        mbtnOff.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSetOff = new Intent();
                intentSetOff.setAction("wits.com.simahuan.shutdown");
                sendBroadcast(intentSetOff);
            }
        });


    }

}
