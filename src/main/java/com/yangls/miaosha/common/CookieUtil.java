package com.yangls.miaosha.common;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * @description: cookie工具类
 * @author: yangLs
 * @create: 2020-06-03 09:15
 **/
public class CookieUtil {
    public static String getCookieValue(HttpServletRequest request, String cookiName) {
        Cookie[]  cookies = request.getCookies();
        if(cookies != null){
            for(Cookie cookie : cookies) {
                if(cookie.getName().equals(cookiName)) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
