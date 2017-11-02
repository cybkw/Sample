package com.frutacloud.baseapp.net;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.frutacloud.baseapp.entity.ErrorMsg;
import com.frutacloud.baseapp.entity.UserBean;
import com.frutacloud.baseapp.netbase.AjaxCallBack;
import com.frutacloud.baseapp.netbase.FastHttp;
import com.frutacloud.baseapp.netbase.NetField;
import com.frutacloud.baseapp.netbase.ResponseEntity;
import com.frutacloud.baseapp.utils.Tools;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;

/**
 * 登录
 *
 * @author ZhangYi 2014-9-10 15:17:21
 */
public class NetLogin {

    private static final String TAG = "NetLogin";

    /**
     * 单例模式
     */
    private static NetLogin INSTANCE;

    /**
     * Gson解析类型
     */
    private Type type = new TypeToken<UserBean>() {
    }.getType();

    private Type errorType = new TypeToken<ErrorMsg>() {
    }.getType();

    public static NetLogin getInstance() {
        if (null == INSTANCE) {
            INSTANCE = new NetLogin();
        }

        return INSTANCE;
    }

    public void getData(final Handler handler, String username, String password) {
        /* 默认数据 */
        /*HashMap<String, String> defData = new HashMap<String, String>();
        defData.put("apiname", NetField_Z.API_NAME);
		defData.put("apikey", NetField_Z.API_KEY);*/

        LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		/* 登录信息 */
        params.put("username", username);
        params.put("password", password);
        //params.put("autoLogin", "false");
        //params.put("apiinfo", defData);

		/* 网络请求 */
        FastHttp.ajax(NetField.TEST_LOGIN, params, new AjaxCallBack() {

            @Override
            public void callBack(ResponseEntity status) {
                UserBean bean = null;
                ErrorMsg errorMsg = null;
                if (status.getStatus() == FastHttp.result_ok) {
                    // 网络访问成功
                    String context = status.getContentAsString();
                    Gson gson = new Gson();
                    Tools.Log("url ====== " + status.getUrl());
                    Tools.Log("json result ========  " + context);
                    try {
                        Log.i(TAG, "GSON解析");
                        errorMsg = gson.fromJson(context, errorType);
                        bean = gson.fromJson(context, type);
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        setHandler(handler, bean, errorMsg, NetField.TEST_LOGIN);
                    }
                } else {
                    // 网络访问失败
                    setHandler(handler, null, errorMsg, NetField.TEST_LOGIN);
                }
            }

            @Override
            public boolean stop() {
                return false;
            }
        });
    }

    /**
     * 数据返回
     *
     * @param handler
     * @param bean
     * @param sign
     * @param errorMsg 请求未能返回正确数据的实体类
     */
    private void setHandler(Handler handler, UserBean bean, ErrorMsg errorMsg, String sign) {
        Message msg = new Message();
        msg.what = 0;
        msg.obj = sign;
        Bundle bundle = new Bundle();
        Log.i(TAG, "Put UserBean");
//        if (null != bean) {
        msg.what = 1;
        bundle.putSerializable(NetField.RES, (Serializable) bean);
//        } else {
        bundle.putSerializable(NetField.MSG, (Serializable) errorMsg);
//        }

        msg.setData(bundle);
        handler.sendMessage(msg);
    }
}
