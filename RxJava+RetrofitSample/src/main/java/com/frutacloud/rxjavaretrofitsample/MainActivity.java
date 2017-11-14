package com.frutacloud.rxjavaretrofitsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.IOException;
import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button btn_fy;

    private Button btn_interval;

    private Button btn_request;

    private Button btn_map;

    private Button btn_flatmap;

    private Button btn_concat;

    private EditText et_edit;

    private String word = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        btn_fy = (Button) findViewById(R.id.btn_fy);
        btn_interval = (Button) findViewById(R.id.btn_interval);
        btn_request = (Button) findViewById(R.id.btn_request);
        et_edit = (EditText) findViewById(R.id.et_edit);
        btn_map = (Button) findViewById(R.id.btn_map);
        btn_flatmap = (Button) findViewById(R.id.btn_flatmap);
        btn_concat = (Button) findViewById(R.id.btn_concat);

        btn_request.setOnClickListener(this);
        btn_interval.setOnClickListener(this);
        btn_fy.setOnClickListener(this);
        btn_map.setOnClickListener(this);
        btn_flatmap.setOnClickListener(this);
        btn_concat.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_fy:
                translation();
                break;
            case R.id.btn_interval:
                interval();
                break;
            case R.id.btn_request:

                postRequest();
                break;
            case R.id.btn_map:
                map();
                break;
            case R.id.btn_flatmap:
                faltMap();
                break;
            case R.id.btn_concat:
                concatObsevable();
                break;
        }
    }

    private void concatObsevable() {

        String[] strs = new String[]{"kw", "cy", "uu"};
        int[] ints = new int[]{1, 2, 3, 4, 5, 6};
        Observable.concat(Observable.just(1, 2, 3, 4), Observable.just("ss", "www", "cy")).subscribe(new Observer<Serializable>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Serializable value) {
                Log.i(TAG, value.toString());
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    private void faltMap() {

        word = et_edit.getText().toString();

        Observable<TranslationBean> ob1;
        final Observable<IcibaBean> ob2;

        Retrofit ref = new Retrofit.Builder().baseUrl(RequestAPI.BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create()).client(getOkHttpClient()).build();

        RequestAPI api = ref.create(RequestAPI.class);

        ob1 = api.getCall("fy", "auto", "auto", word);
        ob2 = api.getCall();

        ob1.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<TranslationBean>() {
                    @Override
                    public void accept(TranslationBean icibaBean) throws Exception {

                        Log.i(TAG, "第一次请求" + icibaBean.getContent().getWord_mean().toString());
                    }
                })
                .observeOn(Schedulers.newThread())
                .flatMap(new Function<TranslationBean, ObservableSource<IcibaBean>>() {

                    @Override
                    public ObservableSource<IcibaBean> apply(TranslationBean icibaBean) throws Exception {
                        return ob2;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<IcibaBean>() {
                    @Override
                    public void accept(IcibaBean icibaBean) throws Exception {
                        Log.i(TAG, "第2次请求" + icibaBean.getContent().toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.i(TAG, "请求失败");
                    }
                });
    }

    private void map() {

        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                e.onNext("1");
                e.onNext("2");
                e.onNext("3");
                e.onComplete();
            }
        }).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return Integer.parseInt(s);
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                Log.i(TAG, "" + integer);
            }
        });
    }

    private void postRequest() {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RequestAPI.HOST)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RequestAPI api = retrofit.create(RequestAPI.class);

        Observable<ResponseBody> observable = api.getPost("admin", "123456");

        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "建立连接");
                    }

                    @Override
                    public void onNext(ResponseBody value) {
                        try {
                            String str = new String(value.bytes());
                            Log.i(TAG, str);

                            if (word != null && !word.equals("")) {
                                translation();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete");
                    }
                });


    }

    private void interval() {
        /*
         * 步骤1：采用interval（）延迟发送
         * 注：此处主要展示无限次轮询，若要实现有限次轮询，仅需将interval（）改成intervalRange（）即可
         **/
        Observable.interval(2, 1, TimeUnit.SECONDS)
                // 参数说明：
                // 参数1 = 第1次延迟时间；
                // 参数2 = 间隔时间数字；
                // 参数3 = 时间单位；
                // 该例子发送的事件特点：延迟2s后发送事件，每隔1秒产生1个数字（从0开始递增1，无限个）

                 /*
                  * 步骤2：每次发送数字前发送1次网络请求（doOnNext（）在执行Next事件前调用）
                  * 即每隔1秒产生1个数字前，就发送1次网络请求，从而实现轮询需求
                  **/
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long integer) throws Exception {
                        Log.d(TAG, "第 " + integer + " 次轮询");

                 /*
                  * 步骤3：通过Retrofit发送网络请求
                  **/
                        // a. 创建Retrofit对象
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("http://fy.iciba.com/") // 设置 网络请求 Url
                                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                                .build();

                        // b. 创建 网络请求接口 的实例
                        RequestAPI request = retrofit.create(RequestAPI.class);

                        // c. 采用Observable<...>形式 对 网络请求 进行封装
                        Observable<IcibaBean> observable = request.getCall();
                        // d. 通过线程切换发送网络请求
                        observable.subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                                .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                                .subscribe(new Observer<IcibaBean>() {

                                    @Override
                                    public void onSubscribe(Disposable d) {
                                    }

                                    @Override
                                    public void onNext(IcibaBean result) {
                                        // e.接收服务器返回的数据
                                        Log.i(TAG, result.getContent().toString());
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        Log.d(TAG, "请求失败");
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });

                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long value) {

            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG, "对Error事件作出响应");
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "对Complete事件作出响应");
            }
        });


    }

    private void translation() {
        word = et_edit.getText().toString();

        //构建Retrofit
        Retrofit retrofit = new Retrofit.Builder().baseUrl(RequestAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        RequestAPI api = retrofit.create(RequestAPI.class);
        Observable<TranslationBean> observabaleApi = api.getCall("fy", "auto", "zh", word);


        observabaleApi.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TranslationBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.i(TAG, "观察者与被观察者建立连接");
                    }

                    @Override
                    public void onNext(TranslationBean value) {
                        Log.i(TAG, value.getContent().getWord_mean().toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i(TAG, "onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "onComplete");
                    }
                });

    }

    private OkHttpClient getOkHttpClient() {
        //日志显示级别
        HttpLoggingInterceptor.Level level = HttpLoggingInterceptor.Level.BODY;
        //新建log拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(TAG, "OkHttp====Message:" + message);
            }
        });
        loggingInterceptor.setLevel(level);

        //定制OkHttp
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient
                .Builder();
        //OkHttp进行添加拦截器loggingInterceptor
        httpClientBuilder.addInterceptor(loggingInterceptor);
        return httpClientBuilder.build();
    }
}
