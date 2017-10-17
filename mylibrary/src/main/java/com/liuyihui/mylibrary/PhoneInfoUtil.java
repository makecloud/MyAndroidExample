package com.liuyihui.mylibrary;

import android.content.Context;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;

/**
 * 手机卡信息工具类
 * Created by liuyi on 2017/9/12.
 */
public class PhoneInfoUtil {

    private static String TAG = "PhoneInfoUtil";
    private static TelephonyManager telephonyManager;
    /** 移动运营商编号 */
    private static String NetworkOperator;


    /**
     * 初始化
     *
     * @param ctx 上下文
     */
    public static void init(Context ctx) {
        telephonyManager = (TelephonyManager) ctx.getSystemService(ctx.TELEPHONY_SERVICE);
    }


    /**
     * 获取sim卡iccid
     *
     * @return
     */
    public static String getIccid(Context ctx) {
        init(ctx);
        String iccid = "N/A";
        iccid = telephonyManager.getSimSerialNumber();
        return iccid;
    }

    /**
     * //获取电话号码
     *
     * @return
     */
    public static String getNativePhoneNumber(Context ctx) {
        init(ctx);
        String nativePhoneNumber = "N/A";
        nativePhoneNumber = telephonyManager.getLine1Number();
        return nativePhoneNumber;
    }

    /**
     * //获取手机服务商信息
     *
     * @return
     */
    public static String getProvidersName(Context ctx) {
        init(ctx);
        String providersName = "N/A";
        NetworkOperator = telephonyManager.getNetworkOperator();
        //IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
//        Flog.d(TAG,"NetworkOperator=" + NetworkOperator);
        if (NetworkOperator.equals("46000") || NetworkOperator.equals("46002")) {
            providersName = "中国移动";//中国移动
        } else if (NetworkOperator.equals("46001")) {
            providersName = "中国联通";//中国联通
        } else if (NetworkOperator.equals("46003")) {
            providersName = "中国电信";//中国电信
        }
        return providersName;

    }

    /**
     * 获取手机信息
     *
     * @return
     */
    public static String getPhoneInfo(Context ctx) {
        init(ctx);
        TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);
        StringBuffer sb = new StringBuffer();

        sb.append("\nLine1Number(MSISDN) = " + tm.getLine1Number());
        sb.append("\nNetworkOperator = " + tm.getNetworkOperator());//移动运营商编号
        sb.append("\nNetworkOperatorName = " + tm.getNetworkOperatorName());//移动运营商名称
        sb.append("\nSimCountryIso = " + tm.getSimCountryIso());
        sb.append("\nSimOperator = " + tm.getSimOperator());
        sb.append("\nSimOperatorName = " + tm.getSimOperatorName());
        sb.append("\nSimSerialNumber(Iccid) = " + tm.getSimSerialNumber());
        sb.append("\nSubscriberId(IMSI) = " + tm.getSubscriberId());
        sb.append("\nDeviceId(IMEI)=" + tm.getDeviceId());
        return sb.toString();
    }

    /**
     * 获取Android_ID
     *
     * @param ctx 上下文
     * @return
     */
    public static String getAndroidID(Context ctx) {
        return Settings.System.getString(ctx.getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    /**
     * 返回安卓序列号<br>
     * Android系统2.3版本以上可以通过下面的方法得到Serial Number，且非手机设备也可以通过该接口获取
     *
     * @return
     */
    public static String getBuildSerialNum() {
        return Build.SERIAL;
    }

}
