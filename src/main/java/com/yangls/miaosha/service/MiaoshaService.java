package com.yangls.miaosha.service;

import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.model.OrderInfo;
import com.yangls.miaosha.vo.GoodsVo;

/**
 * @description: 秒杀service
 * @author: yangLs
 * @create: 2020-06-03 20:08
 **/
public interface MiaoshaService {

    /**
     * 减库存下订单 写入秒杀订单
     * @return
     */
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo);

}
