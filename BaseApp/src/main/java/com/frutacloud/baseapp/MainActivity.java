package com.frutacloud.baseapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.frutacloud.baseapp.base.BaseActivity;
import com.frutacloud.baseapp.entity.ErrorMsg;
import com.frutacloud.baseapp.entity.UserBean;
import com.frutacloud.baseapp.net.NetLogin;
import com.frutacloud.baseapp.netbase.NetField;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private Button btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mActivity = this;
        initView();
    }

    private void initView() {
        btn_login = (Button) findViewById(R.id.login);

        btn_login.setOnClickListener(this);
    }

    @Override
    protected void requestSuccess(String sign, Bundle bundle) {
        Log.i(TAG, "requestSuccess");
        dismissXloading();
        if (sign == NetField.TEST_LOGIN) {
            UserBean bean = (UserBean) bundle.getSerializable(NetField.RES);
            ErrorMsg error = (ErrorMsg) bundle.getSerializable(NetField.MSG);

            if (bean != null) {
                Log.i(TAG, "返回UserBean");
            }

            if (error != null) {
                Log.i(TAG, "返回errorMsg");
                showToastInfo(error.getErrmsg());
            }
        }
    }

    @Override
    protected void requestFail(String sign, Bundle bundle) {
        showToast("网络请求失败");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                showXLoading("", false);
                NetLogin.getInstance().getData(handle_request, "admin@cloudinward.com", "admin@cloudinward.com");
                break;
        }
    }
}
