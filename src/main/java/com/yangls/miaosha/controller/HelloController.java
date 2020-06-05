package com.yangls.miaosha.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.yangls.miaosha.dao.MiaoshaUserMapper;
import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.model.MiaoshaUserExample;
import com.yangls.miaosha.model.User;
import com.yangls.miaosha.redis.RedisService;
import com.yangls.miaosha.redis.UserKey;
import com.yangls.miaosha.service.UserService;
import com.yangls.miaosha.util.MD5Util;
import com.yangls.miaosha.web.result.DataDemo;
import com.yangls.miaosha.web.result.ResponseObject;
import com.yangls.miaosha.web.result.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

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

    @Autowired
    MiaoshaUserMapper miaoshaUserMapper;

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
    public ResponseObject error(){
        User user = userService.getUserById(1);
        redisService.set(UserKey.getById,"1",user);
        int i=1/0;
        return new ResponseObject(ResponseStatus.SERVER_ERROR);
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

    /**
     * 压测自己获取用
     * @return
     */
    @RequestMapping("/getMiaoshaUser")
    @ResponseBody
    public ResponseObject<MiaoshaUser> getMiaoshaUser(MiaoshaUser user){
        return new ResponseObject<MiaoshaUser>(user);
    }

    //创建Jmeter测试用户
    @RequestMapping("/createUser")
    @ResponseBody
    public ResponseObject<Boolean> createUser(){
        for(int i=0;i<5000;i++) {
            MiaoshaUser user = new MiaoshaUser();
            user.setId(13000000000L + i);
            user.setLoginCount(1);
            user.setNickname("user" + i);
            user.setRegisterDate(new Date());
            user.setSalt("1a2b3c");
            user.setPassword(MD5Util.inputPassToDbPass("123456", user.getSalt()));
            miaoshaUserMapper.insert(user);
        }
        return new ResponseObject<Boolean>(true);
    }

    //生成jmeter 测试token文件
    @RequestMapping("/createToken")
    @ResponseBody
    public ResponseObject<Boolean> createToken() throws IOException {
        //查询所有的秒杀用户
        MiaoshaUserExample example = new MiaoshaUserExample();
        List<MiaoshaUser> miaoshaUsers = miaoshaUserMapper.selectByExample(example);
        if (miaoshaUsers != null && miaoshaUsers.size() >0) {
            //登录，生成token
            String urlString = "http://localhost:8080/login/doLogin";
            File file = new File("D:/tokens.txt");
            if(file.exists()) {
                file.delete();
            }
            RandomAccessFile raf = new RandomAccessFile(file, "rw");
            file.createNewFile();
            raf.seek(0);
            for(MiaoshaUser user:miaoshaUsers){
                URL url = new URL(urlString);
                HttpURLConnection co = (HttpURLConnection)url.openConnection();
                co.setRequestMethod("POST");
                co.setDoOutput(true);
                OutputStream out = co.getOutputStream();
                String params = "mobile="+user.getId()+"&password="+MD5Util.inputPassToFormPass("123456");
                out.write(params.getBytes());
                out.flush();
                InputStream inputStream = co.getInputStream();
                ByteArrayOutputStream bout = new ByteArrayOutputStream();
                byte buff[] = new byte[1024];
                int len = 0;
                while((len = inputStream.read(buff)) >= 0) {
                    bout.write(buff, 0 ,len);
                }
                inputStream.close();
                bout.close();
                String response = new String(bout.toByteArray());
                JSONObject jo = JSON.parseObject(response);
                String token = jo.getString("data");
                System.out.println("create token : " + user.getId());

                String row = user.getId()+","+token;
                raf.seek(raf.length());
                raf.write(row.getBytes());
                raf.write("\r\n".getBytes());
                System.out.println("write to file : " + user.getId());
            }
            raf.close();
            System.out.println("over");
        }
        return new ResponseObject<Boolean>(true);
    }










}
