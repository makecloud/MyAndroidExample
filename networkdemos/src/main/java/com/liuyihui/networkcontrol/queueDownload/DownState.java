package com.liuyihui.networkcontrol.queueDownload;

/**
 * 下载状态管理
 * Created by gaowen on 2017/7/12.
 */

public enum DownState {
    START(0),
    DOWN(1),
    PAUSE(2),
    STOP(3),
    ERROR(4),
    FINISH(5);
    private int state;

    public int getState() {
        return state;
    }
    DownState(int state) {
        this.state = state;
    }
}
