package com.sachin.common.auth;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author shicheng.zhang
 * @date 16-11-20
 * @time 下午8:34
 * @Description:
 */
public class DefaultValidate extends AbstractValidate {

    @Override
    public boolean checkAuth(HttpServletRequest request) {
        return true;
    }

    @Override
    public boolean checkLogin(HttpServletRequest request) {
        return true;
    }

    @Override
    public List<String> login(HttpServletRequest request, HttpServletResponse response) {
        return new ArrayList<String>();
    }

}
