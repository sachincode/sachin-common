package com.sachin.common.auth.filter;

import com.sachin.common.auth.AuthConfig;
import com.sachin.common.auth.Validate;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 * @author shicheng.zhang
 * @date 16-11-20
 * @time 下午8:33
 * @Description:
 */
public class AuthFilter implements Filter {

    private Validate validate = null;

    @Resource
    public void setValidate(Validate validate) {
        this.validate = validate;
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        AuthConfig authConfig = new AuthConfig();
        authConfig.setAlertUri(filterConfig.getInitParameter("alertUri"));
        authConfig.setLoginUri(filterConfig.getInitParameter("loginUri"));
        authConfig.setNoCheckUri(filterConfig.getInitParameter("noCheckUris"));
        setAuthConfig(authConfig);
    }

    public void setAuthConfig(AuthConfig authConfig) {
        validate.setAuthConfig(authConfig);
        validate.builderCookieUtils();
    }

    public void doFilter(ServletRequest req, ServletResponse rsp, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rsp;
        // 检查路径是否在监察范围
        if (validate.checkUri(request)) {
            // 检查用户是否登录
            if (validate.checkLogin(request)) {
                if (validate.checkAuth(request)) {
                    chain.doFilter(request, response);
                } else {
                    response.sendRedirect(validate.getAuthWarnURL(request));
                }
            } else {
                response.sendRedirect(validate.getLoginURL(request));
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    public void destroy() {
        validate = null;
    }

}
