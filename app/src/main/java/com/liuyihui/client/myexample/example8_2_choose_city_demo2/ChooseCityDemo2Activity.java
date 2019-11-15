package com.liuyihui.client.myexample.example8_2_choose_city_demo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.liuyihui.client.myexample.R;
import com.lljjcoder.Interface.OnCityItemClickListener;
import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.lljjcoder.style.citylist.Toast.ToastUtils;
import com.lljjcoder.style.citypickerview.CityPickerView;

public class ChooseCityDemo2Activity extends AppCompatActivity {
    //申明对象
    private CityPickerView mPicker;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city_demo2);

        mPicker=new CityPickerView();

        //预先加载仿iOS滚轮实现的全部数据
        mPicker.init(this);
    }

    public void choosecity(View view) {
        //添加默认的配置，不需要自己定义，当然也可以自定义相关熟悉，详细属性请看demo
        CityConfig cityConfig = new CityConfig.Builder().build();
        mPicker.setConfig(cityConfig);

        //监听选择点击事件及返回结果
        mPicker.setOnCityItemClickListener(new OnCityItemClickListener() {
            @Override
            public void onSelected(ProvinceBean province, CityBean city, DistrictBean district) {

                //省份province
                //城市city
                //地区district
            }

            @Override
            public void onCancel() {
                ToastUtils.showLongToast(ChooseCityDemo2Activity.this, "已取消");
            }
        });

        //显示
        mPicker.showCityPicker( );
    }
}
