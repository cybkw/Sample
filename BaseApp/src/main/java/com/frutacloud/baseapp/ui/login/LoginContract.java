package com.frutacloud.baseapp.ui.login;

import android.content.Context;

import com.frutacloud.baseapp.base.OnHttpCallBack;
import com.frutacloud.baseapp.entity.UserBean;

/**
 * 登录关联类
 */

public class LoginContract {

    /**
     * 视图层
     */
    interface ILoginView {
        Context getContext();  //上下文对象

        UserBean getUserBean();  //实体对象

        void showProgress();  //显示加载框

        void dismissProgress(); //隐藏加载框

        void showInfo(String msg); //显示提示显示

        void showError(String msg); //显示错误信息

        void toMain(); //跳转界面
    }

    /**
     * 视图与业务逻辑连接层
     */

    interface ILoginPresenter {
        void login();  //登录
    }

    /**
     * 逻辑处理层
     * (主要的方法)
     */
    interface ILoginModel {
        void login(UserBean userBean, OnHttpCallBack<UserBean> callBack);  //登录请求

        void regit(UserBean userBean, OnHttpCallBack<UserBean> callBack);  //注册

        void saveUserInfo(Context context, UserBean bean);  //保存User信息

    }
}
