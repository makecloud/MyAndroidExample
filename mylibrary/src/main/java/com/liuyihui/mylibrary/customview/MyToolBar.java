package com.liuyihui.mylibrary.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.liuyihui.mylibrary.R;


/**
 * 1.重写Toolbar, 加入一个TextView作为标题实现居中
 * 也是重写view,研究view三个构造方法参数,自定义属性的经验记录
 * <p>
 * 运用到了知识点:
 * <p>重写view的构造方法,及参数的理解和使用方法</p>
 * <p>自定义属性</p>
 * <p>
 * <p>
 * 2.还有一种通过反射实现了改变原title view的layoutParam 实现居中的方式,字数多的时候, 不居中偏右.
 * Created by liuyi on 2019/4/18.
 */

public class MyToolBar extends Toolbar {
    private final String TAG = "MyToolBar";
    private TextView textView;
    private Context context;


    /**
     * 布局中使用该自定义view时,调用的这个构造方法
     *
     * @param context
     * @param attrs
     */
    public MyToolBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        initTitleView(attrs);
    }

    public void initTitleView(AttributeSet attributeSet) {
        // title textView
        textView = new TextView(context, null, 0, android.R.style.Widget_Holo_Light_TextView);
        addView(textView);
        Toolbar.LayoutParams layoutParams = new Toolbar.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                                     ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        textView.setLayoutParams(layoutParams);
        textView.setTextSize(21);
        textView.setGravity(Gravity.RIGHT);

        //get property
        TypedArray typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.MyToolBar);
        String titleTextStr = typedArray.getString(R.styleable.MyToolBar_titleText);
        int color = typedArray.getColor(R.styleable.MyToolBar_titleTextColor,
                                        getResources().getColor(R.color.myToolBarTitleTextDefaultColor));
        typedArray.recycle();

        //must use this, must in init, to remove original actionBar title
        setTitle("");

        //set property
        if (!TextUtils.isEmpty(titleTextStr)) {
            textView.setText(titleTextStr);
        }
        textView.setTextColor(color);

        //todo menu text color

    }

    public void setTitleText(String text) {
        textView.setText(text);
    }

}
