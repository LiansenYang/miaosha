package com.yangls.miaosha.service.impl;

import com.yangls.miaosha.dao.MiaoshaGoodsMapper;
import com.yangls.miaosha.dao.exp.MiaoshaGoodsMapperExp;
import com.yangls.miaosha.model.MiaoshaGoods;
import com.yangls.miaosha.service.GoodsService;
import com.yangls.miaosha.vo.GoodsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description: 商品service
 * @author: yangLs
 * @create: 2020-06-03 16:02
 **/
@Service
public class GoodsServiceImpl implements GoodsService{

    @Autowired
    MiaoshaGoodsMapperExp miaoshaGoodsMapperExp;
    @Autowired
    MiaoshaGoodsMapper miaoshaGoodsMapper;

    @Override
    public List<GoodsVo> getMiaoshaGoods() {
        return miaoshaGoodsMapperExp.listGoodsVo();
    }

    @Override
    public GoodsVo getMiaoshaGoodsByGoodsId(long goodsId) {
        return miaoshaGoodsMapperExp.getMiaoshaGoodsByGoodsId(goodsId);
    }

    @Override
    public void reduceStock(long goodsId) {
        MiaoshaGoods miaoshaGoods = miaoshaGoodsMapper.selectByPrimaryKey(goodsId);
        miaoshaGoods.setStockCount(miaoshaGoods.getStockCount() -1);
        miaoshaGoodsMapper.updateByPrimaryKeySelective(miaoshaGoods);
    }
}
