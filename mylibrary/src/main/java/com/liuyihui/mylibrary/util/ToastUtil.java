package com.liuyihui.mylibrary.util;

import android.widget.Toast;

public class ToastUtil {
    public static void toast(String msg) {
        Toast.makeText(ContextUtil.getContext(), msg, Toast.LENGTH_SHORT).show();
    }
}
