package com.liuyihui.apps;

import android.app.Application;

import com.tencent.bugly.Bugly;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

/**
 * Created by liuyi on 2017/11/30.
 */

public class MyApplication extends TinkerApplication {

    public MyApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.liuyihui.apps.MyApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
