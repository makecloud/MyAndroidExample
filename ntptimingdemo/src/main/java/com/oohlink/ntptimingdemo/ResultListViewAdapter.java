package com.oohlink.ntptimingdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;

import java.util.List;

public class ResultListViewAdapter<T> extends ArrayAdapter<T> {

    public ResultListViewAdapter(@NonNull Context context, int resource, @NonNull List<T> objects) {
        super(context, resource, objects);
    }


    @Override
    public void add(@Nullable T object) {
        super.add(object);
    }
}
