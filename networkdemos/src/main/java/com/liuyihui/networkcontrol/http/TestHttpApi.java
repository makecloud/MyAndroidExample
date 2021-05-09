package com.liuyihui.networkcontrol.http;

import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.liuyihui.mylibrary.util.JsonUtil;
import com.liuyihui.networkcontrol.BuildConfig;
import com.liuyihui.networkcontrol.MyApplication;
import com.liuyihui.networkcontrol.http.entity.PlayerInfo;
import com.liuyihui.networkcontrol.http.retrofitCommon.MyGsonConverterFactory;
import com.liuyihui.networkcontrol.http.retrofitCommon.NetInterceptor;
import com.liuyihui.networkcontrol.http.retrofitCommon.RetrofitHttpInterface;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLReaderAdapter;
import org.xml.sax.helpers.XMLReaderFactory;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.StringReader;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 测试htt请求的
 */
public class TestHttpApi {
    private static final String TAG = "TestHttpApi";
    private static Retrofit retrofit;
    private RetrofitHttpInterface retrofitHttpItf;
    private OkHttpClient okHttpClient;

    private TestHttpApi() {
        //定义okHttp日志拦截器
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY :
                                                HttpLoggingInterceptor.Level.NONE);

        //自定义OkHttpClient
        okHttpClient = new OkHttpClient.Builder().addInterceptor(httpLoggingInterceptor)
                                                 .connectTimeout(30, TimeUnit.SECONDS)
                                                 .readTimeout(30, TimeUnit.SECONDS)
                                                 .writeTimeout(30, TimeUnit.SECONDS)
                                                 //.addNetworkInterceptor(new NetInterceptor())
                                                 .addInterceptor(new NetInterceptor())
                                                 .build();


        //创建retrofit实例
        retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL + "/")
                                         .client(okHttpClient)
                                         .addConverterFactory(MyGsonConverterFactory.create())
                                         .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                         .build();

        //创建接口实例
        retrofitHttpItf = retrofit.create(RetrofitHttpInterface.class);
    }

    private static class InstanceHolder {
        private static TestHttpApi positionHttpApi = new TestHttpApi();
    }

    public static TestHttpApi getInstance() {
        return InstanceHolder.positionHttpApi;
    }

    /**
     * 动态url
     * <p>
     * map操作符
     * <p>
     * doOn操作符
     */
    public void getPlayerInfo() {
        String url = "http://101.201.30.129:9401/playerApi/play/getMyInfo?mac=67DB0C7C480F16F4";
        retrofitHttpItf.getMyInfo(url)
                       .map(apiResponse -> JsonUtil.dataToObject(apiResponse.getData(), PlayerInfo.class))
                       .doOnError(new Action1<Throwable>() {
                           @Override
                           public void call(Throwable throwable) {
                               Log.e(TAG, "call: ", throwable);
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
                                                             .map(apiResponse -> JsonUtil.dataToObject(apiResponse.getData(),
                                                                                                       PlayerInfo.class))
                                                             .doOnError(new Action1<Throwable>() {
                                                                 @Override
                                                                 public void call(Throwable throwable) {
                                                                     Log.e(TAG, "call: ", throwable);
                                                                 }
                                                             })
                                                             .subscribeOn(Schedulers.io());
        Observable<PlayerInfo> localObserver = Observable.just(1).map(new Func1<Integer, PlayerInfo>() {
            @Override
            public PlayerInfo call(Integer integer) {

                PlayerInfo playerInfo = new PlayerInfo();
                playerInfo.setPositionId(33L);
                return playerInfo;
            }
        }).doOnError(new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.e(TAG, "call: ", throwable);
            }
        }).subscribeOn(Schedulers.io());

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


    /**
     * post，formUrlEncode，path，动态url
     */
    public void getTgt() {

        //创建retrofit实例
        Retrofit retrofit = new Retrofit.Builder().baseUrl(BuildConfig.BASE_URL + "/")
                                                  .client(okHttpClient)
                                                  .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                                                  .build();

        //创建接口实例
        RetrofitHttpInterface itf = retrofit.create(RetrofitHttpInterface.class);

        String url = "https://itsdev.fiberhome.com/cas-fiberhome/v1/tickets";
        String username = "qzhang105";
        String password = "Qwer1234!@#$";
        itf.getTGT(url, username, password).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                String getSTUrl = null;
                try {
                    String resultText = response.body().string();
                    Log.d(TAG, "onResponse: " + resultText);
                    int startIndex = resultText.indexOf("<html>");
                    int endIndex = resultText.indexOf("</html>") + 7;
                    String htmlContent = resultText.substring(startIndex, endIndex);

                    XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                    factory.setNamespaceAware(true);
                    factory.setValidating(false);
                    XmlPullParser xpp = factory.newPullParser();
                    xpp.setInput(new StringReader(htmlContent));
                    int eventType = xpp.getEventType();
                    while (eventType != XmlPullParser.END_DOCUMENT) {
                        if (eventType == XmlPullParser.START_DOCUMENT) {
                            System.out.println("Start document");
                        } else if (eventType == XmlPullParser.START_TAG) {
                            System.out.println("Start tag " + xpp.getName());
                            if ("form".equalsIgnoreCase(xpp.getName())) {
                                getSTUrl = xpp.getAttributeValue("", "action");
                                Log.d(TAG, "onResponse: " + getSTUrl);
                            }
                        } else if (eventType == XmlPullParser.END_TAG) {
                            System.out.println("End tag " + xpp.getName());
                        } else if (eventType == XmlPullParser.TEXT) {
                            System.out.println("Text " + xpp.getText());
                        }
                        eventType = xpp.next();
                    }
                    System.out.println("End document");

                } catch (IOException | XmlPullParserException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });
    }

    /**
     * post，formUrlEncode，path，动态url
     */
    public void getST() {
        String url = "";//获取TGT时返回这个url，这个url包含TGT参数
        String service = "http://10.7.102.49/tempo-bi-runtime/publish/show/a1823a75fac64511948ba82f921ef7bb" +
                "/5eb5273575fa49899ab19c46fb166dfa";
        retrofitHttpItf.getST(url, service)
                       .subscribeOn(Schedulers.io())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribe(new Subscriber<String>() {
                           @Override
                           public void onCompleted() {

                           }

                           @Override
                           public void onError(Throwable e) {
                               Log.e(TAG, "onError: ", e);
                           }

                           @Override
                           public void onNext(String s) {
                               Log.d(TAG, "onNext: " + s);
                           }
                       });
    }


}
