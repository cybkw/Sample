package com.frutacloud.baseapp.ui.login;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.frutacloud.baseapp.R;
import com.frutacloud.baseapp.base.BaseAc;
import com.frutacloud.baseapp.entity.UserBean;
import com.frutacloud.baseapp.utils.Tools;

/**
 * 登录
 * Created by Administrator on 2017/11/27.
 */

public class LoginActivity extends BaseAc implements LoginContract.ILoginView, View.OnClickListener {

    private Button btn_login;

    private EditText et_uname;

    private EditText et_pword;


    private String mName = "";

    private String mPword = "";

    private LoginPesenter loginPesenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        loginPesenter = new LoginPesenter(this);
    }

    private void initView() {
        btn_login = (Button) findViewById(R.id.btn_login);
        et_uname = (EditText) findViewById(R.id.uname);
        et_pword = (EditText) findViewById(R.id.pword);

        btn_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                mName = et_uname.getText().toString().trim();
                mPword = et_pword.getText().toString().trim();
                if (!Tools.isNull(mName, mPword)) {
                    loginPesenter.login();
                } else {
                    showInfo("请填写账号与密码");
                }
                break;
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public UserBean getUserBean() {
        return new UserBean(mName, mPword);
    }

    @Override
    public void showProgress() {
        showXLoading("", false);
    }

    @Override
    public void dismissProgress() {
        dismissXloading();
    }

    @Override
    public void showInfo(String msg) {
        showToastInfo(msg);
    }

    @Override
    public void showError(String msg) {
        showToastError(msg);
    }

    @Override
    public void toMain() {

    }
}
