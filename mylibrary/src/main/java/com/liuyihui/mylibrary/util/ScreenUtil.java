package com.liuyihui.mylibrary.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;

/**
 * Created by liuyi on 2018/4/10.
 */

public class ScreenUtil {
    private static final String TAG = "ScreenUtil";

    public static void showPopupWindowCompat(View anchorView, @NonNull PopupWindow popupWindow) {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            return;
        }
        if (Build.VERSION.SDK_INT < 24) {
            popupWindow.showAsDropDown(anchorView);

        } else {
            int[] location = new int[2];
            anchorView.getLocationOnScreen(location);
            int x = location[0];
            int anchorViewY = location[1];

            //            popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, 0,
            //            anchorViewY + anchorView.getHeight());

            /*Rect visibleFrame = new Rect();
            anchorView.getGlobalVisibleRect(visibleFrame);
            int height = anchorView.getResources().getDisplayMetrics().heightPixels -
            visibleFrame.bottom;
            popupWindow.setHeight(height);*/
            //            popupWindow.getContentView().setPadding(0,anchorViewY,0,0);
            popupWindow.showAsDropDown(anchorView);
            //            popupWindow.showAsDropDown(anchorView, 0, anchorViewY + anchorView
            //            .getHeight());
            //            popupWindow.showAtLocation(anchorView, Gravity.NO_GRAVITY, 0,
            //            anchorViewY + anchorView.getHeight());
        }
    }

    /**
     * dip转px
     * <p>
     * metal : scale=3.0
     *
     * @param context
     * @param dpValue
     * @return
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * px 转 dp
     * <p>
     * metal : scale=3.0
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * convert px to its equivalent sp
     * metal : scaledDensity=3.0
     * <p>
     * 将px转换为sp
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }


    /**
     * convert sp to its equivalent px
     * <p>
     * 将sp转换为px
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha
     */
    public void backgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        activity.getWindow().setAttributes(lp);
    }
}
