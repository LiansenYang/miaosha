package com.yangls.miaosha.service;

import com.yangls.miaosha.vo.GoodsVo;
import java.util.*;

public interface GoodsService {
    public List<GoodsVo> getMiaoshaGoods();

    public GoodsVo getMiaoshaGoodsByGoodsId(long goodsId);

    /**
     * 减少库存
     * @param goodsId
     */
    void reduceStock(long goodsId);
}
