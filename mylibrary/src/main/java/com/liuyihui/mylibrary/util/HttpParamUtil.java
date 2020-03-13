package com.liuyihui.mylibrary.util;

import com.liuyihui.mylibrary.exception.OohlinkException;
import com.liuyihui.mylibrary.io.OohlinkSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * httpapi 请求参数工具类
 * Created by liuyi on 2018/5/15.
 */

public class HttpParamUtil {
    private static final String TAG = "HttpParamUtil";

    /**
     * @throws OohlinkException
     */
    public static Map<String, String> baseParamMap() {
        Map<String, String> paramMap = new HashMap<>();
        //获取token和五个基本参数
        String androidId = AndroidUtil.getAndroidID(ContextUtil.getContext());
        String token = "";
        token = OohlinkSerializer.loadTokenFromFile();
        String osType = AndroidUtil.osType;//系统类型
        String deviceModel = AndroidUtil.deviceModel;//设备型号
        String osVersion = AndroidUtil.osVersion;//android系统版本
        String appVersion = String.valueOf(AndroidUtil.getAppVersionCode(ContextUtil.getContext()));//app版本码

        //设置url参数
        paramMap.put("deviceId", androidId);
        paramMap.put("osType", osType);
        paramMap.put("osVersion", osVersion);
        paramMap.put("deviceModel", deviceModel);
        paramMap.put("appVersion", appVersion);
        paramMap.put("token", token);
        return paramMap;
    }

    /**
     * @return
     * @throws OohlinkException
     */
    public static Map<String, String> baseParamNoTokenMap() {
        Map<String, String> paramMap = new HashMap<>();
        //获取参数
        String androidId = AndroidUtil.getAndroidID(ContextUtil.getContext());
        String osType = AndroidUtil.osType;
        String deviceModel = AndroidUtil.deviceModel;
        String osVersion = AndroidUtil.osVersion;
        String appVersion = String.valueOf(AndroidUtil.getAppVersionCode(ContextUtil.getContext()));
        //设置url参数
        paramMap.put("deviceId", androidId);
        paramMap.put("osType", osType);
        paramMap.put("osVersion", osVersion);
        paramMap.put("deviceModel", deviceModel);
        paramMap.put("appVersion", appVersion);
        return paramMap;
    }
}
