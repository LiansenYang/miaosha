package com.yangls.miaosha.service;

import com.yangls.miaosha.model.MiaoshaOrder;
import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.model.OrderInfo;
import com.yangls.miaosha.vo.GoodsVo;

public interface OrderService {

    public MiaoshaOrder getOrderByUserIdAndGoodsId(long userId,long goodsId);

    public OrderInfo createOrder(MiaoshaUser user, GoodsVo goodsVo);

    public OrderInfo getOrderById(long orderId);

    void deleteOrders();
}
