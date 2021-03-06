package com.liuyihui.viewdrag;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 这个是网格布局的recycler列表拖拽排序demo
 */
public class RecyclerGridDragItemForSortActivity extends AppCompatActivity {
    private final static String TAG = "DragActivity";
    RecyclerView recyclerView;
    List<String> dataList;

    private void initData() {
        dataList = new ArrayList<>();
        dataList.add("Hensen 下午1:22 老板：哈哈哈");
        dataList.add("流年不利 早上10:31 美女：呵呵哒");
        dataList.add("1402 下午1:55 嘻嘻哈哈");
        dataList.add("Unstoppable 下午4:32 美美哒");
        dataList.add("流年不利 晚上7:22 萌萌哒");
        dataList.add("Hensen 下午1:22 哈哈哈");
        dataList.add("Hensen 下午1:22 哈哈哈");
        dataList.add("Hensen 下午1:22 哈哈哈");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        // 设置列表
        recyclerView = findViewById(R.id.recycler_view);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);//2是列数
        recyclerView.setLayoutManager(gridLayoutManager);
        final MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(dataList);
        recyclerView.setAdapter(adapter);
        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                //可以拖拽的方向标识
                int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN
                        | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
                //可侧滑标识设置为0,标识禁止侧滑了
                int swipFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                return makeMovementFlags(dragFlags, swipFlags);
            }

            //上下滑拖拽
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                                  RecyclerView.ViewHolder viewHolder1) {
                Collections.swap(dataList, viewHolder.getAdapterPosition(),
                        viewHolder1.getAdapterPosition());
                adapter.notifyItemMoved(viewHolder.getAdapterPosition(),
                        viewHolder1.getAdapterPosition());
                return false;
            }

            //侧向滑懂item. 注意在grid布局中往左右横向拖动排序的动作还是drag, 不是侧滑!
            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
                dataList.remove(viewHolder.getAdapterPosition());
                adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
            }

            @Override
            public boolean isLongPressDragEnabled() {
                // 长按item不触发拖拽
                return false;
            }

            //开始拖拽
            @Override
            public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
                if (viewHolder != null) {
                    Log.i(TAG, "onSelectedChanged: " + actionState + ",itemId:" + viewHolder.getItemId());
                    if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            ((MyViewHolder) viewHolder).itemView.setElevation(30);
                            //item 变大一下及加个透明阴影
                            ((MyViewHolder) viewHolder).itemView.setPadding(0, 20, 0, 20);
                            ((MyViewHolder) viewHolder).itemView.setBackgroundResource(R.drawable.dragbackground);
                        }
                    }
                }
                super.onSelectedChanged(viewHolder, actionState);
            }

            //侧滑开关
            @Override
            public boolean isItemViewSwipeEnabled() {
                return false;
            }

            //结束拖拽
            @Override
            public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (viewHolder != null) {
                    Log.i(TAG, "clearView ,itemId:" + viewHolder.getItemId());
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                        ((MyViewHolder) viewHolder).itemView.setElevation(0);
                        ((MyViewHolder) viewHolder).itemView.setPadding(0, 0, 0, 0);
                        ((MyViewHolder) viewHolder).itemView.setBackgroundResource(android.R.color.white);
                    }
                }

                super.clearView(recyclerView, viewHolder);
            }
        });
        itemTouchHelper.attachToRecyclerView(recyclerView);
        adapter.setDragStarter(itemTouchHelper);
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        LinearLayout contianer;
        TextView textView;
        ImageView dragButton;

        public MyViewHolder(View itemView) {
            super(itemView);
            contianer = itemView.findViewById(R.id.container);
            textView = itemView.findViewById(R.id.text_view);
            dragButton = itemView.findViewById(R.id.dragButton);
        }
    }

    class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
        private List<String> dataList;
        private ItemTouchHelper dragStarter;

        MyRecyclerViewAdapter(List<String> dataList) {
            this.dataList = dataList;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            // 从layout生成 itemView
            View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_item, viewGroup, false);
            final MyViewHolder myViewHolder = new MyViewHolder(v);
            myViewHolder.dragButton.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    if (dragStarter != null) {
                        dragStarter.startDrag(myViewHolder);
                    }
                    return false;
                }
            });
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(MyViewHolder myViewHolder, int i) {
            String data = dataList.get(i);
            myViewHolder.textView.setText(data);
        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        public void setDragStarter(ItemTouchHelper dragStarter) {
            this.dragStarter = dragStarter;
        }
    }

}
