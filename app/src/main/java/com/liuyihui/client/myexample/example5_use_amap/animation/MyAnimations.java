package com.liuyihui.client.myexample.example5_use_amap.animation;


import com.amap.api.maps.model.animation.ScaleAnimation;
import com.amap.api.maps.model.animation.TranslateAnimation;

/**
 * 高德地图动画
 * Created by liuyh on 2016/12/28.
 */

public class MyAnimations {

    private static ScaleAnimation markAnimation1;
    private static TranslateAnimation markAnimation2;

    public static ScaleAnimation getMarkAnimation1() {
        if (markAnimation1 == null) {
            markAnimation1 = new ScaleAnimation(1.0f, 2.0f, 1.0f, 2.0f);
        }
        return markAnimation1;
    }

    public static TranslateAnimation getMarkAnimation2() {
        if (markAnimation2==null){
        }
        return markAnimation2;
    }
}
