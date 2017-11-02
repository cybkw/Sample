package com.frutacloud.baseapp.netbase;

/**
 * 异步请求回调类
 *
 * @author gdpancheng@gmail.com 2012-12-9 下午11:44:42
 */
public interface AjaxCallBack extends CallBack {

    /**
     * 可以用来取消回调
     *
     * @return boolean
     * @author gdpancheng@gmail.com 2013-8-18 下午10:07:31
     */
    public abstract boolean stop();

}
