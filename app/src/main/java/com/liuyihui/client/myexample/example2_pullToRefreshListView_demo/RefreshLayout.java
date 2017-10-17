package com.liuyihui.client.myexample.example2_pullToRefreshListView_demo;

import android.content.Context;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.AbsListView;
import android.widget.ListView;

import com.liuyihui.client.myexample.R;

/**
 * 扩展SwipeRefreshLayout，添加上拉加载功能
 * Created by liuyh on 2016/12/6.
 */

public class RefreshLayout extends SwipeRefreshLayout implements AbsListView.OnScrollListener {
    private final String TAG = "RefreshLayout";
    // 滑动到最下面时的上拉操作
    private int mTouchSlop;
    // listview实例
    private ListView mListView;
    // 上拉监听器, 到了最底部的上拉加载操作
    private OnLoadListener mOnLoadListener;
    // ListView的加载中footer
    private View mListViewFooter;
    // 按下时的y坐标
    private int mYDown;
    // 抬起时的y坐标, 与mYDown一起用于滑动到底部时判断是上拉还是下拉
    private int mLastY;
    // 是否在加载中 ( 上拉加载更多 )
    private boolean isLoading = false;

    /**
     * 构造方法
     *
     * @param context
     */
    public RefreshLayout(Context context) {
        super(context, null);
    }

    public RefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        mTouchSlop = ViewConfiguration.get(context).getScaledTouchSlop();
        mListViewFooter = LayoutInflater.from(context).inflate(R.layout.listview_footer_pullup_load, null, false);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (mListView == null) {
            getListView();
        }
    }

    /**
     * 获取listview控件
     */
    private void getListView() {
        int childs = getChildCount();
        if (childs > 0) {
            View childView = getChildAt(0);
            if (childView instanceof ListView) {
                mListView = (ListView) childView;
                // 设置滚动监听器给ListView, 使得滚动的情况下也可以自动加载
                mListView.setOnScrollListener(this);
            }
        }
    }

    /**
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN://按下
                mYDown = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE://滑动
                mLastY = (int) ev.getRawY();
                break;
            case MotionEvent.ACTION_UP://抬起
                if (canLoad()) {
                    //加载数据
                    loadData();
                }
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 判断是否可以加载更多。到了底部，listview不在加载中，且为上拉操作
     *
     * @return
     */
    public boolean canLoad() {
        return isBottom() && !isLoading && isPullUp();
    }

    /**
     * 判断是否到listview底部
     *
     * @return
     */
    public boolean isBottom() {
        if (mListView != null && mListView.getAdapter() != null) {
            return mListView.getLastVisiblePosition() == (mListView.getAdapter().getCount() - 1);
        }
        return false;
    }

    /**
     * 判断touch操作是不是上拉操作
     *
     * @return
     */
    public boolean isPullUp() {
        return (mYDown - mLastY) >= mTouchSlop;
    }

    /**
     * 加载
     */
    public void loadData() {
        if (mOnLoadListener != null) {
            setLoading(true);
            mOnLoadListener.onLoad();
        }
    }

    /**
     * 设置加载状态。设置加载视图
     *
     * @param loading
     */
    public void setLoading(boolean loading) {
        isLoading = loading;
        if (isLoading) {
            mListView.addFooterView(mListViewFooter);
        } else {
            mListView.removeFooterView(mListViewFooter);
            mYDown = 0;
            mLastY = 0;
        }
    }

    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {

    }

    @Override
    public void onScroll(AbsListView absListView, int i, int i1, int i2) {
        if (canLoad()) {
            loadData();
        }
    }

    /**
     * 设置加载监听
     *
     * @param OnLoadListener
     */
    public void setOnLoadListener(OnLoadListener OnLoadListener) {
        this.mOnLoadListener = OnLoadListener;
    }

    /**
     * 定义加载监听接口
     */
    public interface OnLoadListener {
        void onLoad();
    }
}
