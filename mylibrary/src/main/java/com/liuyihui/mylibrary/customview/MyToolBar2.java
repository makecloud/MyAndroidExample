package com.liuyihui.mylibrary.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import com.client.mylibrary.util.LogUtil;
import com.liuyihui.client.mylibrary.R;

import java.lang.reflect.Field;


/**
 * 重写Toolbar
 * <p>
 * 通过反射实现了改变原title view的layoutParam 实现居中的方式,字数多的时候, 不居中偏右.
 * <p>
 *
 * @deprecated 字数变多偏右
 * <p>
 * Created by liuyi on 2019/4/18.
 */

public class MyToolBar2 extends Toolbar {
    private final String TAG = "MyToolBar";
    private Context context;
    private boolean titleCenter;


    /**
     * 布局中使用该自定义view时,调用的是这个构造方法
     *
     * @param context
     * @param attrs
     */
    public MyToolBar2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        //get property into filed
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyToolBar);
        titleCenter = typedArray.getBoolean(R.styleable.MyToolBar_titleCenter, false);
        typedArray.recycle();

        configTitle();
    }

    @Override
    public void setTitle(CharSequence title) {
        super.setTitle(title);
        configTitle();
    }

    /**
     * 通过反射实现了改变原title view的layoutParam 实现居中的方式,字数多的时候, 不居中偏右.
     */
    public void configTitle() {

        //config accord property
        if (titleCenter) {
            try {
                Field field = getClass().getSuperclass().getDeclaredField("mTitleTextView");
                field.setAccessible(true);
                TextView textView = (TextView) field.get(this);
                if (textView == null) {
                    LogUtil.e(TAG, "make sure title not empty before set center !!");
                    return;
                }
                LayoutParams layoutParam = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                                            ViewGroup.LayoutParams.WRAP_CONTENT);

                layoutParam.gravity = Gravity.CENTER;
                textView.setLayoutParams(layoutParam);
//            textView.setTextColor(context.getResources().getColor(R.color.red));

            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


}
