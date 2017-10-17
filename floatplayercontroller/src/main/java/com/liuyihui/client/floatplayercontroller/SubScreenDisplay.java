package com.liuyihui.client.floatplayercontroller;

import android.app.Presentation;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Display;
import android.widget.FrameLayout;

/**
 * 使用display对象显示的presentation
 * Created by liuyh on 2017/7/25.
 */

@RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
public class SubScreenDisplay extends Presentation {

    private FrameLayout layout;

    public SubScreenDisplay(Context outerContext, Display display) {
        super(outerContext, display);
        //TODOAuto-generated constructor stub

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subdisplay_layout);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void onDisplayRemoved() {
        super.onDisplayRemoved();
    }

    @Override
    public void onDisplayChanged() {
        super.onDisplayChanged();
    }

    @Override
    public Resources getResources() {
        return super.getResources();
    }

    @Override
    public Display getDisplay() {
        return super.getDisplay();
    }
}
