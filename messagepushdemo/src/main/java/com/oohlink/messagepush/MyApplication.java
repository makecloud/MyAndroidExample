package com.oohlink.messagepush;

import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import com.umeng.commonsdk.UMConfigure;
import com.umeng.message.IUmengRegisterCallback;
import com.umeng.message.PushAgent;
import com.umeng.message.UmengMessageHandler;
import com.umeng.message.entity.UMessage;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 友盟推送集成demo
 */
public class MyApplication extends Application {

    private final String TAG = "MyApplication";

    @Override
    public void onCreate() {
        super.onCreate();
        //创建通知渠道
        createNotificationChannel();

        //初始化友盟推送
        initUmengPush();

    }

    public void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_MAX;
            NotificationChannel channel = new NotificationChannel(Constant.CHANNEL_ID,
                                                                  Constant.CHANNEL_NAME,
                                                                  importance);
            channel.setDescription("这是消息渠道");
            channel.enableLights(true);
            channel.enableVibration(true);


            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public void initUmengPush() {
        // 在此处调用基础组件包提供的初始化函数 相应信息可在应用管理 -> 应用信息 中找到 http://message.umeng.com/list/apps
        // 参数一：当前上下文context；
        // 参数二：应用申请的Appkey（需替换）；
        // 参数三：渠道名称；
        // 参数四：设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure
        // .DEVICE_TYPE_BOX则表示盒子；默认为手机；
        // 参数五：Push推送业务的secret 填充Umeng Message Secret对应信息（需替换）
        UMConfigure.init(this,
                         Constant.UMENG_PUSH_KEY,
                         "debug",
                         UMConfigure.DEVICE_TYPE_PHONE,
                         "754e607d5d9621877e26ea52144a7b90");


        //发现不在 Application的OnCreate方法里初始化pushSDK，推送无法回调。。。
        //获取消息推送代理示例
        PushAgent mPushAgent = PushAgent.getInstance(this);
        //注册推送服务，每次调用register方法都会回调该接口
        mPushAgent.register(new IUmengRegisterCallback() {

            @Override
            public void onSuccess(String deviceToken) {
                //注册成功会返回deviceToken deviceToken是推送消息的唯一标志
                Log.i(TAG, "注册成功：deviceToken：-------->  " + deviceToken);
                Constant.deviceToken = deviceToken;
            }

            @Override
            public void onFailure(String s, String s1) {
                Log.e(TAG, "注册失败：-------->  " + "s:" + s + ",s1:" + s1);
            }
        });

        //数据统计
        //PushAgent.getInstance(this).onAppStart();


        //自定义消息的处理
        UmengMessageHandler messageHandler = new UmengMessageHandler() {
            private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            @Override
            public void dealWithCustomMessage(final Context context, final UMessage msg) {

                Log.i(TAG,
                      "dealWithCustomMessage: " + String.format("%s - %s", msg.title, msg.custom));

                Constant.receivedMessageList.add(String.format("message： %s \n%s",
                                                               msg.custom,
                                                               simpleDateFormat.format(new Date())));

                NotificationShower.getInstance()
                                  .showNotificationBar(getApplicationContext(), msg.custom);

            }


            @Override
            public Notification getNotification(Context context, UMessage msg) {
                Log.d(TAG, "getNotification: " + msg.extra);
                Constant.receivedMessageList.add(String.format("notification：%s \n%s",
                                                               msg.text,
                                                               simpleDateFormat.format(new Date())));
                //默认为0，若填写的builder_id并不存在，也使用默认。
                return super.getNotification(context, msg);
            }

        };


        mPushAgent.setMessageHandler(messageHandler);
    }
}
