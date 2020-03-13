package com.liuyihui.mylibrary.util;

import android.text.TextUtils;
import android.util.Log;

/**
 * 二维码内容工具类
 */
public class QrcodeUtil {
    public static final String OOHLINK_IDENTIFY_AT_START = "OOHLINK";
    public static final String OOHLINK_IDENTIFY = "playCode";
    public final static String QRCODE_INVALID = "二维码无效";
    public final static String QRCODE_NOT_FROM_OOHLINK = "二维码来源无效";
    private static String TAG = "QrcodeUtil";

    /**
     * saas二维码规则：baseUrl/play/bind_player?playCode=播控编码&width=分辨率宽&height=分辨率高&ts=时间戳
     *
     * @param qrCode
     * @return
     */
    public static boolean verifyPlayerCode(String qrCode) {
        Log.d(TAG, "verifyPlayerCode: " + qrCode);
        //校验空值
        if (TextUtils.isEmpty(qrCode)) {
            ToastUtil.toast(QRCODE_INVALID);
            return false;
        }

        //oohlink来源标识校验
        if (!qrCode.contains(OOHLINK_IDENTIFY) && !qrCode.startsWith(OOHLINK_IDENTIFY_AT_START)) {
            ToastUtil.toast(QRCODE_NOT_FROM_OOHLINK);
            return false;
        }

        return true;
    }

    /**
     * 提取二维码内的播控编码
     *
     * @param qrCode
     * @return 包含空串结果
     */
    public static String extractPlayerCode(String qrCode) {
        String playCode = "";
        if (qrCode.startsWith(OOHLINK_IDENTIFY_AT_START)) {
            playCode = qrCode.substring(OOHLINK_IDENTIFY_AT_START.length());
        } else {
            try {
                String[] qrcodeArray = qrCode.split("\\?");
                String[] urlParamsArray = qrcodeArray[1].split("&");
                for (int i = 0; i < urlParamsArray.length; i++) {
                    String param = urlParamsArray[i];
                    String[] paramNameValue = param.split("=");
                    String paramName = paramNameValue[0];
                    if (OOHLINK_IDENTIFY.equals(paramName)) {
                        playCode = paramNameValue[1];
                    }
                }
                if (TextUtils.isEmpty(playCode)) {
                    ToastUtil.toast("未识别到播控编码");
                }
            } catch (Exception e) {
                ToastUtil.toast("无法识别播控编码");
            }
        }
        return playCode;
    }

    /**
     * 提取二维码内的分辨率
     *
     * @param qrCode
     * @return 只要有一个未识别到就返回null
     */
    public static Integer[] extractResolution(String qrCode) {
        Integer[] resolution = new Integer[2];
        try {
            String[] qrcodeArray = qrCode.split("\\?");
            Log.e(TAG, "extractResolution: " + qrcodeArray[0]);
            Log.e(TAG, "extractResolution: " + qrcodeArray[1]);
            String[] urlParamsArray = qrcodeArray[1].split("&");
            for (int i = 0; i < urlParamsArray.length; i++) {
                String param = urlParamsArray[i];
                String[] paramNameValue = param.split("=");
                String paramName = paramNameValue[0];
                if ("width".equals(paramName)) {
                    resolution[0] = Integer.valueOf(paramNameValue[1]);
                }
                if ("height".equals(paramName)) {
                    resolution[1] = Integer.valueOf(paramNameValue[1]);
                }
            }
            if (resolution[0] == null) {
                ToastUtil.toast("未识别到屏幕宽");
                return null;
            }
            if (resolution[1] == null) {
                ToastUtil.toast("未识别到屏幕高");
                return null;
            }
        } catch (Exception e) {
            Log.e(TAG, "extractResolution: ", e);
            ToastUtil.toast("无法识别分辨率");
            return null;
        }
        return resolution;
    }
}
