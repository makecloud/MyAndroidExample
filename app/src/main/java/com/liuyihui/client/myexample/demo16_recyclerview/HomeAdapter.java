package com.liuyihui.client.myexample.demo16_recyclerview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liuyihui.client.myexample.R;

import java.util.ArrayList;
import java.util.List;

/**
 * recyclerView 的适配器
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
    private Context context;
    private List<ItemData> dataSet;

    public HomeAdapter(Context context) {
        this.context = context;

        dataSet = new ArrayList<>();
        for (int i = 'A'; i < 'z'; i++) {
            ItemData itemData = new ItemData();
            itemData.setId(i);
            itemData.setName(String.valueOf((char) i));

            ItemGroupInfo itemGroupInfo = new ItemGroupInfo();
            if (i % 5 == 0) {
                itemGroupInfo.setGroupFirst(true);
            }
            itemGroupInfo.setGroupName(String.valueOf(i / 5));
            itemGroupInfo.setGroupId(i / 5);

            itemData.setItemGroupInfo(itemGroupInfo);

            dataSet.add(itemData);
        }
    }

    public ItemGroupInfo getItemGroupInfo(int position) {
        return dataSet.get(position)
                      .getItemGroupInfo();
    }

    /**
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context)
                                                             .inflate(R.layout.recyclerview_item,
                                                                      parent,
                                                                      false));
        holder.tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "ww", Toast.LENGTH_SHORT)
                     .show();
            }
        });
        return holder;
    }

    /**
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv.setText(dataSet.get(position)
                                 .getName());

    }

    /**
     * @return
     */
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    /**
     *
     */
    class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv;
        FrameLayout rootLayout;

        public MyViewHolder(View view) {
            super(view);
            tv = view.findViewById(R.id.id_num);
            rootLayout = view.findViewById(R.id.rootLayout);
        }
    }
}