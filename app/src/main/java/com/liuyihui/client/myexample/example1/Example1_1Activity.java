package com.liuyihui.client.myexample.example1;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.ILoadingLayout;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.liuyihui.client.myexample.R;
import com.liuyihui.client.myexample.example2_pullToRefreshListView_demo.Item;

import java.util.ArrayList;
import java.util.List;

/**
 * listView上滑固定悬浮框实例
 * <p>
 * Created by liuyh on 2016/10/31.
 */

public class Example1_1Activity extends AppCompatActivity {
    private static final String TAG = "Example1Activity";
    /*控件*/
    private PullToRefreshListView pullToRefreshListView;
    private ListView listView;
    private MyAdapter adapter;
    private View header1View;//listView的两个header
    private View header2View;//listView的两个header
    private LinearLayout header2Container;//容器
    private LinearLayout hoverContainer;//容器
    private LinearLayout hoverContentLinearLayout;//* 需要悬浮的内容的布局视图
    private ActionBar mActionBar;//动作栏
    Drawable actionBarBg;
    /*数据*/
    private int scrollY;
    int[] location = new int[2];
    private int header1px;
    private int actionBarBackgroundAlpha;//标题栏背景透明值：0-255

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example1);

        //使用自定义的布局的ActionBar
        mActionBar = getSupportActionBar();
        mActionBar.setElevation(0);
        mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        mActionBar.setCustomView(R.layout.my_actionbar2);
        actionBarBg = getResources().getDrawable(R.color.yunge_H_cyan);
//        mActionBar.getCustomView().setAlpha(0);
        actionBarBg.setAlpha(0);//首次设置为透明
        mActionBar.setBackgroundDrawable(actionBarBg);


        //初始化控件
        initViewComponent();
        initAdapter();
        //给listView添加图片header1
        listView.addHeaderView(header1View);
        //给listView添加header2
        listView.addHeaderView(header2View);

        //滚动事件监听器
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {
            }

            /**
             * @param absListView
             * @param i 当前屏显示的第一个item，在整个item数组中的下标（从0开始）
             *          使用pulltorefresh时，列表滑到最上面，i为0.列表第一个header开始只露一部分（即开始隐藏一部分）时，i为1。
             *          第一个header刚好完全隐藏，此时i也为1.
             *          第二个header开始隐藏一部分时，i为2
             * @param i1
             * @param i2
             */
            @Override
            public void onScroll(AbsListView absListView, int i, int i1, int i2) {
//                Log.i(TAG, "当前屏最上显示的是列表第" + i + "个item");
                /*计算标题栏背景drawable的alpha值start*/
                int actionBarHeight = mActionBar.getCustomView().getMeasuredHeight();//标题栏高度像素
                View firstHeaderView = absListView.getChildAt(0);//获取列表第一个头
                if (firstHeaderView != null) {
                    if (i > 1) {//第二个header开始隐藏一部分
                        actionBarBackgroundAlpha = 255;
                    } else {
                        scrollY = firstHeaderView.getTop();//作为列表向上滚动的像素。活得向上滚动的像素数量
                        if (-scrollY < 300) {//向上滚动300像素内不改变alpha值
                            actionBarBackgroundAlpha = 0;
                        } else {
                            int header1Height = firstHeaderView.getMeasuredHeight();//列表第一个头视图的高度
                            double scale = (double) scrollY / -(header1Height - actionBarHeight);//列表第一个头视图滑动的比例
                            if (scale > 1.0) {
                                scale = 1.0;
                            }
                            actionBarBackgroundAlpha = (int) (scale * 255);
//                            Log.i(TAG, "" + scrollY / header1Height);
//                            System.out.println("scrollY：" + scrollY + ",scale：" + scale + ". 测量高度" + header1Height + ". alpha:" + scale * 255);
                        }
                    }
                }
                /*计算标题栏背景drawable的alpha值end*/
                handleActionBarBackgroundAlpha(actionBarBg, actionBarBackgroundAlpha);//设置alpha值

                /*控制下拉条动态位置*/

                if (firstHeaderView != null && firstHeaderView == header1View) {
                    scrollY = firstHeaderView.getTop();//作为列表向上滚动的像素。活得向上滚动的像素数量
                    int header1Height = firstHeaderView.getMeasuredHeight();//列表第一个头视图的高度
                    if (-scrollY > header1Height - mActionBar.getCustomView().getMeasuredHeight()) {
//                        if (i > 1) {//第2个元素完全隐藏
                        //header2内视图转移到悬浮容器
                        if (hoverContentLinearLayout.getParent() == header2Container) {
                            header2Container.removeView(hoverContentLinearLayout);
                            hoverContainer.addView(hoverContentLinearLayout);
                        }
                    } else {//第2个元素开始显示
                        //悬浮容器内视图转移到header2内
                        if (hoverContentLinearLayout != null && hoverContentLinearLayout.getParent() == hoverContainer) {
                            hoverContainer.removeView(hoverContentLinearLayout);
                            //将悬浮内容布局视图从父容器移
                            header2Container.addView(hoverContentLinearLayout);
                        }
                    }
                }
            }

        });


    }

    public void handleActionBarBackgroundAlpha(Drawable actionBarBackgroundDrawable, int alpha) {
        actionBarBg.setAlpha(alpha);
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        header1px = dip2px(Example1_1Activity.this, 270f);
        return super.onCreateView(parent, name, context, attrs);

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

    /**
     * 初始化控件
     */
    public void initViewComponent() {
        //获取下拉刷新列表控件
        pullToRefreshListView = (PullToRefreshListView) findViewById(R.id.ptrlv);
        //设置模式。both：即可下拉刷新，也可上拉刷新
        pullToRefreshListView.setMode(PullToRefreshBase.Mode.BOTH);
        setPullToRefreshLable();
//                pullToRefreshListView.setOnRefreshListener();
        //        pullToRefreshListView.setOnPullEventListener();
        pullToRefreshListView.setOnPullEventListener(new PullToRefreshBase.OnPullEventListener<ListView>() {
            @Override
            public void onPullEvent(PullToRefreshBase<ListView> refreshView, PullToRefreshBase.State state, PullToRefreshBase.Mode direction) {
                //操作栏的隐藏和显示控制
                if (direction == PullToRefreshBase.Mode.PULL_FROM_START) {
                    mActionBar.hide();
                    if (state == PullToRefreshBase.State.RESET) {
                        mActionBar.show();
                    }
                }
            }
        });

        //获取listview
        listView = pullToRefreshListView.getRefreshableView();
        //初始化两个header视图
        header1View = getLayoutInflater().inflate(R.layout.example1_listview_header1, null);
        header2View = getLayoutInflater().inflate(R.layout.example1_listview_header2, null);
        //需要悬浮的内容的视图
        /*hoverContentLinearLayout = (LinearLayout) getLayoutInflater().inflate(R.layout
                .example1_hover_content_layout, null).findViewById(R.id.ll_hover_content);*/
        hoverContentLinearLayout = (LinearLayout) header2View.findViewById(R.id.ll_hover_content);
        //初始化两个容器视图
        header2Container = (LinearLayout) header2View.findViewById(R.id.ll_header2_container);
        hoverContainer = (LinearLayout) findViewById(R.id.ll_hover_container);
    }

    /**
     * 设置上下拉提示标签
     */
    public void setPullToRefreshLable() {
        //下拉刷新时的弹出布局
        ILoadingLayout topLoadLayout = pullToRefreshListView.getLoadingLayoutProxy(true, false);
        topLoadLayout.setLoadingDrawable(null);
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
            Button btnApplyUnpublish = (Button) view.findViewById(R.id.btn_apply_unpublish);


            //list元素数据，填充到控件上
            final Item item = itemList.get(i);
            tvPlanName.setText(item.getPlanName());
            tvPlanPeriod.setText(item.getPlanPeriod());
            tvPlanCost.setText(item.getPlanCost());
            btnApplyUnpublish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showWindow(item.getPlanCost(), item.getPlanName());
                }
            });

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

    public void showWindow(String data1, String data2) {
        View v = getLayoutInflater().inflate(R.layout.popup_window_1, null);
        PopupWindow popupWindow = new PopupWindow();
        //popupWindow设置
        popupWindow.setContentView(v);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setBackgroundDrawable(new ColorDrawable(0x00000000));//不设置背景透明，点外面popupWindow也不消失
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setAnimationStyle(R.style.AnimationBottomFade);
        //popupWidow内视图设置
        Button popupBtn1 = (Button) popupWindow.getContentView().findViewById(R.id.btn_popup_button_1);
        popupBtn1.setText(data1);
        Button popupBtn2 = (Button) popupWindow.getContentView().findViewById(R.id.btn_popup_button_2);
        popupBtn2.setText(data2);

//        popupWindow.showAsDropDown(clickBtn, 5, 5);
        popupWindow.showAtLocation(listView, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
