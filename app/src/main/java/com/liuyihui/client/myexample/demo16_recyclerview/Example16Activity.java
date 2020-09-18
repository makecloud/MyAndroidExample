package com.liuyihui.client.myexample.demo16_recyclerview;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;

import com.liuyihui.client.myexample.R;

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
    private HomeAdapter homeAdapter;


    /**
     * activity创建回调
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example16);
        homeAdapter = new HomeAdapter(this);
        recyclerView = findViewById(R.id.recylerview);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);

        //set config
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        //设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);
        //设置adapter
        recyclerView.setAdapter(homeAdapter);
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //scroll listener
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                Log.d(TAG,
//                      "onScrolled: firstVisible position" + linearLayoutManager.findFirstVisibleItemPosition());
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

        });
        //
        MyItemDecoration myItemDecoration = new MyItemDecoration();
        myItemDecoration.setItemGroupInfoFetcher(new MyItemDecoration.ItemGroupInfoFetcher() {
            @Override
            public ItemGroupInfo getItemGroupInfo(int position) {
                return homeAdapter.getItemGroupInfo(position);
            }
        });
        recyclerView.addItemDecoration(myItemDecoration);
        //

    }


}
