package com.frutacloud.baseapp.netbase;

/**
 * 网络请求全局变量
 *
 * @author ZhangYi 2014年7月1日 下午7:48:39
 */
public class NetField {
    public static final String RES = "r";  //返回结果标识
    public static final String MSG = "m";  //返回消息标识

    /**
     * 服务器地址: 生产环境
     */
    public static final String HOST = "http://etdemo.cloudinward.com";

    /**
     * 登录接口
     */
    public static final String TEST_LOGIN = HOST + "/ws/token/login";

    /**
     * 获取包装码 get /ws/packagings/:id
     */
    public static final String GET_PACKINGCODE = HOST + "/ws/packagings/";

}
