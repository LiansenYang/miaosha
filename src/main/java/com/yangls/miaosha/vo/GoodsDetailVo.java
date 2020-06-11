package com.yangls.miaosha.vo;

import com.yangls.miaosha.model.MiaoshaUser;

/**
 * @description: 商品详情Vo
 * @author: yangLs
 * @create: 2020-06-05 17:55
 **/
public class GoodsDetailVo {
    private int miaoshaStatus;
    private int remainSeconds;

    private GoodsVo goods;
    private MiaoshaUser user;

    public int getMiaoshaStatus() {
        return miaoshaStatus;
    }

    public void setMiaoshaStatus(int miaoshaStatus) {
        this.miaoshaStatus = miaoshaStatus;
    }

    public int getRemainSeconds() {
        return remainSeconds;
    }

    public void setRemainSeconds(int remainSeconds) {
        this.remainSeconds = remainSeconds;
    }

    public GoodsVo getGoods() {
        return goods;
    }

    public void setGoods(GoodsVo goods) {
        this.goods = goods;
    }

    public MiaoshaUser getUser() {
        return user;
    }

    public void setUser(MiaoshaUser user) {
        this.user = user;
    }
}
