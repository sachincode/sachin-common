package com.sachin.common.auth;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shicheng.zhang
 * @since  16-11-20 下午8:23
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
