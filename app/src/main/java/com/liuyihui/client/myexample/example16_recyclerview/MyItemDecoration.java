package com.liuyihui.client.myexample.example16_recyclerview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 实现顶部sticky效果标题的decorator
 */
public class MyItemDecoration extends RecyclerView.ItemDecoration {
    private final String TAG = getClass().getSimpleName();
    /**
     * 组名部分占的高度
     */
    private int mHeaderHeight = 70;
    private Paint paint, textPaint;
    private Paint.FontMetrics fontMetrics;

    interface ItemGroupInfoFetcher {
        ItemGroupInfo getItemGroupInfo(int position);
    }

    private ItemGroupInfoFetcher itemGroupInfoFetcher;


    public MyItemDecoration() {
        paint = new Paint();
        textPaint = new Paint();
        textPaint.setTextSize(48);
        textPaint.setColor(Color.BLACK);
        textPaint.setStrokeWidth(5);
        fontMetrics = textPaint.getFontMetrics();

        paint.setColor(Color.GREEN);
    }

    public void setItemGroupInfoFetcher(ItemGroupInfoFetcher itemGroupInfoFetcher) {
        this.itemGroupInfoFetcher = itemGroupInfoFetcher;
    }


    /**
     * Draw any appropriate decorations into the Canvas supplied to the RecyclerView.
     * Any content drawn by this method will be drawn before the item views are drawn,
     * and will thus appear underneath the views.
     *
     * @param c      Canvas to draw into
     * @param parent RecyclerView this ItemDecoration is drawing into
     * @param state  The current state of RecyclerView
     */
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        //        Log.d(TAG, "onDraw: ");

        //遍历recyclerView（parent）的可见子view，判断可见子view是组第一个则画组title
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            //知识点： recyclerView的子view就是adapter创建的itemView
            View itemView = parent.getChildAt(i);
            ItemGroupInfo itemGroupInfo =
                    itemGroupInfoFetcher.getItemGroupInfo(parent.getChildAdapterPosition(
                    itemView));
            if (itemGroupInfo.isGroupFirst()) {

                //混淆了的时候 打印一下看看。
                /*System.out.println("Top:" + itemView.getTop());
                System.out.println("Bottom:" + itemView.getBottom());
                System.out.println("Left:" + itemView.getLeft());
                System.out.println("Right:" + itemView.getRight());
                System.out.println("X:" + itemView.getX());
                System.out.println("Y:" + itemView.getY());*/

                int left = itemView.getLeft();
                int top = itemView.getTop() - mHeaderHeight;
                int right = itemView.getRight();
                int bottom = itemView.getTop();//等于itemView的getY();
                //绘制组标题区域
                c.drawRect(left, top, right, bottom, paint);

                float titleX = left;
                float titleY = itemView.getY() - fontMetrics.bottom;
                //绘制Title
                c.drawText(itemGroupInfo.getGroupName(), titleX, titleY, textPaint);
            }
        }
    }

    /**
     * Draw any appropriate decorations into the Canvas supplied to the RecyclerView.
     * Any content drawn by this method will be drawn after the item views are drawn
     * and will thus appear over the views.
     *
     * @param c      Canvas to draw into
     * @param parent RecyclerView this ItemDecoration is drawing into
     * @param state  The current state of RecyclerView.
     */
    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        //draw background
        int left = 0;
        int right = parent.getWidth();
        int top = 0;
        float bottom = mHeaderHeight;
        c.drawRect(left, top, right, bottom, paint);

        //find firstVisible item groupInfo,draw the group info name
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) parent.getLayoutManager();
        //知识点：这里的统计第一可见，只要没滑出RecyclerView的边界，就算还visible，不受drawOver内容覆盖的影响。
        int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition();
        ItemGroupInfo firstVisibleItemGroupInfo = itemGroupInfoFetcher.getItemGroupInfo(
                firstVisibleItemPosition);
        ItemGroupInfo nextItemGroupInfo =
                itemGroupInfoFetcher.getItemGroupInfo(++firstVisibleItemPosition);

        //知识点：parent即recyclerView的childAt(0)一直保持为可见的第一个itemView
        //而linearLayoutManager.findFirstVisibleItemPosition()得到的position为itemView对应adapter的position
        //千万不要给recycler的childAt传adapter的position这么用 ，已经失误过一次！！！

        //if is group last item, perform moving draw
        if (nextItemGroupInfo.isGroupFirst()) {
            View itemViewOfGroupLast = parent.getChildAt(0);//firstVisibleItemView is group last

            //change bottom within this condition can:
            // 1.perform group last name drawing moving up or down
            // follow scroll up or down
            // 2.perform the group first name drawing moving up or down

            if (itemViewOfGroupLast.getBottom() < mHeaderHeight) {
                bottom = itemViewOfGroupLast.getBottom();

                //draw next item's group name follow  scrolling.
                c.drawText(nextItemGroupInfo.getGroupName(),
                           left,
                           bottom - fontMetrics.bottom + mHeaderHeight,
                           textPaint);

                //这里要参考fontMatrix的的图文文档
            }

        }
        //draw current group text on hovered view.
        c.drawText(firstVisibleItemGroupInfo.getGroupName(),
                   left,
                   bottom - fontMetrics.bottom,
                   textPaint);
    }

    /**
     * Retrieve any offsets for the given item. Each field of <code>outRect</code> specifies
     * the number of pixels that the item view should be inset by, similar to padding or margin.
     * The default implementation sets the bounds of outRect to 0 and returns.
     *
     * <p>
     * If this ItemDecoration does not affect the positioning of item views, it should set
     * all four fields of <code>outRect</code> (left, top, right, bottom) to zero
     * before returning.
     *
     * <p>
     * If you need to access Adapter for additional data, you can call
     * {@link RecyclerView#getChildAdapterPosition(View)} to get the adapter position of the
     * View.
     *
     * @param outRect Rect to receive the output.
     * @param view    The child view to decorate
     * @param parent  RecyclerView this ItemDecoration is decorating
     * @param state   The current state of RecyclerView.
     */
    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent,
                               RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        System.out.println("getItemOffsets: ");

        outRect.top = 25;
        if (itemGroupInfoFetcher != null) {
            //知识点： recyclerView的子view就是adapter创建的itemView
            //知识点： 通过parent.getChildAdapterPosition(view); 拿到当前item的位置
            ItemGroupInfo itemGroupInfo =
                    itemGroupInfoFetcher.getItemGroupInfo(parent.getChildAdapterPosition(
                    view));
            if (itemGroupInfo.isGroupFirst()) {
                outRect.top = mHeaderHeight;
            }
        }

        //这里跟margin作用一样
        outRect.left = 0;
        outRect.right = 0;
        outRect.bottom = 0;
    }

}
