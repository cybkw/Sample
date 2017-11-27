package com.frutacloud.baseapp.ui.login;

import com.frutacloud.baseapp.base.OnHttpCallBack;
import com.frutacloud.baseapp.entity.UserBean;
import com.frutacloud.baseapp.ui.login.LoginContract.ILoginPresenter;

/**
 * 视图与业务逻辑连接层
 */

public class LoginPesenter implements ILoginPresenter {

    private static final String TAG = "LoginPesenter";
    private LoginContract.ILoginView mLoginView;
    private LoginContract.ILoginModel mLoginModel;

    public LoginPesenter(LoginContract.ILoginView mLoginView) {
        this.mLoginView = mLoginView;
        mLoginModel = new LoginModel();
    }

    @Override
    public void login() {
        mLoginView.showProgress();
        mLoginModel.login(mLoginView.getUserBean(), new OnHttpCallBack<UserBean>() {
            @Override
            public void onSuccessful(UserBean responseBody) {
                mLoginView.dismissProgress();
                if (responseBody.getErrmsg() == null) {
                    mLoginView.showInfo("登录成功");
                } else {
                    mLoginView.showError(responseBody.getErrmsg());
                }
            }

            @Override
            public void onFaild(String errorMsg) {
                mLoginView.dismissProgress();
                mLoginView.showError(errorMsg);
            }
        });
    }
}
