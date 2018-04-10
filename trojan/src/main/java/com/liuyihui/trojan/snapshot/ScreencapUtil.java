package com.liuyihui.trojan.snapshot;

import com.liuyihui.trojan.shell.ShellUtils;

/**
 * Created by liuyi on 2018/1/22.
 */

public class ScreencapUtil {
    public void getTiaoyitiaoScreencap(){
        ShellUtils.execCommand("screencap -p tiaoyitiao.png",false);
    }
}
