package com.liuyihui.mylibrary.customview;


import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.liuyihui.client.mylibrary.R;

/**
 * @author Yue
 * 扇形进度进度条。
 * <p>说明：
 * <p>可以设置扇形颜色，背景，起始位置（度数）。
 * <p>定义了扇形颜色，起始位置（度数)，进度等属性。
 */
public class SectorProgressView extends View {


    private Paint paint;
    /** 进度 */
    private float progress;
    private RectF rectf;
    /** 绘制度数 */
    private float sweepangle;
//  private String namespace = "http://www.hao123.com";
    /** 画笔颜色 */
    private int shapeColor;
    private float startPosition;

    public SectorProgressView(Context context) {
        this(context, null);//调用更多参数的构造方法
    }

    public SectorProgressView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);//调用更多参数的构造方法
    }

    public SectorProgressView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private final void init(Context context, AttributeSet attrs, int defStyle) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.lu, defStyle, 0);

        progress = a.getFloat(R.styleable.lu_progress, 0f);//获取自定义属性的进度值
        //progress = attrs.getAttributeFloatValue(namespace , "hao",0);
        shapeColor = a.getColor(R.styleable.lu_shapeColor, Color.BLUE);//获取自定义属性的画笔颜色
        startPosition = a.getFloat(R.styleable.lu_startPosition, 0);//获取绘制的起始位置
        a.recycle();

        sweepangle = progress * 360;
        rectf = new RectF();

        paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
        paint.setColor(shapeColor);

    }

    @Override
    protected void onDraw(Canvas canvas) {
//      rectf = new RectF(0, 0, getWidth(), getHeight());
        if (rectf.isEmpty()) {
            rectf.set(0, 0, getWidth(), getHeight());
        }
        sweepangle = progress * 360;
        canvas.drawArc(rectf, -180 + startPosition, sweepangle, true, paint);//奇葩的，为0时是从右下角顺时针绘制，因此减去180度

    }

    /**
     * 设置进度。符合条件则更新进度条
     *
     * @param progress
     */
    public void setProgerss(float progress) {

        float newSweepangle = progress * 360;
        float sub = Math.abs(newSweepangle - sweepangle);
        if (progress != this.progress && sub > 1) {
            invalidate();
        }
        this.progress = progress;
    }

    /**
     * @return 当前进度
     */
    public double getProgress() {
        return progress;
    }

    /**
     * @return 扇形弧度
     */
    public float getSweepangle() {
        return sweepangle;
    }

    /**
     * @return 起始位置（度数）
     */
    public float getStartPosition() {
        return startPosition;
    }

    /**
     * 设置起始位置（度数）
     *
     * @param startPosition
     */
    public void setStartPosition(float startPosition) {
        this.startPosition = startPosition;
    }
}
