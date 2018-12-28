package com.liuyihui.viewpager.example;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

/**
 * Created by liuyi on 2018/8/30.
 */

public class ShufflePageItemView extends FrameLayout {
    private static final String TAG = "ShufflePageItemView";
    private Activity activity;
    private ImageView imageView;

    private FrameLayout videoLayout;
    private VideoView videoView;
    private MediaController mediaController;
    private boolean shownImage = false;
    private boolean shownVideo = false;

    /**
     * 构造函数
     *
     * @param context 需要为activity
     */
    public ShufflePageItemView(@NonNull Context context) {
        super(context);
        activity = (Activity) context;
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, Gravity.CENTER));
        //imageView
        imageView = new ImageView(activity);
        imageView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        addView(imageView);
        //videoView
        videoLayout = (FrameLayout) LayoutInflater.from(context).inflate(R.layout.videoview, null);
        videoView = videoLayout.findViewById(R.id.video_view);
        mediaController = new MediaController(context);
        videoView.setMediaController(mediaController);
        mediaController.setAnchorView(videoLayout);
        addView(videoLayout);

    }

    public void showImage(String imageUrl) {
        videoLayout.setVisibility(INVISIBLE);
        imageView.setVisibility(VISIBLE);
        shownImage = true;
        //加载图片
        Picasso.with(getContext())
               .load(imageUrl)
               .into(imageView);

    }

    public void showVideoView(String videoUrl) {
        imageView.setVisibility(INVISIBLE);
        videoLayout.setVisibility(VISIBLE);
//        mediaController.show();
        shownVideo = true;
//        videoUrl = "http://flashmedia.eastday.com/newdate/news/2016-11/shznews1125-19.mp4";//test line
        videoView.setVideoURI(Uri.parse(videoUrl));
    }

    public void pauseVideo() {
        if (shownVideo) {
            if (videoView.isPlaying()) {
                videoView.pause();
            }
        }
        Log.i(TAG, "pauseVideo");
    }

    public void releaseVideoView() {
        videoView.stopPlayback();
    }


}
