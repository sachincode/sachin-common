package com.sachin.common.auth;

import java.util.Date;

/**
 * @author shicheng.zhang
 * @since  16-11-20 下午8:23
 */
public class User {

    private String id;
    // 用户名
    private String username;
    // 用户登录时间
    private Date loginDate = new Date();

    public User() {
        super();
    }

    public User(String username) {
        this.username = username;
    }

    public User(String username, Date loginDate) {
        super();
        this.username = username;
        this.loginDate = loginDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
