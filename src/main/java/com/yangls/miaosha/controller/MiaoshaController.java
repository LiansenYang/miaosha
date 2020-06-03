package com.yangls.miaosha.controller;

import com.yangls.miaosha.model.MiaoshaOrder;
import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.model.OrderInfo;
import com.yangls.miaosha.service.GoodsService;
import com.yangls.miaosha.service.MiaoshaService;
import com.yangls.miaosha.service.OrderService;
import com.yangls.miaosha.vo.GoodsVo;
import com.yangls.miaosha.web.result.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @description: 秒杀Controller
 * @author: yangLs
 * @create: 2020-06-03 19:52
 **/
@Controller
@RequestMapping("miaosha")
public class MiaoshaController {


    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService miaoshaOrderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RequestMapping("do_miaosha")
    public String maiosha(@RequestParam("goodsId") long goodsId, MiaoshaUser user, Model model){
        //TODO 这个判断有没有用户应该放到拦截器中做。
        model.addAttribute("user", user);
        if(user == null) {
            return "login";
        }
        //查询秒杀订单中是否有这个用户之前已经秒杀过了
        MiaoshaOrder miaoshaOrder = miaoshaOrderService.getOrderByUserIdAndGoodsId(user.getId(),goodsId);
        if(miaoshaOrder != null){
            model.addAttribute("errmsg", ResponseStatus.REPEATE_MIAOSHA.getStatusMsg());
            return "miaosha_fail";
        }
        //判断库存
        GoodsVo goodsVo = goodsService.getMiaoshaGoodsByGoodsId(goodsId);

        if(goodsVo.getStockCount() <=0 ){//已经没有了，跳转到已经秒杀没有了的页面
            model.addAttribute("errmsg", ResponseStatus.MIAO_SHA_OVER.getStatusMsg());
            return "miaosha_fail";
        }

        //减库存，下订单 写入秒杀订单
        OrderInfo orderInfo = miaoshaService.miaosha(user,goodsVo);

        model.addAttribute("orderInfo", orderInfo);
        model.addAttribute("goods", goodsVo);

        return "order_detail";
    }
}
