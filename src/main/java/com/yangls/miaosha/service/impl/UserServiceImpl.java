package com.yangls.miaosha.service.impl;

import com.sun.tools.doclets.internal.toolkit.builders.ConstantsSummaryBuilder;
import com.yangls.miaosha.common.Constants;
import com.yangls.miaosha.dao.MiaoshaUserMapper;
import com.yangls.miaosha.dao.UserMapper;
import com.yangls.miaosha.exception.GlobalException;
import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.model.MiaoshaUserExample;
import com.yangls.miaosha.model.User;
import com.yangls.miaosha.redis.MiaoshaUserKey;
import com.yangls.miaosha.redis.RedisService;
import com.yangls.miaosha.service.UserService;
import com.yangls.miaosha.util.MD5Util;
import com.yangls.miaosha.util.UUIDUtil;
import com.yangls.miaosha.vo.LoginVo;
import com.yangls.miaosha.web.result.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.thymeleaf.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * @description:
 * @author: yangLs
 * @create: 2020-06-01 19:18
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    MiaoshaUserMapper miaoshaUserMapper;

    @Autowired
    RedisService redisService;

    @Override
    public User getUserById(int id) {

        return userMapper.selectByPrimaryKey(id);
    }

    @Transactional
    public boolean addUser() {
        User user = new User();
        user.setId(2);
        user.setName("hahha");
        userMapper.insert(user);

        User user2 = new User();
        user2.setId(1);
        user2.setName("sdfs");
        userMapper.insert(user2);

        return true;
    }

    @Override
    public String login(HttpServletResponse response,LoginVo loginVo) throws GlobalException{
        //查找数据库登录对象，如果纯在就放到缓存中
        MiaoshaUserExample miaoshaUserExample = new MiaoshaUserExample();
        //除了页面进行了MD5加密外还需要再次加密密码，形成  MD5(MD5(pass,salt),dbslat)
        String mobile = loginVo.getMobile();
        String voPass = loginVo.getPassword();
        if(StringUtils.isEmpty(mobile)){
            throw new GlobalException(ResponseStatus.MOBILE_EMPTY);
        }
        if(StringUtils.isEmpty(voPass)){
            throw new GlobalException(ResponseStatus.PASSWORD_EMPTY);
        }
        MiaoshaUser miaoshaUser = miaoshaUserMapper.selectByPrimaryKey(Long.valueOf(loginVo.getMobile()));
        if(miaoshaUser == null){
            throw new GlobalException(ResponseStatus.USER_NOT_EXIST);
        }
        String dbPass = miaoshaUser.getPassword();
        String dbSalt = miaoshaUser.getSalt();
        String calcPass = MD5Util.formPassToDBPass(voPass,dbSalt);
        if(!calcPass.equals(dbPass)){
            throw new GlobalException(ResponseStatus.USER_NOT_EXIST);
        }
        //生成cookie
        String token = UUIDUtil.getUUID();
        addCookie(response, token, miaoshaUser);
        return token;
    }

    @Override
    public MiaoshaUser getByToken(HttpServletResponse response, String token) {
        if(StringUtils.isEmpty(token)){
            return null;
        }
        MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
        if(user != null){
            addCookie(response,token,user);
        }

        return user;
    }

    private void addCookie(HttpServletResponse response, String token, MiaoshaUser user) {
        redisService.set(MiaoshaUserKey.token, token, user);
        Cookie cookie = new Cookie(Constants.COOKIE_NAME_TOKEN, token);
        cookie.setMaxAge(MiaoshaUserKey.token.expireSeconds());
        cookie.setPath("/");
        response.addCookie(cookie);
    }


}
