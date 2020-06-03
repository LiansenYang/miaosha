package com.yangls.miaosha.service.impl;

import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.model.OrderInfo;
import com.yangls.miaosha.service.GoodsService;
import com.yangls.miaosha.service.MiaoshaService;
import com.yangls.miaosha.service.OrderService;
import com.yangls.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @description: 秒杀service实现
 * @author: yangLs
 * @create: 2020-06-03 20:25
 **/
@Service
public class MiaoshaServiceImpl implements MiaoshaService{

    @Autowired
    OrderService orderService;

    @Autowired
    GoodsService goodsService;


    @Override
    @Transactional //有修改的必须加上事务
    public OrderInfo miaosha(MiaoshaUser user,GoodsVo goodsVo) {
        //减库存下订单 写入秒杀订单
        goodsService.reduceStock(goodsVo.getId());

        OrderInfo orderInfo = orderService.createOrder(user,goodsVo);

        return orderInfo;
    }
}
