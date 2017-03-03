package com.sachin.common.auth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sachin.common.auth.exception.AuthConfigException;
import com.sachin.common.auth.utils.CookieUtils;
import org.apache.commons.lang.StringUtils;



/**
 * @author shicheng.zhang
 * @date 16-11-20
 * @time 下午8:25
 * @Description:
 */
public abstract class AbstractValidate implements Validate {

    private final static String HTTP = "http://";
    private final static String HTTPS = "https://";
    private final static String FAVICON = "/favicon.ico";
    private final static String DOT = ",";
    private AuthConfig authConfig;
    private CookieUtils cookieUtils;
    private Pattern[] noCheckUris;
    private AtomicInteger mark = new AtomicInteger();
    private String key = "id";
    private String loginURL;
    private String authWarnURL;

    private String cookieId = "login_id";
    private String cookieTime = "login_time";
    private String cookieToken = "login_token";

    public void init() {
        String appCode = authConfig.getAppCode();
        if (StringUtils.isEmpty(appCode)) {
            return;
        }
        cookieId = appCode + "_" + cookieId;
        cookieTime = appCode + "_" + cookieTime;
        cookieToken = appCode + "_" + cookieToken;
    }

    public CookieUtils getCookieUtils() {
        return cookieUtils;
    }

    @Override
    public void setAuthConfig(AuthConfig loginConfig) {
        this.authConfig = loginConfig;
    }

    public AuthConfig getLoginConfig() {
        return authConfig;
    }

    @Override
    public void builderCookieUtils() {
        init();
        cookieUtils = new CookieUtils(authConfig.getLoginCookieDomain(), authConfig.getLoginCookiePath(),
                authConfig.getLoginCookieMaxAge(), authConfig.getLoginCookieSalt());
        if (authConfig.getNoCheckUris() == null || authConfig.getNoCheckUris().size() == 0) {
            if (!StringUtils.isBlank(authConfig.getNoCheckUri())) {
                builderCheckUri(authConfig.getNoCheckUri());
            }
        } else {
            builderCheckUri(authConfig.getNoCheckUris());
        }
    }

    private void builderCheckUri(List<String> noCheckUriList) {
        int len = noCheckUriList.size();
        noCheckUris = new Pattern[len];
        for (int i = 0; i < len; i++) {
            noCheckUris[i] = Pattern.compile(StringUtils.trim(noCheckUriList.get(i)));
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T extends User> T getUser(HttpServletRequest request) {
        Object o = request.getAttribute("user");
        if (o != null) {
            return (T) o;
        }
        return null;
    }

    private void builderCheckUri(String noCheckUris) {
        if (!StringUtils.isBlank(noCheckUris)) {
            String[] uris = noCheckUris.split(DOT);
            int len = uris.length;
            if (len > 0) {
                List<String> noCheckUriList = new ArrayList<String>();
                Collections.addAll(noCheckUriList, uris);
                builderCheckUri(noCheckUriList);
            }
        }
    }

    @Override
    public boolean checkUri(HttpServletRequest request) {
        String uri = request.getRequestURI();
        if (FAVICON.equals(uri)) {
            return false;
        }
        // 登录和告警不检查
        if (uri.equals(authConfig.getLoginUri()) || uri.equals(authConfig.getAlertUri())
                || uri.equals(authConfig.getLogoutUri())) {
            return false;
        }
        if (noCheckUris != null && noCheckUris.length > 0) {
            for (Pattern pattern : noCheckUris) {
                Matcher matcher = pattern.matcher(uri);
                if (matcher.find()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String getLoginURL(HttpServletRequest request) {
        if (loginURL == null) {
            loginURL = builderURL(request, authConfig.getLoginUri());
        }
        return loginURL;
    }

    @Override
    public String getAuthWarnURL(HttpServletRequest request) {
        if (authWarnURL == null) {
            authWarnURL = builderURL(request, authConfig.getAlertUri());
        }
        return authWarnURL;
    }

    private String builderURL(HttpServletRequest request, String uri) {
        if (StringUtils.isBlank(uri)) {
            throw new AuthConfigException("loginURL or authWarnURL is null");
        }
        if (uri.startsWith(HTTP) || uri.startsWith(HTTPS)) {
            return uri;
        }
        StringBuilder authWarnURL = new StringBuilder(500);
        authWarnURL.append(request.getScheme()).append("://");
        authWarnURL.append(request.getServerName()).append(":");
        authWarnURL.append(request.getServerPort()).append(request.getContextPath()).append(uri);
        return authWarnURL.toString();
    }

    @Override
    public void setLogin(HttpServletResponse response, String value) {
        String mk = String.valueOf(mark.incrementAndGet());
        cookieUtils.writeCookie(response, cookieTime, mk);
        cookieUtils.writeCookie(response, cookieId, value);
        cookieUtils.writeSecureCookie(response, cookieToken, mk + value);
    }

    @Override
    public void clearLogin(HttpServletResponse response) {
        cookieUtils.clearCookie(response, cookieTime);
        cookieUtils.clearCookie(response, cookieId);
        cookieUtils.clearCookie(response, cookieToken);
    }

    @Override
    public boolean checkLogin(HttpServletRequest request) {
        String mk = cookieUtils.getCookieByName(request, cookieTime);
        String value = cookieUtils.getCookieByName(request, cookieId);
        String key = cookieUtils.getCookieByName(request, cookieToken);
        if (StringUtils.isBlank(mk) || StringUtils.isBlank(value) || StringUtils.isBlank(key)) {
            return false;
        }
        setValue(request, value);
        return cookieUtils.checkKey(key, mk + value);
    }

    @Override
    public void setValue(HttpServletRequest request, String value) {
        request.setAttribute(key, value);
    }

    @Override
    public String getValue(HttpServletRequest request) {
        Object obj = request.getAttribute(key);
        if (obj == null) {
            return null;
        }
        return (String) obj;
    }

    @Override
    public String getLoginUserName(HttpServletRequest request) {
        return cookieUtils.getCookieByName(request, cookieId);
    }
}
