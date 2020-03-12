package util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by liuyi on 2018/1/31.
 */

public class ContextUtil {
    private static final String TAG = "ContextUtil";
    public static Context context;

    //静态块,在加载类,执行构造方法前执行
    static {
        //library中为拿到context,使用反射获取appModule中的Application类context属性
        try {
            Class cls = Class.forName("com.yunge.client.media.myexample");
            context = (Context) cls.getDeclaredField("context").get(null);//由于context是静态字段, 所以参数null是被忽略的
        } catch (ClassNotFoundException e) {
            try {//拿不到mediaapp的再去拿advertiser app模块下 的application实例
                Class cls = Class.forName("com.yunge.client.advertiser.MyApplication");
                context = (Context) cls.getDeclaredField("context").get(null);//由于context是静态字段, 所以参数null是被忽略的
            } catch (ClassNotFoundException e1) {
                Log.e(TAG, "获取context出错", e1);
            } catch (IllegalAccessException e1) {
                Log.e(TAG, "获取context出错", e1);
            } catch (NoSuchFieldException e1) {
                Log.e(TAG, "获取context出错", e1);
            }
        } catch (NoSuchFieldException e) {
            Log.e(TAG, "获取context出错", e);
        } catch (IllegalAccessException e) {
            Log.e(TAG, "获取context出错", e);
        }
    }


    public static Context getContext() {
        return context;
    }

    /**
     * 跳转到登录页activity.并销毁之前的activity
     */
    public static void gotoLoginActivity() {
        Context ctx = ContextUtil.getContext();
        Intent i = new Intent();
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        i.setClassName(ctx, "com.yunge.client.media.common.view.LoginActivity");
        ctx.startActivity(i);
        Log.i(TAG, "go to login");
    }
}
