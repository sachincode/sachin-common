package com.sachin.common.auth.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

/**
 * @author shicheng.zhang
 * @since 16-11-20 下午8:26
 */
public class CookieUtils {

    private String loginCookieDomain;
    private String loginCookiePath;
    private int loginCookieMaxAge;
    private String loginCookieSalt;

    public CookieUtils(String loginCookieDomain, String loginCookiePath, int loginCookieMaxAge, String loginCookieSalt) {
        this.loginCookieDomain = loginCookieDomain;
        this.loginCookieSalt = loginCookieSalt;
        this.loginCookiePath = loginCookiePath;
        this.loginCookieMaxAge = loginCookieMaxAge;
    }

    public void writeCookie(HttpServletResponse response, String name, String value) {
        Cookie cookie = new Cookie(name, URLEncoderUtils.encoder(value));
        cookie.setPath(loginCookiePath);
        //cookie.setDomain(loginCookieDomain);
        cookie.setMaxAge(loginCookieMaxAge);
        response.addCookie(cookie);
    }

    public boolean checkKey(String key, String value) {
        return key.equals(DigestUtils.md5Hex(value + loginCookieSalt));
    }

    public void writeSecureCookie(HttpServletResponse response, String name, String value) {
        writeCookie(response, name, DigestUtils.md5Hex(value + loginCookieSalt));
    }

    public String getCookieByName(HttpServletRequest request, String name) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                String cName = cookie.getName();
                if (name.equals(cName)) {
                    return URLEncoderUtils.decode(cookie.getValue());
                }
            }
        }
        return null;
    }

    public void removeCookie(HttpServletResponse response, String name) {
        Cookie userCookie = new Cookie(name, StringUtils.EMPTY);
        userCookie.setPath(loginCookiePath);
        userCookie.setDomain(loginCookieDomain);
        userCookie.setMaxAge(0);
        response.addCookie(userCookie);
    }


    public void clearCookie(HttpServletResponse response, String name) {
        writeCookie(response, name, null);
    }

}
