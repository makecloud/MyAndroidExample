package com.liuyihui.client.myexample.example13_viewflipper;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.liuyihui.client.myexample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * viewFlipper demo
 * <p>
 * 多个view切换显示
 */
public class UseViewFlipperActivity extends AppCompatActivity {
    private final String TAG = getClass().getSimpleName();
    private ViewFlipper flipper;
    private AdapterViewFlipper adapterViewFlipper;
    private FlipperAdapter flipperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_view_flipper);
        flipper = findViewById(R.id.flipper);

        //        flipper.startFlipping();
        flipper.setAutoStart(true);

        //适配器的flipper
        adapterViewFlipper = findViewById(R.id.adapterViewFlipper);
        flipperAdapter = new FlipperAdapter();
        flipperAdapter.addData("nihao");
        flipperAdapter.addData("liuyuihui");
        flipperAdapter.addData("xiabanhuijia");
        adapterViewFlipper.setAdapter(flipperAdapter);
        adapterViewFlipper.startFlipping();
    }


    /**
     * flipper适配器
     */
    class FlipperAdapter extends BaseAdapter {
        private List<String> dataSet;

        public FlipperAdapter() {
            dataSet = new ArrayList<>();
        }


        @Override
        public int getCount() {
            return dataSet.size();
        }

        @Override
        public String getItem(int position) {
            return dataSet.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }


        public void addData(String data) {
            dataSet.add(data);
            notifyDataSetChanged();
        }


        //生成 flipper 的 item view
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(TAG, "getView: " + (convertView == null));
            if (convertView == null) {
                View contentView = LayoutInflater.from(UseViewFlipperActivity.this)
                                                 .inflate(R.layout.recyclerview_item, parent, true);

                TextView textView = contentView.findViewById(R.id.id_num);
                textView.setText(dataSet.get(position));

                return contentView;
            }

            return convertView;
        }

    }
}
