package com.yangls.miaosha.vo;

import com.yangls.miaosha.model.OrderInfo;

/**
 * @description: 订单vo
 * @author: yangLs
 * @create: 2020-06-05 22:04
 **/
public class OrderDetailVo {

    private GoodsVo goods;
    private OrderInfo order;

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public OrderInfo getOrder() {
        return order;
    }

    public void setOrder(OrderInfo order) {
        this.order = order;
    }
}
