package com.yangls.miaosha.controller;

import com.yangls.miaosha.annotation.NeedLogin;
import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.model.OrderInfo;
import com.yangls.miaosha.service.GoodsService;
import com.yangls.miaosha.service.OrderService;
import com.yangls.miaosha.vo.GoodsVo;
import com.yangls.miaosha.vo.OrderDetailVo;
import com.yangls.miaosha.web.result.ResponseObject;
import com.yangls.miaosha.web.result.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description: 订单controller
 * @author: yangLs
 * @create: 2020-06-05 22:03
 **/
@Controller
@RequestMapping("/order")
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    GoodsService goodsService;

    @RequestMapping("/detail")
    @ResponseBody
    @NeedLogin
    public ResponseObject<OrderDetailVo> getOrder(MiaoshaUser user, @RequestParam("orderId") long orderId){
        OrderInfo order = orderService.getOrderById(orderId);
        if(order == null) {
            return new ResponseObject<OrderDetailVo>(ResponseStatus.ORDER_NOT_EXIST);
        }
        long goodsId = order.getGoodsId();
        GoodsVo goods = goodsService.getMiaoshaGoodsByGoodsId(goodsId);
        OrderDetailVo vo = new OrderDetailVo();
        vo.setOrder(order);
        vo.setGoods(goods);
        return new ResponseObject<OrderDetailVo>(vo);
    }
}
