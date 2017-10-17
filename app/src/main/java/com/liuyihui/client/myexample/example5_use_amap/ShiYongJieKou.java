package com.liuyihui.client.myexample.example5_use_amap;

/**
 * Created by liuyh on 2016/12/26.
 */

public class ShiYongJieKou {
    private interf m;

    //使用作为类属性的接口
    public void amethod() {
        //调用接口方法，传给接口方法参数，具体接口方法逻辑将来在外部去实现。实现解耦
        m.activate(5, "mm");
    }

    //使用作为参数的接口
    public void bmethod(interf m) {
        m.deactivate();
    }

    void setM(interf m) {
        this.m = m;
    }

}

/**
 * 定义接口
 */
interface interf {
    public void activate(int i, String s);

    public void deactivate();
}
