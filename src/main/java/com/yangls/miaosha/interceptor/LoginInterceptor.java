package com.yangls.miaosha.interceptor;

import com.yangls.miaosha.annotation.NeedLogin;
import com.yangls.miaosha.common.CommonUtils;
import com.yangls.miaosha.controller.LoginController;
import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.redis.MiaoshaUserKey;
import com.yangls.miaosha.redis.RedisService;
import com.yangls.miaosha.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description: 登录拦截
 * @author: yangLs
 * @create: 2020-06-05 18:36
 **/

@Component
public class LoginInterceptor implements HandlerInterceptor{
    private static Logger log = LoggerFactory.getLogger(LoginInterceptor.class);

    @Autowired
    RedisService redisService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info(request.getRequestURI().toString());
        if(handler instanceof HandlerMethod){
            HandlerMethod method = (HandlerMethod)handler;

            NeedLogin needLogin= method.getMethodAnnotation(NeedLogin.class);
            if(needLogin!=null){
                String token = CommonUtils.getToken(request,response);
                MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
                if(user == null){
                    log.info("用户未登录，跳转到登录页面,现在是前后端分离，所以直接到后台返回json让前端自己根据这个返回数据做判断跳转到登录的压面");
                    request.getRequestDispatcher("/login/toLogin4Font").forward(request,response);

                    //如果是动静分离的话，不能使用服务端跳转，要使用客户端跳转
//                    request.getRequestDispatcher("/login/toLogin").forward(request,response);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }

}
