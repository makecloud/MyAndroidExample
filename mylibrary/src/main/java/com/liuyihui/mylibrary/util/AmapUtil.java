package com.liuyihui.mylibrary.util;

import android.content.Context;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * 搞得地图工具类
 * Created by liuyi on 2018/2/27.
 */

public class AmapUtil {
    private static final String TAG = "AmapUtil";
    /** 定位服务类。此类提供单次定位、持续定位、地理围栏、最后位置相关功能。 */
    private static AMapLocationClient mLocationClient;
    /** 定位参数设置，通过这个类可以对定位的相关参数进行设置 */
    private static AMapLocationClientOption mLocationClientOption;

    /**
     * 构造方法
     */
    private AmapUtil() {
    }

    /**
     * 获取单例
     *
     * @return
     */
    public static AmapUtil getInstance() {
        return InstanceHolder.amapUtil;
    }

    /**
     * 开始定位. 结果异步回调
     *
     * @param ctx
     * @param myLocateListener 定位后回调对象
     */
    public static void getLocationAsync(final Context ctx, final MyLocateListener myLocateListener) {
        if (myLocateListener == null) {
            throw new IllegalArgumentException("myLocateListener cannot be null.");
        }

        //mLocationClient 起始为null,配置
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(ctx);
            //初始化定位参数
            mLocationClientOption = new AMapLocationClientOption();
            //设置定位回调监听 : AMapLocationListener.onLocationChanged(AMapLocation location)
            mLocationClient.setLocationListener(new AMapLocationListener() {

                @Override
                public void onLocationChanged(AMapLocation aMapLocation) {
                    Log.d(TAG, "onLocationChanged---");
                    if (aMapLocation != null) {
                        //定位成功
                        if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                            if (myLocateListener instanceof MyLocateListener2) {
                                ((MyLocateListener2) myLocateListener).onLocateSuccess2(aMapLocation);
                            } else {
                                myLocateListener.onLocateSuccess(aMapLocation.getLatitude(), aMapLocation.getLongitude(), aMapLocation.getAddress());

                            }
                        } else {//定位失败
                            String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                            myLocateListener.onLocateFailed(errText);
                        }
                    }
                    mLocationClient.stopLocation();
                    mLocationClient.onDestroy();
                    mLocationClient = null;
                }
            });
            //设置为高精度定位模式
            mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationClientOption.setInterval(2000);//设置定位间隔
            //设置定位参数
            mLocationClient.setLocationOption(mLocationClientOption);
        }
        // 此方法每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用onDestroy()方法
        // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
        mLocationClient.startLocation();//启动定位
    }

    public String getPOIByLatlng() {
        return null;
    }

    /** 定位回调对象 */
    public interface MyLocateListener {
        void onLocateSuccess(Double latitude, Double longitude, String addressStr);

        void onLocateFailed(String errText);
    }

    public interface MyLocateListener2 extends MyLocateListener {
        void onLocateSuccess2(AMapLocation aMapLocation);
    }

    /**
     * 单例holder类
     */
    private static class InstanceHolder {
        private static AmapUtil amapUtil = new AmapUtil();
    }
}
