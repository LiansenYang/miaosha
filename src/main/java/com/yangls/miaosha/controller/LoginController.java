package com.yangls.miaosha.controller;
import com.yangls.miaosha.exception.GlobalException;
import com.yangls.miaosha.model.User;
import com.yangls.miaosha.service.UserService;
import com.yangls.miaosha.vo.LoginVo;
import com.yangls.miaosha.web.result.ResponseObject;
import com.yangls.miaosha.web.result.ResponseStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
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

    @RequestMapping("/toLogin4Font")
    @ResponseBody
    public ResponseObject toLogin4Font() {
        return new ResponseObject(ResponseStatus.NOT_LOGIN);
    }

    @RequestMapping("/doLogin")
    @ResponseBody
    public ResponseObject<String> doLogin(HttpServletResponse response, @Valid LoginVo loginVo) throws GlobalException {
        log.info(loginVo.toString());
        //登录
        String token = userService.login(response, loginVo);
        return new ResponseObject<String>(token);
    }


}
