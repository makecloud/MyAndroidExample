package com.liuyihui.networkcontrol.http;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.liuyihui.mylibrary.util.JsonUtil;
import com.liuyihui.networkcontrol.MyApplication;
import com.liuyihui.networkcontrol.http.entity.PlayerInfo;
import com.liuyihui.networkcontrol.http.retrofitCommon.RetrofitHttpInterface;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 测试htt请求的
 */
public class TestHttpApi {
    private static final String TAG = "TestHttpApi";
    private RetrofitHttpInterface retrofitHttpItf;

    private TestHttpApi() {
        retrofitHttpItf = MyApplication.retrofit.create(RetrofitHttpInterface.class);
    }

    private static class InstanceHolder {
        private static TestHttpApi positionHttpApi = new TestHttpApi();
    }

    public static TestHttpApi getInstance() {
        return InstanceHolder.positionHttpApi;
    }

    public void getPlayerInfo() {
        String url = "http://101.201.30.129:9401/playerApi/play/getMyInfo?mac=67DB0C7C480F16F4";
        retrofitHttpItf.getMyInfo(url)
                .map(apiResponse -> JsonUtil.dataToObject(
                        apiResponse.getData(),
                        PlayerInfo.class))
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG,
                                "call: ",
                                throwable);
                    }
                })
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<PlayerInfo>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(PlayerInfo playerInfo) {
                        Log.d(TAG, "onNext: ");
                    }
                });
    }

    /**
     * 测试双数据源
     */
    public void getPlayerInfoDoubleSource() {
        String url = "http://101.201.30.129:9401/playerApi/play/getMyInfo?mac=67DB0C7C480F16F4";
        Observable<PlayerInfo> httpObserver = retrofitHttpItf.getMyInfo(url)
                .map(apiResponse -> JsonUtil.dataToObject(
                        apiResponse.getData(),
                        PlayerInfo.class))
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG,
                                "call: ",
                                throwable);
                    }
                })
                .subscribeOn(Schedulers.io());
        Observable<PlayerInfo> localObserver = Observable.just(1)
                .map(new Func1<Integer, PlayerInfo>() {
                    @Override
                    public PlayerInfo call(Integer integer) {

                        PlayerInfo playerInfo =
                                new PlayerInfo();
                        playerInfo.setPositionId(33L);
                        return playerInfo;
                    }
                })
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e(TAG, "call: ", throwable);
                    }
                })
                .subscribeOn(Schedulers.io());

        //测试双数据源
        Observable.concatDelayError(httpObserver, localObserver).first()//按顺序执行，取第一个成功的
                .subscribe(new Subscriber<PlayerInfo>() {

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: ", e);
                    }

                    @Override
                    public void onNext(PlayerInfo playerInfo) {
                        Log.d(TAG, "onNext: " + JSON.toJSONString(playerInfo, true));
                    }
                });
    }


}
