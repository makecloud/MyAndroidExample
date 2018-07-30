package com.liuyihui.client.myexample.example16_recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.liuyihui.client.myexample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * recyclerview使用
 * <p>
 * Created by liuyh on 2016/10/31.
 */

public class Example16Activity extends Activity {
    private static final String TAG = "Example1Activity";
    /***/
    private RecyclerView recyclerView;
    /** 需要悬浮的内容的布局视图 */
    private LinearLayout hoverContentLinearLayout;
    /*数据*/
    private List<String> mDatas;

    /**
     * activity创建回调
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example16);
        initData();
        recyclerView = (RecyclerView) findViewById(R.id.recylerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置adapter
        recyclerView.setAdapter(new HomeAdapter());
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
    }

    /**
     * 创建数据集合
     */
    protected void initData() {
        mDatas = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            mDatas.add("" + (char) i);
        }
    }

    /**
     * recyclerView 的适配器
     */
    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {

        /**
         * @param parent
         * @param viewType
         * @return
         */
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            MyViewHolder holder = new MyViewHolder(LayoutInflater.from(Example16Activity.this).inflate(R.layout.recyclerview_item, parent, false));
            return holder;
        }

        /**
         * @param holder
         * @param position
         */
        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mDatas.get(position));
        }

        /**
         * @return
         */
        @Override
        public int getItemCount() {
            return mDatas.size();
        }

        /**
         *
         */
        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView tv;

            public MyViewHolder(View view) {
                super(view);
                tv = (TextView) view.findViewById(R.id.id_num);
            }
        }
    }
}
