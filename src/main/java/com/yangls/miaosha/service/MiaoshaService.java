package com.yangls.miaosha.service;

import com.yangls.miaosha.exception.GlobalException;
import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.model.OrderInfo;
import com.yangls.miaosha.vo.GoodsVo;

import java.util.List;

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
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goodsVo) throws GlobalException;

    void reset(List<GoodsVo> goodsList);

    long getMiaoshaResult(long id, long goodsId);
}
