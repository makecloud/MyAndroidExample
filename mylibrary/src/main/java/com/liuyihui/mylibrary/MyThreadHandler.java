package com.liuyihui.mylibrary;

import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * 线程handler.
 * 可指定持有的instance，handlerMessage中处理哪个对象的逻辑。
 * 在类实例化后，将实例设置进来
 * <p>
 * <p>
 * 在任意位置声明任意类的handler
 *
 * @param <T> 目标类
 * @author makecloudl
 */
public class MyThreadHandler<T> extends Handler {
    /**
     * 使用handler的类。绑定instance，即可执行此类的逻辑
     */
    private WeakReference<T> targetInstance;

    public interface IMessageProcessor<T> {
        /**
         * 处理消息
         *
         * @param t   要执行的逻辑所在类的实例
         * @param msg
         */
        void processMessage(T t, Message msg);
    }

    private IMessageProcessor iMessageProcessor;

    public MyThreadHandler(IMessageProcessor<T> iMessageProcessor) {
        super();
        this.iMessageProcessor = iMessageProcessor;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (iMessageProcessor != null) {
            iMessageProcessor.processMessage(targetInstance, msg);
        }
    }

    /**
     * 设置持有的实例
     *
     * @param t
     */
    public void setTargetInstance(T t) {
        this.targetInstance = new WeakReference<>(t);
    }
}
