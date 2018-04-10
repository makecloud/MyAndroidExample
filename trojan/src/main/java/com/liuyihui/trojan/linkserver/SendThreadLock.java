package com.liuyihui.trojan.linkserver;

/**
 * Created by liuyi on 2018/1/25.
 */

public class SendThreadLock {
    private Integer msg;

    SendThreadLock() {
    }

    /**
     *
     */
    public void notifyAllWithMsg(int msg) {
        this.msg = msg;
        this.notifyAll();
    }

    /**
     * 取消息
     *
     * @return
     */
    public Integer pollMsg() {
        Integer tmpMsg = this.msg;
        this.msg = null;
        return tmpMsg;
    }

}
