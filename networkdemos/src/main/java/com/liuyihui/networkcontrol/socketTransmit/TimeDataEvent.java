package com.liuyihui.networkcontrol.socketTransmit;

/**
 * 时间相关事件
 */
public class TimeDataEvent {
    public final static int TYPE_SEND = 1;
    public final static int TYPE_RECEIVE = 2;


    public int EventType;
    public Long timeData;
    public int setTimeSuccess;

    public TimeDataEvent(int eventType, Long timeData, int setTimeSuccess) {
        EventType = eventType;
        this.timeData = timeData;
        this.setTimeSuccess = setTimeSuccess;
    }
}
