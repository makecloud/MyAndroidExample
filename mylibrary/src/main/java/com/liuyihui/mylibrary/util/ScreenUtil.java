package com.liuyihui.mylibrary.util;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Surface;
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
     * 获取屏幕的宽度（单位：px）（系统UI/Display分辨率,可能跟屏幕物理像素不一致）
     *
     * @return 屏幕宽px
     */
    public static int getUIWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 17) {
            int screenWidth = 0;
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
                screenWidth = realSize.x;
            } catch (Exception ignored) {
            }
            return screenWidth;
        } else {
            DisplayMetrics dm = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(dm);
            return dm.widthPixels;
        }
    }

    /**
     * 获取屏幕的高度（单位：px）（系统UI/Display分辨率,可能跟屏幕物理像素不一致）
     *
     * @return 屏幕高px
     */
    public static int getUIHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        if (Build.VERSION.SDK_INT >= 17) {
            int screenHeight = 0;
            try {
                Point realSize = new Point();
                Display.class.getMethod("getRealSize", Point.class).invoke(display, realSize);
                screenHeight = realSize.y;
            } catch (Exception ignored) {

            }
            return screenHeight;
        } else {
            DisplayMetrics dm = new DisplayMetrics();
            windowManager.getDefaultDisplay().getMetrics(dm);
            return dm.heightPixels;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getPhysicalWidth(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int screenwidth = 0;
        try {
            screenwidth = display.getMode().getPhysicalWidth();
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenwidth;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static int getPhysicalHeight(Context context) {
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        int screenHeight = 0;
        try {
            screenHeight = display.getMode().getPhysicalHeight();
        } catch (NoSuchMethodError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return screenHeight;
    }

    /**
     * 设置屏幕为横屏
     * <p>还有一种就是在Activity中加属性android:screenOrientation="landscape"</p>
     * <p>不设置Activity的android:configChanges时，切屏会重新调用各个生命周期，切横屏时会执行一次，切竖屏时会执行两次</p>
     * <p>设置Activity的android:configChanges="orientation"时，切屏还是会重新调用各个生命周期，切横、竖屏时只会执行一次</p>
     * <p>设置Activity的android:configChanges="orientation|keyboardHidden|screenSize"（4.0以上必须带最后一个参数）时
     * 切屏不会重新调用各个生命周期，只会执行onConfigurationChanged方法</p>
     *
     * @param activity activity
     */
    public static void setLandscape(final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 设置屏幕为竖屏
     *
     * @param activity activity
     */
    public static void setPortrait(final Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    /**
     * 判断是否横屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    /**
     * 判断是否竖屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isPortrait(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }

    /**
     * 获取屏幕旋转角度
     *
     * @param activity activity
     * @return 屏幕旋转角度
     */
    public static int getScreenRotation(final Activity activity) {
        switch (activity.getWindowManager().getDefaultDisplay().getRotation()) {
            default:
            case Surface.ROTATION_0:
                return 0;
            case Surface.ROTATION_90:
                return 90;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_270:
                return 270;
        }
    }

    /**
     * 截屏
     *
     * @param activity activity
     * @return Bitmap
     */
    public static Bitmap screenShot(@NonNull final Activity activity) {
        View decorView = activity.getWindow().getDecorView();
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap bitmap = decorView.getDrawingCache();
        DisplayMetrics dm = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(dm);
        Bitmap ret = Bitmap.createBitmap(bitmap, 0, 0, dm.widthPixels, dm.heightPixels);
        decorView.destroyDrawingCache();
        decorView.setDrawingCacheEnabled(false);
        return ret;
    }

    public static Bitmap screenShot(@NonNull final View decorView) {
        decorView.setDrawingCacheEnabled(true);
        decorView.buildDrawingCache();
        Bitmap bitmap = decorView.getDrawingCache();
        Bitmap ret = Bitmap.createBitmap(bitmap, 0, 0, decorView.getMeasuredWidth(), decorView.getMeasuredHeight());
        decorView.destroyDrawingCache();
        decorView.setDrawingCacheEnabled(false);
        return ret;
    }

    /**
     * 判断是否锁屏
     *
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isScreenLock(Context context) {
        KeyguardManager km = (KeyguardManager) context.getSystemService(Context.KEYGUARD_SERVICE);
        return km.inKeyguardRestrictedInputMode();
    }

    /**
     * 设置进入休眠时长
     * <p>需添加权限 {@code <uses-permission android:name="android.permission.WRITE_SETTINGS" />}</p>
     *
     * @param duration 时长
     */
    public static void setSleepDuration(final int duration, Context context) {
        Settings.System.putInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT, duration);
    }

    /**
     * 获取进入休眠时长
     *
     * @return 进入休眠时长，报错返回-123
     */
    public static int getSleepDuration(Context context) {
        try {
            return Settings.System.getInt(context.getContentResolver(), Settings.System.SCREEN_OFF_TIMEOUT);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return -123;
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
