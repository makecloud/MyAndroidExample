package com.liuyihui.client.myexample.example5_use_amap;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapOptions;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.SupportMapFragment;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.Circle;
import com.amap.api.maps.model.CircleOptions;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.AlphaAnimation;
import com.amap.api.maps.model.animation.Animation;
import com.amap.api.maps.model.animation.RotateAnimation;
import com.amap.api.maps.model.animation.TranslateAnimation;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.liuyihui.client.myexample.R;

/**
 * SupportMapFragment显示地图
 */
public class BaseMapFragmentActivity extends AppCompatActivity implements LocationSource, AMapLocationListener, AMap.OnCameraChangeListener {
    /**
     * 常量
     */
    private final String TAG = "BaseMapFragmentActivity";
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    public static final String LOCATION_MARKER_FLAG = "mylocation";

    /**
     * 数据
     */
    private AMapLocationClient mLocationClient;//定位服务类。此类提供单次定位、持续定位、地理围栏、最后位置相关功能。
    private AMapLocationClientOption mLocationClientOption;//定位参数设置，通过这个类可以对定位的相关参数进行设置
    private LatLng myLocation;//存储经纬度坐标值的类，单位角度。
    private Animation myAnimation;
    private boolean mFirstFix = false;
    private SensorEventHelper mSensorHelper;//传感器类
    private Circle mCircle;
    private GeocodeSearch geocodeSearch;
    private boolean isMovingMarker = false;
    private BitmapDescriptor movingDescriptor, chooseDescripter, successDescripter;
    private ValueAnimator animator = null;

    /**
     * 接口
     */
    private OnLocationChangedListener onLocationChangedListener;

    /**
     * 控件
     */
    private View view;
    private MapView mapView;
    private AMap aMap;//地图对象
    private UiSettings mUiSettings;//定义一个UiSettings对象
    private Marker centerMarker;//Marker 是在地图上的一个点绘制图标。这个图标和屏幕朝向一致，和地图朝向无关，也不会受地图的旋转、倾斜、缩放影响。
    private Marker markerBeijing;
    private Marker curLocationMarker;//○>形标记，作为定位当前位置指针
    EditText addressEditText;

    /*infoWindow*/
    private View infoWindow = null;

    /**
     * 信息窗适配器
     */
    private AMap.InfoWindowAdapter infoWindowAdapter = new AMap.InfoWindowAdapter() {
        @Override
        public View getInfoWindow(Marker marker) {
            Toast.makeText(BaseMapFragmentActivity.this, "invoke", Toast.LENGTH_SHORT).show();
            if (infoWindow == null) {
                infoWindow = LayoutInflater.from(BaseMapFragmentActivity.this).inflate(R.layout.info_window, null);
            }
            addressEditText = (EditText) infoWindow.findViewById(R.id.et_input_address);
//            addressEditText.setText(marker.getSnippet());
            Button addressInfoConfirmButton = (Button) infoWindow.findViewById(R.id.btn_address_info_confirm);
            addressInfoConfirmButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // TODO: 2017/2/6 调用异步服务，提交地址，经纬度

                }
            });
            return infoWindow;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    };

    /**
     * 创建
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_map_fragment);
        //获得地图Fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        //地图对象
        aMap = supportMapFragment.getMap();
        mUiSettings = aMap.getUiSettings();//实例化UiSettings类对象

        //配置
        initAnimation();
        amapSetting();
        uiSetting();
//        initBeijingMarker();
        aMap.setInfoWindowAdapter(infoWindowAdapter);//设置amap的infoWindow适配器
    }

    private void initAnimation() {
        myAnimation = new AlphaAnimation(0.0f, 1.0f);
        myAnimation.setDuration(2000L);
    }

    private void amapSetting() {
        //传感器初始化
        mSensorHelper = new SensorEventHelper(this);
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
        // 设置定位监听
        aMap.setLocationSource(this);
        // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setMyLocationEnabled(true);
        // 设置定位的类型为定位模式，有定位、跟随或地图根据面向方向旋转几种
        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
        //设置amap对象的标记点击事件
        /*aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Toast.makeText(BaseMapFragmentActivity.this, "dian", Toast.LENGTH_SHORT).show();
                return false;
            }
        });*/
        aMap.setOnCameraChangeListener(this);
        movingDescriptor = BitmapDescriptorFactory.fromResource(R.drawable.loaction_moving);//移动
        geocodeSearch = new GeocodeSearch(this);
        aMap.setInfoWindowAdapter(infoWindowAdapter);//设置amap的infoWindow适配器
    }

    private void uiSetting() {
        //控件交互设置
        mUiSettings.setCompassEnabled(true);//设置显示指南针
        mUiSettings.setZoomControlsEnabled(true);//设置显示缩放加减号
        mUiSettings.setMyLocationButtonEnabled(true); //显示默认的定位按钮
        aMap.setMyLocationEnabled(true);// 可触发定位并显示当前位置
        mUiSettings.setScaleControlsEnabled(true);//控制比例尺控件是否显示
        mUiSettings.setLogoPosition(AMapOptions.LOGO_POSITION_BOTTOM_LEFT);//设置logo位置
    }

    private void initBeijingMarker() {
        //创建地图点标记（经纬度北京）
        LatLng latLng = new LatLng(39.906901, 116.397972);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("beijing");
        markerOptions.snippet("北京欢迎你");
        // 将Marker设置为贴地显示，可以双指下拉地图查看效果
        markerOptions.setFlat(true);//设置marker平贴地图效果
        markerBeijing = aMap.addMarker(markerOptions);

        //给北京标记 绘制动画效果 Marker
        Animation animation = new RotateAnimation(markerBeijing.getRotateAngle(), markerBeijing.getRotateAngle() + 360, 0, 0, 0);
        long duration = 500L;
        animation.setDuration(duration);
        animation.setInterpolator(new LinearInterpolator());
        markerBeijing.setAnimation(animation);
        markerBeijing.startAnimation();
    }


    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View v = super.onCreateView(parent, name, context, attrs);
        view = v;
        return v;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mSensorHelper != null) {
            mSensorHelper.registerSensorListener();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mSensorHelper != null) {
            mSensorHelper.unRegisterSensorListener();
            mSensorHelper.setCurrentMarker(null);
            mSensorHelper = null;
        }
        deactivate();
        mFirstFix = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mapView.onDestroy();
        if (curLocationMarker != null) {
            curLocationMarker.destroy();
        }
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    /**
     * locationSource
     * 激活定位
     *
     * @param onLocationChangedListener
     */
    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        this.onLocationChangedListener = onLocationChangedListener;
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(this);
            //初始化定位参数
            mLocationClientOption = new AMapLocationClientOption();
            //设置定位回调监听
            mLocationClient.setLocationListener(this);//: onLocationChanged(AMapLocation location)
            //设置为高精度定位模式
            mLocationClientOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationClientOption.setInterval(2000);//设置定位间隔
            //设置定位参数
            mLocationClient.setLocationOption(mLocationClientOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mLocationClient.startLocation();//启动定位
        }

    }

    /**
     * locationSource
     * 停止定位
     */
    @Override
    public void deactivate() {
        onLocationChangedListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    /**
     * 定位成功后回调函数（定位客户端定位一次，触发一次位置改变）
     *
     * @param aMapLocation
     */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        Log.i(TAG, "onLocationChanged");
        if (onLocationChangedListener != null && aMapLocation != null) {
            if (aMapLocation != null && aMapLocation.getErrorCode() == 0) {
                //onLocationChangedListener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                LatLng location = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
                myLocation = location;
                if (!mFirstFix) {
                    mFirstFix = true;
                    addCircle(location, aMapLocation.getAccuracy());//添加定位精度圆
                    addCurMarker(location);//添加定位图标
                    mSensorHelper.setCurrentMarker(curLocationMarker);//定位图标旋转
                    aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 18));
                } else {
                    mCircle.setCenter(location);
                    mCircle.setRadius(aMapLocation.getAccuracy());
                    curLocationMarker.setPosition(location);
                    //aMap.moveCamera(CameraUpdateFactory.changeLatLng(location));
                }
            } else {
                String errText = "定位失败," + aMapLocation.getErrorCode() + ": " + aMapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
            keepMarkerOnCenter();
        }
    }

    /**
     * 在移动地图时维持中心marker在屏幕中央
     * 为什么要在OnlocationChanged回调中创建中心marker？因为marker参数需要位置对象location
     */
    private void keepMarkerOnCenter() {
        MarkerOptions centerMarkerOptions = new MarkerOptions();// TODO: 2016/12/27 .icon();
        //以定位的位置作为marker参数
        centerMarkerOptions.position(myLocation);
        centerMarkerOptions.icon(movingDescriptor);
        centerMarkerOptions.title("中心针");
        if (null == centerMarker) {
            //如不存在则添加屏幕中央针
            centerMarker = aMap.addMarker(centerMarkerOptions);
        }
        centerMarker.setPositionByPixels(view.getWidth() / 2, view.getHeight() / 2);//marker使用经纬度数据创建，然后用此方法使marker显示在屏幕中央
        centerMarker.setInfoWindowEnable(true);//设置infoWindow可用
        //中心标记设置动画监听
        centerMarker.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart() {

            }

            @Override
            public void onAnimationEnd() {
//                centerMarker.showInfoWindow();
            }
        });
        //// TODO: 2017/2/6 添加info window ，可编辑
        /*handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                CameraUpdate update = CameraUpdateFactory.zoomTo(17f);
                aMap.animateCamera(update, 1000, new AMap.CancelableCallback() {
                    @Override
                    public void onFinish() {
                        aMap.setOnCameraChangeListener(BaseMapFragmentActivity.this);
                    }

                    @Override
                    public void onCancel() {
                    }
                });
            }
        }, 1000);*/
    }

    /**
     * 地图视角位置改变回调函数（手动或者自动滑动一次地图导致的改变）
     *
     * @param cameraPosition
     */
    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
//        centerMarker.hideInfoWindow();
    }


    /**
     * 地图视角位置改变完成回调函数（手动或者自动滑动一次地图导致的改变）
     *
     * @param cameraPosition
     */
    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
//        markerBeijing.startAnimation();
        /*if (addressEditText != null) {
            addressEditText.setText(cameraPosition.toString());
        }*/
        centerMarker.showInfoWindow();

        startCenterMarkerJumpAnimation();
        LatLonPoint pinedLatLng = new LatLonPoint(cameraPosition.target.longitude, cameraPosition.target.latitude);
    }

    /**
     * 添加圆
     *
     * @param latlng 经纬度
     * @param radius 半径
     */
    private void addCircle(LatLng latlng, double radius) {
        CircleOptions options = new CircleOptions();
        options.strokeWidth(1f);
        options.fillColor(FILL_COLOR);
        options.strokeColor(STROKE_COLOR);
        options.center(latlng);
        options.radius(radius);
        mCircle = aMap.addCircle(options);
    }

    /**
     * 添加当前位置标记
     *
     * @param latlng 经纬度
     */
    private void addCurMarker(LatLng latlng) {
        if (curLocationMarker != null) {
            return;
        }
        MarkerOptions options = new MarkerOptions();
        options.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(this.getResources(), R.mipmap.navi_map_gps_locked)));
        options.anchor(0.5f, 0.5f);
        options.position(latlng);
        curLocationMarker = aMap.addMarker(options);
//        curLocationMarker.setTitle(LOCATION_MARKER_FLAG);
    }

    private Animation jumpAnimation;

    /**
     * 屏幕中心marker 跳动一次
     */
    public void startCenterMarkerJumpAnimation() {

        if (centerMarker != null) {
            //根据屏幕距离计算需要移动的目标点
            final LatLng latLng = centerMarker.getPosition();
            Point point = aMap.getProjection().toScreenLocation(latLng);
            point.y -= dip2px(this, 80);//第二个参数上跳的高度
            LatLng target = aMap.getProjection().fromScreenLocation(point);
            if (jumpAnimation == null) {
            }
            //使用TranslateAnimation,填写一个需要移动的目标点
            jumpAnimation = new TranslateAnimation(target);
            jumpAnimation.setInterpolator(new Interpolator() {
                @Override
                public float getInterpolation(float input) {
                    // 模拟重加速度的interpolator
                    if (input <= 0.5) {
                        return (float) (0.5f - 2 * (0.5 - input) * (0.5 - input));
                    } else {
                        return (float) (0.5f - Math.sqrt((input - 0.5f) * (1.5f - input)));
                    }
                }
            });
            //整个移动所需要的时间
            jumpAnimation.setDuration(300);
            //设置动画
            centerMarker.setAnimation(jumpAnimation);
            //开始动画
            centerMarker.startAnimation();

        } else {
            Log.e("ama", "screenMarker is null");
        }
    }

    //dip和px转换
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /*主线程处理器*/
    private Handler handler = new Handler() {};


}
