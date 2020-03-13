package com.liuyihui.mylibrary.util;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;


/**
 * 实现自上而下填充的grid之间等间距的 item decorator
 */
public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
    private final String TAG = getClass().getSimpleName();

    /**
     * 期望间距
     */
    private int expectGridSpace;
    private int span;

    /**
     * @param expectGridSpace
     * @param span            要求是偶数
     */
    public SpacesItemDecoration(int expectGridSpace, int span) {
        this.expectGridSpace = expectGridSpace;
        this.span = span;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {

        int left;
        int right;
        int top;
        int bottom;

        int adapterPosition = parent.getChildAdapterPosition(view);

        GridLayoutManager gridLayoutManager = (GridLayoutManager) parent.getLayoutManager();
        GridLayoutManager.SpanSizeLookup spanSizeLookup = gridLayoutManager.getSpanSizeLookup();


        // spanGroup, 可能代表行
        // spanIndex, view在第几个span，第几列。
        // spanSize, view占用几个span。一个view可以跨越span，占多列。
        int spanGroupIndex = spanSizeLookup.getSpanGroupIndex(adapterPosition, span);
        int spanIndex = spanSizeLookup.getSpanIndex(adapterPosition, span);
        int spanSize = spanSizeLookup.getSpanSize(adapterPosition);
        //        Log.d(TAG,
        //              "getItemOffsets: position" + adapterPosition + " spanGroupIndex:" +
        //              spanGroupIndex);
        //        Log.d(TAG, "getItemOffsets: position" + adapterPosition + " spanIndex:" +
        //        spanIndex);
        //        Log.d(TAG, "getItemOffsets: position" + adapterPosition + " spanSize:" +
        //        spanSize);

        //根据以上概念计算：

        //上下间距算法：最上面一行top为期望space，其余减半。所有行的bottom都减半
        if (spanGroupIndex == 0) {
            top = expectGridSpace;
        } else {
            top = expectGridSpace / 2;
        }
        bottom = expectGridSpace / 2;


        //左右间距算法: 在屏幕最左边的left和贴在屏幕最有边的right不变，贴在一起的left和right各自减半
        if (spanIndex == 0) {
            left = expectGridSpace;
            right = expectGridSpace / 2;
        } else if (spanIndex == (span - 1)) {
            left = expectGridSpace / 2;
            right = expectGridSpace;
        } else {
            left = expectGridSpace / 2;
            right = expectGridSpace / 2;
        }
        //所以，要求space设置为偶数


        outRect.top = top;
        outRect.bottom = bottom;
        outRect.left = left;
        outRect.right = right;

    }
}