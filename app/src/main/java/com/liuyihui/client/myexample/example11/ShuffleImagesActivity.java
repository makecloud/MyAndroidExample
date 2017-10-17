package com.liuyihui.client.myexample.example11;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.liuyihui.client.myexample.R;

import java.util.ArrayList;


/**
 * 使用Android-ConvenientBanner控件的轮播图实例
 */
public class ShuffleImagesActivity extends AppCompatActivity {
    /*常量*/
    private final String TAG = "ShuffleImagesActivity";

    /*数据*/
    private ArrayList<Integer> localImages = new ArrayList<Integer>();//轮播图资源数组

    /*控件*/
    private ConvenientBanner convenientBanner;//轮播图控件

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shuffle_images);

        convenientBanner = (ConvenientBanner) findViewById(R.id.convenientBanner);
        //添加本地图片
        localImages.add(R.drawable.shuffle_image_1);
        localImages.add(R.drawable.shuffle_image_2);
        localImages.add(R.drawable.shuffle_image_3);

        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        convenientBanner.setPages(new CBViewHolderCreator<LocalImageHolderView>() {
            @Override
            public LocalImageHolderView createHolder() {
                return new LocalImageHolderView();
            }
        }, localImages);
        //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//        convenientBanner.setPageIndicator(new int[]{R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused})
        //设置指示器的方向
        convenientBanner.setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
        convenientBanner.setManualPageable(true);//设置不能手动影响

        //启动轮播
        convenientBanner.startTurning(600);
    }


    /**
     * 本地图片控制类
     */
    public class LocalImageHolderView implements Holder<Integer> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, Integer data) {
            imageView.setImageResource(data);
        }
    }
}
