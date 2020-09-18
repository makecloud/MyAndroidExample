package com.liuyihui.client.myexample.demo5_1_use_amaplocate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.liuyihui.client.myexample.R;

public class GetLocationActivity extends AppCompatActivity {
    private static final String TAG = "GetLocationActivity";

    private AMapLocationClient mLocationClient;//定位服务类。此类提供单次定位、持续定位、地理围栏、最后位置相关功能。
    private AMapLocationClientOption mLocationClientOption;//定位参数设置，通过这个类可以对定位的相关参数进行设置
    private LatLng myLocation;//存储经纬度坐标值的类，单位角度。
    private double longitude;//经度
    private double latitude;//纬度

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_location);
        getLoccation();
    }

    /**
     * 开始定位
     */
    public void getLoccation() {
        //mLocationClient 起始为null,配置
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(this);
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
                            latitude = aMapLocation.getLatitude();
                            longitude = aMapLocation.getLongitude();
                            Toast.makeText(GetLocationActivity.this, "定位成功:" + latitude + "," + longitude, Toast.LENGTH_SHORT).show();
                        } else {//定位失败
                            String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                            Toast.makeText(GetLocationActivity.this, errText, Toast.LENGTH_SHORT).show();
                        }
                    }
                    mLocationClient.stopLocation();
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

}
