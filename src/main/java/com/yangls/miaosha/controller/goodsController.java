package com.yangls.miaosha.controller;

import com.yangls.miaosha.common.Constants;
import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.redis.MiaoshaUserKey;
import com.yangls.miaosha.redis.RedisService;
import com.yangls.miaosha.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 商品controller
 * @author: yangLs
 * @create: 2020-06-02 21:12
 **/
@Controller
@RequestMapping("/goods")
public class goodsController {
    @Autowired
    RedisService redisService;

//    @RequestMapping("/to_list")
//    public String list(Model model, @CookieValue(Constants.COOKIE_NAME_TOKEN) String token) {
//        MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
//        model.addAttribute("user", miaoshaUser);
//        return "goods_list";
//    }
    //修改为使用UserMethodArgumentResolver封装user
    @RequestMapping("/to_list")
    public String list(Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        return "goods_list";
    }
}
