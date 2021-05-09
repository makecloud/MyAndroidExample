package com.liuyihui.txpushlivedemo;

enum CPushEvent {
    PUSH_EVT_CONNECT_SUCC(1001, "已经成功连接到腾讯云推流服务器"),
    PUSH_EVT_PUSH_BEGIN(1002, "与服务器握手完毕，一切正常，准备开始推流"),
    PUSH_EVT_OPEN_CAMERA_SUCC(1003, "推流器已成功打开摄像头（Android 部分手机这个过程需要1秒 - 2秒）"),
    PUSH_ERR_OPEN_CAMERA_FAIL(-1301, "打开摄像头失败"),
    PUSH_ERR_OPEN_MIC_FAIL(-1302, "打开麦克风失败"),
    PUSH_ERR_VIDEO_ENCODE_FAIL(-1303, "视频编码失败"),
    PUSH_ERR_AUDIO_ENCODE_FAIL(-1304, "音频编码失败"),
    PUSH_ERR_UNSUPPORTED_RESOLUTION(-1305, "不支持的视频分辨率"),
    PUSH_ERR_UNSUPPORTED_SAMPLERATE(-1306, "不支持的音频采样率"),
    PUSH_ERR_NET_DISCONNECT(-1307, "网络断连，且经三次重连无效，可以放弃，更多重试请自行重启推流"),
    PUSH_WARNING_NET_BUSY(1101, "网络状况不佳：上行带宽太小，上传数据受阻"),
    PUSH_WARNING_RECONNECT(1102, "网络断连，已启动自动重连（自动重连连续失败超过三次会放弃）"),
    PUSH_WARNING_HW_ACCELERATION_FAIL(1103, "硬编码启动失败，采用软编码"),
    PUSH_WARNING_DNS_FAIL(3001, "RTMP -DNS 解析失败（会触发重试流程）"),
    PUSH_WARNING_SEVER_CONN_FAIL(3002, "RTMP 服务器连接失败（会触发重试流程）"),
    PUSH_WARNING_SHAKE_FAIL(3003, "RTMP 服务器握手失败（会触发重试流程）"),
    PUSH_WARNING_SERVER_DISCONNECT(3004, "RTMP 服务器主动断开连接（会触发重试流程）");

    private String name;
    private int code;

    CPushEvent(int code, String name) {
        this.name = name;
        this.code = code;
    }


    public static String getDescribeFromValue(int value) {
        for (CPushEvent CPushEvent : values()) {
            if (CPushEvent.code == value) {
                return value + "," + CPushEvent.name;
            }
        }

        return value + ",unknown";
    }
}