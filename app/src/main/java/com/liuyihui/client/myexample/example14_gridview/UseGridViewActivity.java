package com.liuyihui.client.myexample.example14_gridview;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.liuyihui.client.myexample.R;

import java.util.ArrayList;
import java.util.List;

public class UseGridViewActivity extends AppCompatActivity {
    private GridView gridView;
    private List<String> dataSet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_grid_view);
        gridView = findViewById(R.id.girdView);
        dataSet = new ArrayList<>();
        dataSet.add("没有");
        dataSet.add("fsld");
        dataSet.add("pik");

        GridViewAdapter adapter = new GridViewAdapter(this, dataSet);
        gridView.setAdapter(adapter);

    }

    class GridViewAdapter extends BaseAdapter {
        private List<String> dataSet;
        private Context context;

        public GridViewAdapter(@NonNull Context context, List<String> dataSet) {
            this.dataSet = dataSet;
            this.context = context;
        }

        @Override
        public int getCount() {
            return dataSet.size();
        }

        @Override
        public Object getItem(int position) {
            return dataSet.get(position);
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.recyclerview_item, parent, false);
            }
            TextView numView = convertView.findViewById(R.id.id_num);
            numView.setText(dataSet.get(position));
            return convertView;
        }
    }
}
