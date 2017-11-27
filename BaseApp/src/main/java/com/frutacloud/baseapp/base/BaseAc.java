package com.frutacloud.baseapp.base;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.android.pc.ioc.inject.InjectInit;
import com.frutacloud.baseapp.utils.Tools;
import com.frutacloud.baseapp.weight.XLoadingDialog;
import com.frutacloud.baseapp.weight.XToast;

/**
 * 基类Activity
 * Created by Administrator on 2017/11/27.
 */

public class BaseAc extends AppCompatActivity {
    /* 上下文*/
    protected Activity mActivity;


    @InjectInit
    void init() {

        mActivity = this;
    }

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


}
