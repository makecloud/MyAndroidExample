package com.liuyihui.networkcontrol.http.retrofitCommon;


import android.util.Log;

import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;

import retrofit2.HttpException;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;


/**
 * Created by gaowen on 2017/7/17.
 */

public class RetryWhenNetworkException implements Func1<Observable<? extends Throwable>,
        Observable<?>> {

    private static final String TAG = "RetryWhenNetwrkEcption";

    private int retryCount = 3;
    private long delay = 3000;
    private long increaseDelay = 3000;

    public RetryWhenNetworkException() {
    }

    public RetryWhenNetworkException(int retryCount, long delay) {
        this.retryCount = retryCount;
        this.delay = delay;
    }

    public RetryWhenNetworkException(int retryCount, long delay, long increaseDelay) {
        this.retryCount = retryCount;
        this.delay = delay;
        this.increaseDelay = increaseDelay;
    }

    @Override
    public Observable<?> call(Observable<? extends Throwable> observable) {
        return observable.zipWith(Observable.range(1, retryCount + 1),
                                  new Func2<Throwable, Integer, Wrapper>() {
                                      @Override
                                      public Wrapper call(Throwable throwable, Integer integer) {
                                          return new Wrapper(throwable, integer);
                                      }
                                  }).flatMap(new Func1<Wrapper, Observable<?>>() {
            @Override
            public Observable<?> call(Wrapper wrapper) {
                Log.d(TAG, wrapper.throwable.getMessage() + "：" + retryCount);
                if (!(wrapper.throwable instanceof HttpException || wrapper.throwable instanceof NoSuchElementException) && wrapper.index < retryCount + 1) { //如果超出重试次数也抛出错误，否则默认是会进入onCompleted
                    return Observable.timer(delay + (wrapper.index - 1) * increaseDelay,
                                            TimeUnit.MILLISECONDS);
                }
                return Observable.error(wrapper.throwable);
            }
        });
    }

    private static class Wrapper {
        private int index;
        private Throwable throwable;

        public Wrapper(Throwable throwable, int index) {
            this.index = index;
            this.throwable = throwable;
        }
    }
}
