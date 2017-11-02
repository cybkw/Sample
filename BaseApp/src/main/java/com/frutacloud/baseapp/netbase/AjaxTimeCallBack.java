package com.frutacloud.baseapp.netbase;

/**
 * 异步请求回调类
 *
 * @author gdpancheng@gmail.com 2012-12-9 下午11:44:42
 */
public interface AjaxTimeCallBack extends CallBack {

    /**
     * 如果返回为true 则轮询 如果设为false则取消
     *
     * @return boolean
     * @author gdpancheng@gmail.com 2013-8-18 下午10:06:14
     */
    public abstract boolean getIsContinue();
}
