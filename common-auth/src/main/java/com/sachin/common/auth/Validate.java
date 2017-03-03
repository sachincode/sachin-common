package com.sachin.common.auth;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shicheng.zhang
 * @date 16-11-20
 * @time 下午8:23
 * @Description:
 */
public interface Validate {

    boolean checkUri(HttpServletRequest request);

    String getLoginURL(HttpServletRequest request);

    boolean checkLogin(HttpServletRequest request);

    boolean checkAuth(HttpServletRequest request);

    void setAuthConfig(AuthConfig loginConfig);

    void builderCookieUtils();

    String getAuthWarnURL(HttpServletRequest request);

    <T extends User> T getUser(HttpServletRequest request);

    List<String> login(HttpServletRequest request, HttpServletResponse response);

    void setLogin(HttpServletResponse response, String value);

    void clearLogin(HttpServletResponse response);

    void setValue(HttpServletRequest request, String value);

    String getValue(HttpServletRequest request);

    String getLoginUserName(HttpServletRequest request);
}
