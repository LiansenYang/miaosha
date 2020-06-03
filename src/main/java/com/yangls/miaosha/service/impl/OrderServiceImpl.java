package com.yangls.miaosha.service.impl;

import com.yangls.miaosha.dao.MiaoshaOrderMapper;
import com.yangls.miaosha.dao.OrderInfoMapper;
import com.yangls.miaosha.dao.exp.OrderInfoMapperExp;
import com.yangls.miaosha.model.MiaoshaOrder;
import com.yangls.miaosha.model.MiaoshaOrderExample;
import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.model.OrderInfo;
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


    @Override
    public MiaoshaOrder getOrderByUserIdAndGoodsId(long userId, long goodsId){
        MiaoshaOrderExample example = new MiaoshaOrderExample();
        example.createCriteria().andUserIdEqualTo(userId).andGoodsIdEqualTo(goodsId);
        List<MiaoshaOrder> miaoshaOrders = miaoshaOrderMapper.selectByExample(example);
        if(miaoshaOrders != null &&miaoshaOrders.size() >0 ){
            return miaoshaOrders.get(0);
        }
        return null;
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
        //这个insert 返回的不是插入的个数，而是返回那个主键的id，具体看注解
        long orderId = orderInfoMapperExp.insert(orderInfo);
        MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
        miaoshaOrder.setGoodsId(goods.getId());
        miaoshaOrder.setOrderId(orderId);
        miaoshaOrder.setUserId(user.getId());
        miaoshaOrderMapper.insert(miaoshaOrder);
        return orderInfo;
    }
}
