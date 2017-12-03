package com.frutacloud.socketsample;

/**
 * Created by Administrator on 2017/12/1.
 */

public interface ClientCallBack<T> {

    void onSuccesful(T t);

    void onFaild(String st);
}
