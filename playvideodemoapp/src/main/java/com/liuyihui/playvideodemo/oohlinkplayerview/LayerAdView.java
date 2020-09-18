package com.liuyihui.playvideodemo.oohlinkplayerview;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

/**
 * 层view之广告层View：广告层View顺序播放广告素材Item
 * <p>
 *
 * @author makecloudl
 */

public class LayerAdView extends FrameLayout {

    private static final String TAG = "LayerAdView";
    //素材view
    private MaterialVideoView4 mMaterialVideoView;
    //播放控制
    private Context mContext;

    public LayerAdView(@NonNull Context context) {
        super(context);
        mContext = context;
        initView(context);
        setKeepScreenOn(true);
    }


    private void initView(Context context) {
        this.mContext = context;


    }

    public void prepare() {
        if (mMaterialVideoView == null) {

            mMaterialVideoView = new MaterialVideoView4(mContext);
            mMaterialVideoView.setVisibility(View.INVISIBLE);
            //处理图片切换视频导致的黑屏
            addView(mMaterialVideoView, 0);

            //视频居中
            LayoutParams layoutParams = (LayoutParams) mMaterialVideoView.getLayoutParams();
            layoutParams.gravity = Gravity.CENTER;
            mMaterialVideoView.setLayoutParams(layoutParams);
            mMaterialVideoView.setParentLayerView(LayerAdView.this);
            LayerAdView.this.setBackgroundColor(Color.BLACK);
        }
    }

    public void playOn() {
        String videoPath = "/sdcard/oohlink/player/.screen/72EE7BAD3659665BFF44C5EF23CF32B4";

        //准备播放
        mMaterialVideoView.setResourcePath(videoPath);

        mMaterialVideoView.playOn();
    }

    public void release() {
        if (mMaterialVideoView != null) {
            removeView(mMaterialVideoView);
            mMaterialVideoView.release();
            mMaterialVideoView = null;
        }
    }


}
