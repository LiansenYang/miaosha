package com.yangls.miaosha.rabbitMq;

import com.yangls.miaosha.model.MiaoshaUser;

/**
 * @description: 消息
 * @author: yangLs
 * @create: 2020-06-08 17:50
 **/
public class MiaoshaMessage {

    private MiaoshaUser user;
    private Long goodsId;

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }

    public Long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Long goodsId) {
        this.goodsId = goodsId;
    }
}
