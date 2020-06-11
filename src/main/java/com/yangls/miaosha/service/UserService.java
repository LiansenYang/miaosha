package com.yangls.miaosha.service;

import com.yangls.miaosha.exception.GlobalException;
import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.model.User;
import com.yangls.miaosha.vo.LoginVo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: yangLs
 * @create: 2020-06-01 19:17
 **/
public interface UserService {
    public User getUserById(int id);

    public boolean addUser();

    public String login(HttpServletResponse response, LoginVo loginVo) throws GlobalException;

    MiaoshaUser getByToken(HttpServletResponse response, String token);
}
