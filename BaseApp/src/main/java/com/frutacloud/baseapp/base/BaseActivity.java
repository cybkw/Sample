package com.frutacloud.baseapp.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.android.pc.ioc.inject.InjectInit;
import com.frutacloud.baseapp.netbase.NetField;
import com.frutacloud.baseapp.utils.Tools;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/11/2.
 */

public abstract class BaseActivity extends AppCompatActivity {
    protected Activity mActivity;
    /**
     * Handler处理
     */
    protected HandlerRequest handle_request = new HandlerRequest(mActivity);

    @InjectInit
    void init() {
        mActivity = this;
    }

    /**
     * Toast提示
     */
    protected void showToast(String content) {
        Tools.Toast(this, content);
    }

    /**
     * 网络请求成功
     *
     * @param sign   签名
     * @param bundle 内容
     */
    protected abstract void requestSuccess(String sign, Bundle bundle);

    /**
     * 网络请求失败
     *
     * @param sign   签名
     * @param bundle 内容
     */
    protected abstract void requestFail(String sign, Bundle bundle);

    protected class HandlerRequest extends Handler {
        WeakReference<Activity> reference;

        public HandlerRequest(Activity main) {
            reference = new WeakReference<Activity>(main);
        }

        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                requestSuccess((String) msg.obj, msg.getData());
            } else {
                Bundle bundle = msg.getData();
                if (bundle.containsKey(NetField.MSG)) {
                    showToast(bundle.getString(NetField.MSG)); // 显示后台返回错误内容
                }
                requestFail((String) msg.obj, bundle);
            }
            System.setProperty("http.keepAlive", "false");
        }
    }

}
