package com.frutacloud.retrofitdemo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2017/11/10.
 */

public interface ICabcAPI {

    // URL模板
    public static final String URL = "http://fy.iciba.com/";

    // URL实例
    String GET_URL = URL + "ajax.php";

// 参数说明：
// a：固定值 fy
// f：原文内容类型，日语取 ja，中文取 zh，英语取 en，韩语取 ko，德语取 de，西班牙语取 es，法语取 fr，自动则取 auto
// t：译文内容类型，日语取 ja，中文取 zh，英语取 en，韩语取 ko，德语取 de，西班牙语取 es，法语取 fr，自动则取 auto
// w：查询内容

    String a = "fy";
    String f = "auto";
    String t = "zh";
    /**
     * 服务器地址: 生产环境
     */
    public static final String HOST = "http://etdemo.cloudinward.com";
    /**
     * 登录 Post /ws/token/login
     */
    public static final String LOGIN = HOST + "/ws/token/login";

    /**
     * @Query和@QueryMap 作用：用于 @GET 方法的查询参数（Query = Url 中 ‘?’ 后面的 key-value）
     * <p>
     * 如：url = http://www.println.net/?cate=android，其中，Query = cate
     */
    @GET(GET_URL)
    Call<WordBean> getCall(@Query("a") String a, @Query("f") String f, @Query("t") String t, @Query("w") String word);
    // 注解里传入 网络请求 的部分URL地址
    // Retrofit把网络请求的URL分成了两部分：一部分放在Retrofit对象里，另一部分放在网络请求接口里
    // 如果接口里的url是一个完整的网址，那么放在Retrofit对象里的URL可以忽略
    // getCall()是接受网络请求数据的方法

    /**
     * method：网络请求的方法（区分大小写）
     * path：网络请求地址路径
     * hasBody：是否有请求体
     * // {id} 表示是一个变量
     * // method 的值 retrofit 不会做处理，所以要自行保证准确
     */
    @HTTP(method = "GET", path = "ajax.php?a=fy&f=auto&t=zh&w={word}", hasBody = false)
    Call<WordBean> getCallHttp(@Path("word") String id);

    /**
     * @FormUrlEncoded 代表参数以HTML表单方式提交
     * @Field 参数
     * @FieldMap // 实现的效果与Field相同，但要传入Map
     * Map<String, Object> map = new HashMap<>();
     * map.put("username", "Carson");
     * map.put("password", 24);
     * 具体使用：与 @FormUrlEncoded 注解配合使用
     * @Part & @PartMap
     * <p>
     * 作用：发送 Post请求 时提交请求的表单字段
     * <p>
     * 与@Field的区别：功能相同，但携带的参数类型更加丰富，包括数据流，所以适用于 有文件上传 的场景
     * 具体使用：与 @Multipart 注解配合使用
     */
    @POST(LOGIN)
    @FormUrlEncoded
    Call<ResponseBody> postCall(@Field("username") String uname, @Field("password") String pword);
}
