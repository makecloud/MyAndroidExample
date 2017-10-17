package com.liuyihui.client.myexample.example2_pullToRefreshListView_demo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.liuyihui.client.myexample.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 动态加载数据的listView例子
 * 上下拉刷新例子
 * Created by liuyh on 2016/11/1.
 */
public class Example2Activity extends Activity implements AbsListView.OnScrollListener {

    private final String TAG = "Example2Activity";
    private ListView listView;
    private MyAdapter adapter;
    private PullToRefreshListView pullToRefreshListView;

    /**
     * activity创建
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);
        initViewComponent();
        initAdapter();
    }

    /**
     * 初始化控件
     */
    public void initViewComponent() {
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.ptrlv);
        pullToRefreshListView.setGravity(View.TEXT_ALIGNMENT_CENTER);
        setPullToRefreshLable();//设置上下拉提示部分
        pullToRefreshListView.setOnRefreshListener(onRefreshListener2);//下拉刷新控件设置上下拉监听接口对象
        listView = pullToRefreshListView.getRefreshableView();//获取实际的listview
        listView.setOnScrollListener(this);//设置滚动监听
    }

    /**
     * 设置上下拉提示标签
     */
    public void setPullToRefreshLable() {
        //下拉刷新时的弹出布局
        ILoadingLayout topLoadLayout = pullToRefreshListView.getLoadingLayoutProxy(true, false);
        topLoadLayout.setLoadingDrawable(getResources().getDrawable(R.drawable.indicator_bg_top));
        topLoadLayout.setPullLabel("继续下拉刷新");
        topLoadLayout.setRefreshingLabel("正在刷新...");
        topLoadLayout.setReleaseLabel("释放刷新");
        //上拉加载时的弹出布局
        ILoadingLayout bottomLoadLayout = pullToRefreshListView.getLoadingLayoutProxy(false, true);
        bottomLoadLayout.setPullLabel("上拉加载");
        bottomLoadLayout.setRefreshingLabel("玩命加载中...");
        bottomLoadLayout.setReleaseLabel("释放加载");
    }

    /**
     * 初始化适配器
     */
    public void initAdapter() {
        List<Item> items = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Item item = new Item("--计划--" + i, i + "", i + "");
            items.add(item);
        }
        adapter = new MyAdapter(items);
        listView.setAdapter(adapter);
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {
    }

    /**
     * Parameters
     *
     * @param absListView The view whose scroll state is being reported
     * @param i           firstVisibleItem , the index of the first visible cell (ignore if visibleItemCount == 0)
     * @param i1          visibleItemCount,the number of visible cells
     * @param i2          totalItemCount，the number of items in the list adaptor
     */
    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        int lastItemVisiblePosition = absListView.getLastVisiblePosition();
//        Toast.makeText(this, "onScroll", Toast.LENGTH_SHORT).show();
    }


    /**
     * 上下拉监听接口对象
     */
    PullToRefreshBase.OnRefreshListener2 onRefreshListener2 = new PullToRefreshBase.OnRefreshListener2() {
        @Override
        public void onPullDownToRefresh(PullToRefreshBase refreshView) {
            getFristPageData();
        }

        @Override
        public void onPullUpToRefresh(PullToRefreshBase refreshView) {
            loadMoreData();
        }
    };

    /**
     * 加载更多数据，添加到适配器
     * 1、请求网络数据
     * 2、格式化数据
     * 3、添加到适配器
     */
    public void loadMoreData() {
        //模拟请求网络数据，生成10个item对象
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (int i = 0; i < 10; i++) {
                    Item item = new Item("--广告计划--" + i, i + "", i + "");
                    adapter.addItems(item);
                }
                handler.sendEmptyMessage(3);
            }
        }).start();
    }

    public void getFristPageData() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //模拟请求网络数据，生成10个item对象
                adapter.clear();
                for (int i = 0; i < 10; i++) {
                    Item item = new Item("--广告计划--" + i, i + "", i + "");
                    adapter.addItems(item);
                }
                handler.sendEmptyMessage(2);
            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 2://下拉刷新
                    pullToRefreshListView.onRefreshComplete();//结束刷新视图
                    adapter.notifyDataSetChanged();
                    break;
                case 3://上拉加载下一页
                    pullToRefreshListView.onRefreshComplete();//结束刷新视图
                    adapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    };

    /**
     * 重写继承自BaseAdapter的适配器类
     */
    class MyAdapter extends BaseAdapter {

        List<Item> itemList;

        public MyAdapter(List<Item> items) {
            this.itemList = items;
        }

        @Override
        public int getCount() {
            return itemList.size();
        }

        @Override
        public Object getItem(int i) {
            return itemList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            if (view == null) {
                view = getLayoutInflater().inflate(R.layout.example2_listview_item_adv_plan, null);
            }
            //获取item布局中的控件
            TextView tvPlanName = (TextView) view.findViewById(R.id.tv_plan_name);
            TextView tvPlanPeriod = (TextView) view.findViewById(R.id.tv_plan_period);
            TextView tvPlanCost = (TextView) view.findViewById(R.id.tv_plan_cost);

            //list元素数据，填充到控件上
            Item item = itemList.get(i);
            tvPlanName.setText(item.getPlanName());
            tvPlanPeriod.setText(item.getPlanPeriod());
            tvPlanCost.setText(item.getPlanCost());

            return view;
        }

        /**
         * 添加数据列表项
         *
         * @param item
         */
        public void addItems(Item item) {
            itemList.add(item);
        }

        public void clear() {
            itemList.clear();
        }


    }

}
