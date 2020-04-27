package com.liuyihui.testroot.netinfo;

/**
 * Created by gaowen on 2017/8/11.
 */

public class NetWork {

    private boolean isEnable;
    private int type;
    private String ipv4;
    private String ipv6;
    private int signal;
    private String mac;
    private String broadCastIp;

    public boolean getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(boolean enable) {
        isEnable = enable;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getIpv4() {
        return ipv4;
    }

    public void setIpv4(String ipv4) {
        this.ipv4 = ipv4;
    }

    public String getIpv6() {
        return ipv6;
    }

    public void setIpv6(String ipv6) {
        this.ipv6 = ipv6;
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getBroadCastIp() {
        return broadCastIp;
    }

    public void setBroadCastIp(String broadCastIp) {
        this.broadCastIp = broadCastIp;
    }
}
