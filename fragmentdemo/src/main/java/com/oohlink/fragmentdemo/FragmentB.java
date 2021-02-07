package com.oohlink.fragmentdemo;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class FragmentB extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public FragmentB() {
    }

    public static FragmentB newInstance(String param1, String param2) {
        FragmentB fragment = new FragmentB();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Transition slideRightTransition = TransitionInflater.from(getActivity())
                                                            .inflateTransition(R.transition.slide_right);
        //设置该fragment进场的动画
        //setEnterTransition(slideRightTransition);

        Transition transition = TransitionInflater.from(getActivity())
                                                  .inflateTransition(R.transition.shared_image);
        //设置进入时 共享元素的变换动画
        setSharedElementEnterTransition(transition);


        // 注意这里按照官方文档给的R.transition.shared_image资源定义内只有一个<changeImageTransform/>，结果返回前
        // 一个fragment的动画有问题。
        // 之后在网友的正常的demo中发现使用的是 andriod.R.transition.move这个资源。这个资源包含几个其他变换
        // 和<changeImageTransform/>一起，然后就没有问题。
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_b, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //低于api21即android5.1时使用以下代码给 shared element view定义transition name
        //ViewCompat.setTransitionName(imageViewB,"imageviewb");
    }
}
