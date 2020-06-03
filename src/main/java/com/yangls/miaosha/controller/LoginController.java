package com.yangls.miaosha.controller;
import com.yangls.miaosha.model.User;
import com.yangls.miaosha.service.UserService;
import com.yangls.miaosha.vo.LoginVo;
import com.yangls.miaosha.web.result.ResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * @description: 登录注册controller
 * @author: yangLs
 * @create: 2020-06-02 12:54
 **/
@Controller
@RequestMapping("/login")
public class LoginController {
    private static Logger log = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @RequestMapping("/toLogin")
    public String toLogin() {
        return "login";
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public ResponseObject<Boolean> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) {
        log.info(loginVo.toString());
        //登录
        userService.login(response, loginVo);
        return new ResponseObject<Boolean>(true);
    }


}
