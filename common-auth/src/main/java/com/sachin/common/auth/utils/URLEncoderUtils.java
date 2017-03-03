package com.sachin.common.auth.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * @author shicheng.zhang
 * @date 16-11-20
 * @time 下午8:26
 * @Description:
 */
public class URLEncoderUtils {

    private final static String CHARSET = "UTF-8";

    public static String encoder(String value) {
        if (value == null) {
            return null;
        }
        try {
            return URLEncoder.encode(value, CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static String decode(String value) {
        if (value == null) {
            return null;
        }
        try {
            return URLDecoder.decode(value, CHARSET);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return value;
    }
}
