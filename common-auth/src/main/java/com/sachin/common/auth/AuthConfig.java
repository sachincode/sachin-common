package com.sachin.common.auth;

import java.util.List;

/**
 * @author shicheng.zhang
 * @date 16-11-20
 * @time 下午8:16
 * @Description:
 */
public class AuthConfig {

    // 用,分开的配置
    private String noCheckUri;
    private List<String> noCheckUris;
    private String loginUri = "/login.html";
    private String alertUri = "/permit.html";
    private String logoutUri = "/logout.html";
    private String appCode = "";
    private String loginCookiePath = "/";
    private String loginCookieDomain = "localhost";
    private String loginCookieSalt = "";
    private int loginCookieMaxAge = 86400;

    public String getAppCode() {
        return appCode;
    }

    public void setAppCode(String appCode) {
        this.appCode = appCode;
    }

    public String getLoginCookieSalt() {
        return loginCookieSalt;
    }

    public void setLoginCookieSalt(String loginCookieSalt) {
        this.loginCookieSalt = loginCookieSalt;
    }

    public List<String> getNoCheckUris() {
        return noCheckUris;
    }

    public void setNoCheckUris(List<String> noCheckUris) {
        this.noCheckUris = noCheckUris;
    }

    public String getLoginUri() {
        return loginUri;
    }

    public void setLoginUri(String loginUri) {
        this.loginUri = loginUri.startsWith("/") ? loginUri : "/" + loginUri;
    }

    public String getAlertUri() {
        return alertUri;
    }

    public void setAlertUri(String alertUri) {
        this.alertUri = alertUri.startsWith("/") ? alertUri : "/" + alertUri;
    }

    public String getLogoutUri() {
        return logoutUri;
    }

    public void setLogoutUri(String logoutUri) {
        this.logoutUri = logoutUri.startsWith("/") ? logoutUri : "/" + logoutUri;
    }

    public String getLoginCookiePath() {
        return loginCookiePath;
    }

    public void setLoginCookiePath(String loginCookiePath) {
        this.loginCookiePath = loginCookiePath;
    }

    public String getLoginCookieDomain() {
        return loginCookieDomain;
    }

    public void setLoginCookieDomain(String loginCookieDomain) {
        this.loginCookieDomain = loginCookieDomain;
    }

    public int getLoginCookieMaxAge() {
        return loginCookieMaxAge;
    }

    public void setLoginCookieMaxAge(int loginCookieMaxAge) {
        this.loginCookieMaxAge = loginCookieMaxAge;
    }

    public String getNoCheckUri() {
        return noCheckUri;
    }

    public void setNoCheckUri(String noCheckUri) {
        this.noCheckUri = noCheckUri;
    }
}
