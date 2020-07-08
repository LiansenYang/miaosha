package com.yangls.miaosha.common;

import com.alibaba.fastjson.JSON;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:共用工具类
 * @author: yangLs
 * @create: 2020-06-05 19:22
 **/
public class CommonUtils {

    public static final String getToken(HttpServletRequest request, HttpServletResponse response){
        String paramToken = request.getParameter(Constants.COOKIE_NAME_TOKEN);
        String cookieToken = null;
        if(StringUtils.isEmpty(paramToken)){
            cookieToken = CookieUtil.getCookieValue(request, Constants.COOKIE_NAME_TOKEN);
            if(StringUtils.isEmpty(cookieToken)) {
                return null;
            }
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return token;
    }

    public static <T> String beanToString(T value) {
        if(value == null) {
            System.out.println("jsjdj");
            return null;
        }
        Class<?> clazz = value.getClass();
        if(clazz == int.class || clazz == Integer.class) {
            return ""+value;
        }else if(clazz == String.class) {
            return (String)value;
        }else if(clazz == long.class || clazz == Long.class) {
            return ""+value;
        }else {
            return JSON.toJSONString(value);
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T stringToBean(String str, Class<T> clazz) {
        if(str == null || str.length() <= 0 || clazz == null) {
            return null;
        }
        if(clazz == int.class || clazz == Integer.class) {
            return (T)Integer.valueOf(str);
        }else if(clazz == String.class) {
            return (T)str;
        }else if(clazz == long.class || clazz == Long.class) {
            return  (T)Long.valueOf(str);
        }else {
            return JSON.toJavaObject(JSON.parseObject(str), clazz);
        }
    }
}
