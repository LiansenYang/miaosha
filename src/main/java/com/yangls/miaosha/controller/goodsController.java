package com.yangls.miaosha.controller;

import com.yangls.miaosha.common.Constants;
import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.redis.MiaoshaUserKey;
import com.yangls.miaosha.redis.RedisService;
import com.yangls.miaosha.service.GoodsService;
import com.yangls.miaosha.service.impl.UserServiceImpl;
import com.yangls.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description: 商品controller
 * @author: yangLs
 * @create: 2020-06-02 21:12
 **/
@Controller
@RequestMapping("/goods")
public class goodsController {
//    @Autowired
//    RedisService redisService;

    @Autowired
    GoodsService goodsService;

//    @RequestMapping("/to_list")
//    public String list(Model model, @CookieValue(Constants.COOKIE_NAME_TOKEN) String token) {
//        MiaoshaUser miaoshaUser = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
//        model.addAttribute("user", miaoshaUser);
//        return "goods_list";
//    }
    //修改为使用UserMethodArgumentResolver封装user
    @RequestMapping("/to_list2")
    public String list(Model model, MiaoshaUser user) {
        model.addAttribute("user", user);
        return "goods_list";
    }

    @RequestMapping("/to_list")
    public String list(Model model) {
        model.addAttribute("goodsList", goodsService.getMiaoshaGoods());
        return "goods_list";
    }
    @RequestMapping("/to_detail/{goodsId}")
    //PathVariable（）内的值对应path{}中的值
    public String to_detail(Model model, MiaoshaUser user, @PathVariable("goodsId") long goodsId) {
        model.addAttribute("user",user);
        //获取商品的信息
        GoodsVo goodsVo = goodsService.getMiaoshaGoodsByGoodsId(goodsId);
        model.addAttribute("goods", goodsVo);

        //获取商品此时的状态和需要倒计时的时间
        long startT = goodsVo.getStartDate().getTime();
        long endT = goodsVo.getEndDate().getTime();
        long curntT = System.currentTimeMillis();

        int miaoshaStatus =Constants.MIAOSHA_STATUS_UNSTART ;
        int remainSeconds = 0; //倒计时
        if(startT > curntT){ //未开始
            miaoshaStatus = Constants.MIAOSHA_STATUS_UNSTART;
            remainSeconds = (int)((startT-curntT)/1000);
        }else if(curntT > endT){ //已结束
            miaoshaStatus = Constants.MIAOSHA_STATUS_STARTED;
            remainSeconds = -1;
        }else{//进行中
            miaoshaStatus = Constants.MIAOSHA_STATUS_STARTING;
            remainSeconds = 0;
        }
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);

        return "goods_detail";
    }




}
