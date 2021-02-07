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
import android.widget.ImageView;


public class FragmentA extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    public FragmentA() {
    }

    public static FragmentA newInstance(String param1, String param2) {
        FragmentA fragment = new FragmentA();
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
        Transition fadeTransition = TransitionInflater.from(getActivity())
                                                      .inflateTransition(R.transition.fade);
        //设置该fragment 进场的动画
        //setExitTransition(fadeTransition);
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_a, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        final ImageView imageViewA = view.findViewById(R.id.imageViewA);

        //低于api21即android5.1时使用以下代码给 shared element view定义transition name
        //ViewCompat.setTransitionName(imageViewA,"imageviewa");

        imageViewA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //创建b
                final FragmentB fragmentB = FragmentB.newInstance(null, null);

                //添加b. 这里addSharedElement传入即将消失的frag的共享元素view和即将出现的frag的共享元素transitionName
                getActivity().getSupportFragmentManager()
                             .beginTransaction()
                             .addSharedElement(imageViewA, "imageviewb")
                             .addToBackStack(null)
                             .replace(R.id.fragContainer, fragmentB)
                             .commit();
            }
        });

    }
}
