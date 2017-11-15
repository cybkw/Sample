package com.frutacloud.rxjavaretrofitsample;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
    private TextView text;
    private Button btn_fy;

    private Button btn_interval;

    private Button btn_request;

    private Button btn_map;

    private Button btn_flatmap;

    private Button btn_concat;

    private Button btn_delay;
    private Button btn_repeatWhen;

    private Button btn_repeatRequest;

    private EditText et_edit;

    private String word = "";
    // 设置变量
    // 可重试次数
    private int maxConnectCount = 10;
    // 当前已重试次数
    private int currentRetryCount = 0;
    // 重试等待时间
    private int waitRetryTime = 0;
    /* 模拟轮询请求次数*/
    private int i = 0;

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
        btn_delay = (Button) findViewById(R.id.btn_delay);
        text = (TextView) findViewById(R.id.text);
        btn_repeatWhen = (Button) findViewById(R.id.btn_repeatWhen);
        btn_repeatRequest = (Button) findViewById(R.id.btn_repeatRequest);

        btn_request.setOnClickListener(this);
        btn_interval.setOnClickListener(this);
        btn_fy.setOnClickListener(this);
        btn_map.setOnClickListener(this);
        btn_flatmap.setOnClickListener(this);
        btn_concat.setOnClickListener(this);
        btn_delay.setOnClickListener(this);
        btn_repeatWhen.setOnClickListener(this);
        btn_repeatRequest.setOnClickListener(this);
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
            case R.id.btn_delay:
                delay();
                break;
            case R.id.btn_repeatWhen:
                repeatWhen();
                break;
            case R.id.btn_repeatRequest:
                repeatRequest();
                break;
        }
    }

    private void repeatRequest() {
        // 步骤1：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RequestAPI.BASE_URL) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        // 步骤2：创建 网络请求接口 的实例
        final RequestAPI request = retrofit.create(RequestAPI.class);

        // 步骤3：采用Observable<...>形式 对 网络请求 进行封装
        Observable<IcibaBean> observable = request.getCall();

        // 步骤4：发送网络请求 & 通过retryWhen（）进行重试
        // 注：主要异常才会回调retryWhen（）进行重试
        observable.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(@NonNull Observable<Throwable> throwableObservable) throws Exception {
                // 参数Observable<Throwable>中的泛型 = 上游操作符抛出的异常，可通过该条件来判断异常的类型
                return throwableObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Throwable throwable) throws Exception {

                        // 输出异常信息
                        Log.d(TAG, "发生异常 = " + throwable.toString());

                        /**
                         * 需求1：根据异常类型选择是否重试
                         * 即，当发生的异常 = 网络异常 = IO异常 才选择重试
                         */
                        if (throwable instanceof IOException) {

                            Log.d(TAG, "属于IO异常，需重试");

                            /**
                             * 需求2：限制重试次数
                             * 即，当已重试次数 < 设置的重试次数，才选择重试
                             */
                            if (currentRetryCount < maxConnectCount) {

                                // 记录重试次数
                                currentRetryCount++;
                                Log.d(TAG, "重试次数 = " + currentRetryCount);

                                /**
                                 * 需求2：实现重试
                                 * 通过返回的Observable发送的事件 = Next事件，从而使得retryWhen（）重订阅，最终实现重试功能
                                 *
                                 * 需求3：延迟1段时间再重试
                                 * 采用delay操作符 = 延迟一段时间发送，以实现重试间隔设置
                                 *
                                 * 需求4：遇到的异常越多，时间越长
                                 * 在delay操作符的等待时间内设置 = 每重试1次，增多延迟重试时间1s
                                 */
                                // 设置等待时间
                                waitRetryTime = 1000 + currentRetryCount * 1000;
                                Log.d(TAG, "等待时间 =" + waitRetryTime);
                                return Observable.just(1).delay(waitRetryTime, TimeUnit.MILLISECONDS);


                            } else {
                                // 若重试次数已 > 设置重试次数，则不重试
                                // 通过发送error来停止重试（可在观察者的onError（）中获取信息）
                                return Observable.error(new Throwable("重试次数已超过设置次数 = " + currentRetryCount + "，即 不再重试"));

                            }
                        }

                        // 若发生的异常不属于I/O异常，则不重试
                        // 通过返回的Observable发送的事件 = Error事件 实现（可在观察者的onError（）中获取信息）
                        else {
                            return Observable.error(new Throwable("发生了非网络异常（非I/O异常）"));
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                .subscribe(new Observer<IcibaBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(IcibaBean result) {
                        // 接收服务器返回的数据
                        Log.d(TAG, "发送成功");
                        Log.i(TAG, result.getContent().toString());
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 获取停止重试的信息
                        Log.d(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void repeatWhen() {
        // 步骤1：创建Retrofit对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(RequestAPI.BASE_URL) // 设置 网络请求 Url
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析(记得加入依赖)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) // 支持RxJava
                .build();

        // 步骤2：创建 网络请求接口 的实例
        RequestAPI request = retrofit.create(RequestAPI.class);

        // 步骤3：采用Observable<...>形式 对 网络请求 进行封装
        Observable<IcibaBean> observable = request.getCall();

        // 步骤4：发送网络请求 & 通过repeatWhen（）进行轮询
        observable.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            // 在Function函数中，必须对输入的 Observable<Object>进行处理，此处使用flatMap操作符接收上游的数据
            public ObservableSource<?> apply(@NonNull Observable<Object> objectObservable) throws Exception {
                // 将原始 Observable 停止发送事件的标识（Complete（） /  Error（））转换成1个 Object 类型数据传递给1个新被观察者（Observable）
                // 以此决定是否重新订阅 & 发送原来的 Observable，即轮询
                // 此处有2种情况：
                // 1. 若返回1个Complete（） /  Error（）事件，则不重新订阅 & 发送原来的 Observable，即轮询结束
                // 2. 若返回其余事件，则重新订阅 & 发送原来的 Observable，即继续轮询
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(@NonNull Object throwable) throws Exception {

                        // 加入判断条件：当轮询次数 = 5次后，就停止轮询
                        if (i > 3) {
                            // 此处选择发送onError事件以结束轮询，因为可触发下游观察者的onError（）方法回调
                            return Observable.error(new Throwable("轮询结束"));
                        }
                        // 若轮询次数＜4次，则发送1Next事件以继续轮询
                        // 注：此处加入了delay操作符，作用 = 延迟一段时间发送（此处设置 = 2s），以实现轮询间间隔设置
                        return Observable.just(1).delay(2000, TimeUnit.MILLISECONDS);
                    }
                });

            }
        }).subscribeOn(Schedulers.io())               // 切换到IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  // 切换回到主线程 处理请求结果
                .subscribe(new Observer<IcibaBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(IcibaBean result) {
                        // e.接收服务器返回的数据
                        Log.i(TAG, result.getContent().toString());
                        i++;
                    }

                    @Override
                    public void onError(Throwable e) {
                        // 获取轮询结束信息
                        Log.d(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete");
                    }
                });

    }


    private void delay() {

        word = et_edit.getText().toString();
        Retrofit ref = new Retrofit.Builder().baseUrl(RequestAPI.BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).build();


        RequestAPI api = ref.create(RequestAPI.class);

        final Observable<TranslationBean> ob1 = api.getCall("fy", "auto", "zh", word);

        ob1.subscribeOn(Schedulers.io()).delay(5, TimeUnit.SECONDS).doOnError(new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.i(TAG, "发生错误时的处理");
            }
        }).retry().observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<TranslationBean>() {
            @Override
            public void accept(TranslationBean translationBean) throws Exception {
                text.setText(translationBean.getContent().toString());
                Log.i(TAG, translationBean.getContent().toString());
            }
        });

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
