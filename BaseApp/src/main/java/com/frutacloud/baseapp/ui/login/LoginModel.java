package com.frutacloud.baseapp.ui.login;

import android.content.Context;
import android.util.Log;

import com.frutacloud.baseapp.base.OnHttpCallBack;
import com.frutacloud.baseapp.entity.UserBean;
import com.frutacloud.baseapp.http.NetField;
import com.frutacloud.baseapp.http.RetrofitUtils;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;

import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 业务逻辑处理主要层
 */

public class LoginModel implements LoginContract.ILoginModel {


    private static final String TAG = "LoginModel";

    @Override
    public void login(UserBean userBean, final OnHttpCallBack<UserBean> callBack) {

        RetrofitUtils.newInstence(NetField.HOST)
                .create(LoginNetServiceApi.class)
                .login(userBean.getUsername(), userBean.getPassword())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UserBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UserBean value) {
                        callBack.onSuccessful(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        //失败的时候调用-----以下可以忽略 直接 callBack.onFaild("请求失败");
                        if (e instanceof HttpException) {
                            HttpException httpException = (HttpException) e;
                            //httpException.response().errorBody().string()
                            int code = httpException.code();
                            if (code == 500 || code == 404) {
                                callBack.onFaild("服务器出错");
                            }
                        } else if (e instanceof ConnectException) {
                            callBack.onFaild("网络断开,请打开网络!");
                        } else if (e instanceof SocketTimeoutException) {
                            callBack.onFaild("网络连接超时!!");
                        } else {
                            callBack.onFaild("发生未知错误" + e.getMessage());
                        }
                    }

                    @Override
                    public void onComplete() {
                        Log.i(TAG, "操作完成");
                    }
                });
    }


    @Override
    public void regit(UserBean userBean, OnHttpCallBack<UserBean> callBack) {

    }

    @Override
    public void saveUserInfo(Context context, UserBean bean) {
        context.getSharedPreferences("userinfo", Context.MODE_PRIVATE).edit()
                .putString("uname", bean.getUsername())
                .putString("pword", bean.getPassword())
                .commit();
    }
}
