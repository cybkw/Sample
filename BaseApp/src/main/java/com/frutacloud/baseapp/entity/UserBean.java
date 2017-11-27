package com.frutacloud.baseapp.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/2.
 * 实体类
 */

public class UserBean implements Serializable {

    private String username;
    private String password;
    /**
     * errcode : 21003
     * errmsg : 用户名不存在
     * debug : {}
     */

    private int errcode;
    private String errmsg;

    public UserBean(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
