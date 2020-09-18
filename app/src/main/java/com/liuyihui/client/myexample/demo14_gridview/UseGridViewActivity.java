package com.liuyihui.client.myexample.demo14_gridview;

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

/**
 * 使用gridview做宫格列表
 */
public class UseGridViewActivity extends AppCompatActivity {
    private GridView gridView;
    private GridViewAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_use_grid_view);
        gridView = findViewById(R.id.girdView);


        adapter = new GridViewAdapter<>(this);
        adapter.addData("没有");
        adapter.addData("fsld");
        adapter.addData("kkdie");
        adapter.addData("sdcv");
        adapter.addData("在v哦俄武器");
        adapter.addData("vv额我玩");
        adapter.addData("咯哦i旗袍女a");
        adapter.addData("博文呀");

        gridView.setAdapter(adapter);

    }

    /**
     * for Test
     */
    public void changeItemViewHeight(View view) {
        adapter.changeItemViewHeight();
    }

    class GridViewAdapter<T> extends BaseAdapter {
        private List<T> dataSet;
        private Context context;
        private List<View> itemViewList;

        public GridViewAdapter(@NonNull Context context) {
            dataSet = new ArrayList<>();
            this.context = context;
            itemViewList = new ArrayList<>();
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

        public void addData(T string) {
            dataSet.add(string);
        }

        public void changeItemViewHeight() {
            for (View view : itemViewList) {
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height += 20;
                view.setLayoutParams(layoutParams);
            }
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                convertView = LayoutInflater.from(context)
                                            .inflate(R.layout.layout_gridview_item, null, false);
                itemViewList.add(convertView);
            }
            TextView numView = convertView.findViewById(R.id.id_num);
            numView.setText((String) dataSet.get(position));
            return convertView;
        }
    }
}
