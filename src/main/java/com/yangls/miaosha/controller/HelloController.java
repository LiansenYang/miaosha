package com.yangls.miaosha.controller;

import com.yangls.miaosha.model.User;
import com.yangls.miaosha.redis.RedisService;
import com.yangls.miaosha.redis.UserKey;
import com.yangls.miaosha.service.UserService;
import com.yangls.miaosha.web.result.DataDemo;
import com.yangls.miaosha.web.result.ResponseObject;
import com.yangls.miaosha.web.result.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description:
 * @author: yangLs
 * @create: 2020-06-01 10:03
 **/
@Controller
@RequestMapping("/demo")
public class HelloController {

    @Autowired
    UserService userService;

    @Autowired
    RedisService redisService;

    @RequestMapping("hello2")
    @ResponseBody
    public String Hello2(){
        return "helloworld";
    }

    @RequestMapping("/success")
    @ResponseBody
    public ResponseObject<DataDemo> Hello(){
        DataDemo dataDemo = new DataDemo("weyang",12,'1');
        return new ResponseObject<DataDemo>(dataDemo);
    }

    @RequestMapping("/error")
    @ResponseBody
    public ResponseObject<DataDemo> error(){
        return new ResponseObject<DataDemo>(
                ResponseStatus.SERVER_ERROR.getStatusCode(),
                ResponseStatus.SERVER_ERROR.getStatusMsg());
    }
    @RequestMapping("/error2")
    @ResponseBody
    public ResponseObject<DataDemo> error2(){
        return new ResponseObject<DataDemo>(ResponseStatus.SERVER_ERROR);
    }

    @RequestMapping("/thymeleaf")
    public String thymeleaf(Model model){
        System.out.println("haha");
        model.addAttribute("name","yangLs");
        return "hello";
    }

    @RequestMapping("/getUser")
    @ResponseBody
    public ResponseObject<User> getUser(){
        User user = userService.getUserById(1);
        return new ResponseObject<User>(user);
    }

    /**
     * 测试事务
     * @return
     */
    @RequestMapping("/testTX")
    @ResponseBody
    public ResponseObject<Boolean> testTX(){
        boolean flag = userService.addUser();
        return new ResponseObject<Boolean>(flag);
    }

    /**
     * 插入缓存
     * @return
     */
    @RequestMapping("/setRedis")
    @ResponseBody
    public ResponseObject<Boolean> setRedis(){
        User user = userService.getUserById(1);
        redisService.set(UserKey.getById,"1",user);
        return new ResponseObject<Boolean>(true);
    }

    /**
     * 获取缓存
     * @return
     */
    @RequestMapping("/getRedis")
    @ResponseBody
    public ResponseObject<User> getRedis(){
        User user = redisService.get(UserKey.getById,"1",User.class);
        return new ResponseObject<User>(user);
    }



}
