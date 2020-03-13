package com.liuyihui.mylibrary.util;


import android.content.Context;
import android.text.TextUtils;

import com.liuyihui.mylibrary.exception.OohlinkException;
import com.liuyihui.mylibrary.io.OohlinkSerializer;

import java.util.Iterator;
import java.util.Map;

/**
 * url构建器
 * Created by liuyh on 2016/10/26.
 */

public class UrlBuilder {
    private final static String TAG = "UrlBuilder";
    private Context context;
    private String finalUrl;
    //    public static Properties prop=new Properties();
    //    // 从属性文件加载
    //    static {
    //        try {
    //            prop.load(context.getResources().openRawResource(R.raw.url));
    //        } catch (IOException e) {
    //            e.printStackTrace();
    //        }
    //    }


    /**
     * 构造方法
     *
     * @param url 无参的url
     */
    public UrlBuilder(String url) {
        finalUrl = url;
        context = ContextUtil.getContext();
    }

    /**
     * 为get请求的url添加一个请求参数
     *
     * @param isFirst
     * @param name
     * @param value
     * @return
     */
    public UrlBuilder addOneParam(boolean isFirst, String name, String value) {
        StringBuilder urlSb = new StringBuilder(finalUrl);
        if (isFirst) {
            urlSb.append("?");
        } else {
            urlSb.append("&");
        }
        urlSb.append(name).append("=").append(value);
        finalUrl = urlSb.toString();
        return this;
    }

    public UrlBuilder addOneParam(String name, String value) {
        addOneParam(false, name, value);
        return this;
    }

    /**
     * 将map添加到url参数
     *
     * @param paramMap
     * @return
     */
    public UrlBuilder addMapParam(Map<String, String> paramMap) {
        if (paramMap == null) {
            throw new IllegalArgumentException("paramMap参数为null");
        }

        Iterator it = paramMap.keySet().iterator();
        while (it.hasNext()) {
            String name = (String) it.next();
            String value = paramMap.get(name);
            if (!TextUtils.isEmpty(value)) {
                addOneParam(name, value);
            }
        }
        return this;
    }

    /**
     * 准备一个设置好云歌基本参数的url
     *
     * @throws OohlinkException
     */
    public UrlBuilder prepareBaseParam() throws OohlinkException {
        //获取token和五个基本参数
        String token = OohlinkSerializer.loadTokenFromFile();
        String androidId = AndroidUtil.getAndroidID(context);
        String osType = AndroidUtil.osType;//系统类型
        String deviceModel = AndroidUtil.deviceModel;//设备型号
        String osVersion = AndroidUtil.osVersion;//android系统版本
        String appVersion = AndroidUtil.getAppVersionCode(context) + "";//app版本码

        //设置url参数
        this.addOneParam(true, "deviceId", androidId);
        this.addOneParam("osType", osType);
        this.addOneParam("osVersion", osVersion);
        this.addOneParam("deviceModel", deviceModel);
        this.addOneParam("appVersion", appVersion);
        this.addOneParam("token", token);
        return this;
    }

    /**
     * 准备一个设置好基本参数的url 无token
     *
     * @return
     * @throws OohlinkException
     */
    public UrlBuilder prepareBaseParamNoToken() throws OohlinkException {
        //获取参数
//        String mac = AndroidUtil.getLocalMacAddressFromIp(context);//mac地址
        String osType = AndroidUtil.osType;
        String androidId = AndroidUtil.getAndroidID(context);
        String deviceModel = AndroidUtil.deviceModel;
        String osVersion = AndroidUtil.osVersion;
        String appVersion = AndroidUtil.getAppVersionCode(context) + "";
        //设置url参数
        this.addOneParam(true, "deviceId", androidId);
        this.addOneParam("osType", osType);
        this.addOneParam("osVersion", osVersion);
        this.addOneParam("deviceModel", deviceModel);
        this.addOneParam("appVersion", appVersion);
        return this;
    }

    public String build() {
        return toString();
    }

    @Override
    public String toString() {
        return finalUrl;
    }

}
