package com.yangls.miaosha.service.impl;

import com.yangls.miaosha.dao.MiaoshaOrderMapper;
import com.yangls.miaosha.dao.OrderInfoMapper;
import com.yangls.miaosha.dao.exp.OrderInfoMapperExp;
import com.yangls.miaosha.model.*;
import com.yangls.miaosha.redis.OrderKey;
import com.yangls.miaosha.redis.RedisService;
import com.yangls.miaosha.service.MiaoshaService;
import com.yangls.miaosha.service.OrderService;
import com.yangls.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    MiaoshaOrderMapper miaoshaOrderMapper;

    @Autowired
    OrderInfoMapper orderInfoMapper;

    @Autowired
    OrderInfoMapperExp orderInfoMapperExp;

    @Autowired
    RedisService redisService;


    @Override
    public MiaoshaOrder getOrderByUserIdAndGoodsId(long userId, long goodsId){
//        MiaoshaOrderExample example = new MiaoshaOrderExample();
//        example.createCriteria().andUserIdEqualTo(userId).andGoodsIdEqualTo(goodsId);
//        List<MiaoshaOrder> miaoshaOrders = miaoshaOrderMapper.selectByExample(example);
//        if(miaoshaOrders != null &&miaoshaOrders.size() >0 ){
//            return miaoshaOrders.get(0);
//        }
        //改从缓存中获取
        MiaoshaOrder miaoshaOrder = redisService.get(OrderKey.getMiaoshaOrderByUidGid,""+userId+"_"+goodsId,MiaoshaOrder.class);
        return miaoshaOrder;
    }

    @Override
    @Transactional
    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goods) {
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setCreateDate(new Date());
        orderInfo.setDeliveryAddrId(0L);
        orderInfo.setGoodsCount(1);
        orderInfo.setGoodsId(goods.getId());
        orderInfo.setGoodsName(goods.getGoodsName());
        orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
        orderInfo.setOrderChannel(new Byte("1"));
        orderInfo.setStatus(new Byte("0"));
        orderInfo.setUserId(user.getId());
        orderInfoMapperExp.insert(orderInfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderInfo.getId());
        miaoshaOrder.setUserId(user.getId());
        miaoshaOrderMapper.insert(miaoshaOrder);

        //创建订单之后把订单存放到缓存中
        redisService.set(OrderKey.getMiaoshaOrderByUidGid,""+user.getId()+"_"+goods.getId(), miaoshaOrder);

        return orderInfo;
    }

    @Override
    public OrderInfo getOrderById(long orderId) {

        return orderInfoMapper.selectByPrimaryKey(orderId);
    }

    @Override
    public void deleteOrders() {
        MiaoshaOrderExample miaoshaOrderExample = new MiaoshaOrderExample();
        miaoshaOrderMapper.deleteByExample(miaoshaOrderExample);
        OrderInfoExample orderInfoExample = new OrderInfoExample();
        orderInfoMapper.deleteByExample(orderInfoExample);
    }
}
