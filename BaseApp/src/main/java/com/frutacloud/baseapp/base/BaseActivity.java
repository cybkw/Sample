package com.frutacloud.baseapp.base;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;

import com.android.pc.ioc.inject.InjectInit;
import com.frutacloud.baseapp.netbase.NetField;
import com.frutacloud.baseapp.utils.Tools;
import com.frutacloud.baseapp.weight.XLoadingDialog;
import com.frutacloud.baseapp.weight.XToast;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2017/11/2.
 */

public abstract class BaseActivity extends AppCompatActivity {

    /* 上下文*/
    protected Activity mActivity;

    /* Handler处理*/
    protected HandlerRequest handle_request = new HandlerRequest(mActivity);

    @InjectInit
    void init() {
        mActivity = this;
    }

    /**
     * 网络请求失败
     *
     * @param sign   签名
     * @param bundle 内容
     */
    protected abstract void requestFail(String sign, Bundle bundle);

    /**
     * 网络请求成功
     *
     * @param sign   签名
     * @param bundle 内容
     */
    protected abstract void requestSuccess(String sign, Bundle bundle);

    /**
     * Toast提示错误
     */
    protected void showToastError(String msg) {
        XToast.error(msg);
    }

    /**
     * Toast提示正确
     */
    protected void showToastSuccess(String msg) {
        XToast.success(msg);
    }

    /**
     * Toast提示信息
     */
    protected void showToastInfo(String msg) {
        XToast.info(msg);
    }

    /**
     * Toast提示警告
     */
    protected void showToastWarning(String msg) {
        XToast.warning(msg);
    }

    /**
     * Toast 正常显示
     */
    protected void showToast(String msg) {
        XToast.normal(msg);
    }

    /**
     * @param message(显示内容)
     * @param isCancel(点击是否可取消) 显示加载框 点击不可取消的loading
     */
    protected void showXLoading(String message, boolean isCancel) {
        if (Tools.isNull(message)) {
            XLoadingDialog.with(this).setCanceled(isCancel).show();
        } else {
            XLoadingDialog.with(this).setCanceled(isCancel).setMessage(message).show();
        }
    }

    /**
     * 移除加载框
     */
    protected void dismissXloading() {
        XLoadingDialog.with(this).dismiss();
    }

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
