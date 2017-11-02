package com.frutacloud.baseapp.entity;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/11/2.
 * 实体类
 */

public class UserBean implements Serializable {

    private String username;
    private String password;

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
}
