package com.frutacloud.baseapp.ui.login;

import com.frutacloud.baseapp.entity.UserBean;
import com.frutacloud.baseapp.http.NetField;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * 登录网络操作类
 */

public interface LoginNetServiceApi {

    /**
     * 登录请求
     */
    @POST(NetField.TEST_LOGIN)
    @FormUrlEncoded
    Observable<UserBean> login(@Field("username") String username, @Field("password") String password);
}
