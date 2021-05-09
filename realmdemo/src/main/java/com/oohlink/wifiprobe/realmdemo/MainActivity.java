package com.oohlink.wifiprobe.realmdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.oohlink.wifiprobe.realmdemo.entity.EmergentLayer;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textview);
    }

    public void addOne(View view) {
        EmergentLayer emergentLayer = new EmergentLayer();
        emergentLayer.setId(3L);
        emergentLayer.setMaterialType(2);
        emergentLayer.setMaterialUrl("url1");
        DatabaseDataSource.getInstance().saveEmergentLayer(emergentLayer);
    }

    public void selectAllDisplay(View view) {
        textView.setText("");
        List<EmergentLayer> emergentLayers = DatabaseDataSource.getInstance().getAllEmergentLayer();
        for (EmergentLayer emergentLayer : emergentLayers) {
            textView.append(emergentLayer.getId() + "," + emergentLayer.getMaterialType() + "," + emergentLayer.getMaterialUrl() + "\n");
        }
    }
}
