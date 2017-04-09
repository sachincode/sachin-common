package com.sachin.common.auth.exception;

/**
 * @author shicheng.zhang
 * @since 16-11-20 下午8:32
 */
public class AuthConfigException extends RuntimeException {

    private static final long serialVersionUID = 7132596220405816332L;

    private String message;

    public AuthConfigException(String message) {
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

}
