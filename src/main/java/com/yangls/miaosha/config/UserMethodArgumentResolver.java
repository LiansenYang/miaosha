package com.yangls.miaosha.config;

import com.yangls.miaosha.common.Constants;
import com.yangls.miaosha.common.CookieUtil;
import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.redis.RedisService;
import com.yangls.miaosha.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 用户自定义解析器，实际情况是从Session、数据库或者缓存中查得当前的用户信息，返回到User参数中
 * @author: yangLs
 * @create: 2020-06-03 09:01
 **/
@Configuration
public class UserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    @Autowired
    RedisService redisService;

    @Autowired
    UserService userService;

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        //判断什么方法的参数类型可以进入到这个方法参数解析器中
        Class<?> clazz = methodParameter.getParameterType();
        return clazz==MiaoshaUser.class;
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        HttpServletRequest request = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
        HttpServletResponse response = nativeWebRequest.getNativeResponse(HttpServletResponse.class);

        String paramToken = request.getParameter(Constants.COOKIE_NAME_TOKEN);
        String cookieToken = null;
        if(StringUtils.isEmpty(paramToken)){
            cookieToken = CookieUtil.getCookieValue(request, Constants.COOKIE_NAME_TOKEN);
            if(StringUtils.isEmpty(cookieToken)) {
                return null;
            }
        }
        String token = StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
        return userService.getByToken(response, token);
    }
}
