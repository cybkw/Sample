package com.frutacloud.baseapp.http;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retofit网络请求工具类
 * Retrofit使用注意事项，baseurl要以“/”结束 在Service中Get("---")/Post("----")不能以“/”开始
 */
public class RetrofitUtils {
    private static final int READ_TIMEOUT = 60;//读取超时时间,单位  秒
    private static final int CONN_TIMEOUT = 12;//连接超时时间,单位  秒
    private static Retrofit mRetrofit;

    private RetrofitUtils() {
    }

    public static Retrofit newInstence(String baseUrl) {
        mRetrofit = null;
        OkHttpClient client = new OkHttpClient();//初始化一个client,不然retrofit会自己默认添加一个
        client.newBuilder().readTimeout(READ_TIMEOUT, TimeUnit.MINUTES);//设置读取时间为一分钟
        client.newBuilder().connectTimeout(CONN_TIMEOUT, TimeUnit.SECONDS);//设置连接时间为12s
        mRetrofit = new Retrofit.Builder()
                .client(client)//添加一个client,不然retrofit会自己默认添加一个
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return mRetrofit;
    }
}
