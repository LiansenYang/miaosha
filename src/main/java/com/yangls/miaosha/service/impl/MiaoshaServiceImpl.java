package com.yangls.miaosha.service.impl;

import com.yangls.miaosha.exception.GlobalException;
import com.yangls.miaosha.model.MiaoshaOrder;
import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.model.OrderInfo;
import com.yangls.miaosha.redis.MiaoshaKey;
import com.yangls.miaosha.redis.RedisService;
import com.yangls.miaosha.service.GoodsService;
import com.yangls.miaosha.service.MiaoshaService;
import com.yangls.miaosha.service.OrderService;
import com.yangls.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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

    @Autowired
    RedisService redisService;


    @Override
    @Transactional //有修改的必须加上事务
    public OrderInfo miaosha(MiaoshaUser user,GoodsVo goodsVo) throws GlobalException {
        //防止卖超的方法，和一个用户重复秒
        //1、在减少的时候加上判断数据要大于0，因为数据库查询之后是有锁的，不会出现两个线程同时更新一条记录的情况
        //2、防止重复秒的方法是在订单的表中加上《唯一索引》，在保存的时候就会回滚，注意不是普通索引

        //减库存下订单 写入秒杀订单
        goodsService.reduceStock(goodsVo.getId());

        OrderInfo orderInfo = orderService.createOrder(user,goodsVo);

        return orderInfo;
    }

    @Override
    public void reset(List<GoodsVo> goodsList) {
        goodsService.resetStock(goodsList);
        orderService.deleteOrders();
    }

    @Override
    public long getMiaoshaResult(long userId, long goodsId) {
        MiaoshaOrder order = orderService.getOrderByUserIdAndGoodsId(userId, goodsId);
        if(order != null) {//秒杀成功
            return order.getOrderId();
        }else {
            boolean isOver = getGoodsOver(goodsId);
            if(isOver) {
                return -1;
            }else {
                return 0;
            }
        }
    }


    private boolean getGoodsOver(long goodsId) {
        return redisService.exists(MiaoshaKey.isGoodsOver, ""+goodsId);
    }
}
