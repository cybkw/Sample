package com.frutacloud.rxjavaretrofitsample;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/11/13.
 * 网络API
 */

public interface RequestAPI {

    public static String BASE_URL = "http://fy.iciba.com/";

    public static String FY_URL = BASE_URL + "ajax.php";
    public static String HOST = "http://etdemo.cloudinward.com";
    public static String LOGIN = HOST + "/ws/token/login";
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // 采用Observable<...>接口
    // getCall()是接受网络请求数据的方法

    @GET(FY_URL)
    Observable<TranslationBean> getCall(@Query("a") String fy, @Query("f") String auto, @Query("t") String t, @Query("w") String word);

    // 登录 Post /ws/token/login

    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<IcibaBean> getCall();

    @POST(LOGIN)
    @FormUrlEncoded
    Observable<ResponseBody> getPost(@Field("username") String uname, @Field("password") String pword);
}
