package com.yangls.miaosha.service.impl;

import com.yangls.miaosha.dao.MiaoshaGoodsMapper;
import com.yangls.miaosha.dao.exp.MiaoshaGoodsMapperExp;
import com.yangls.miaosha.exception.GlobalException;
import com.yangls.miaosha.model.MiaoshaGoods;
import com.yangls.miaosha.model.MiaoshaGoodsExample;
import com.yangls.miaosha.service.GoodsService;
import com.yangls.miaosha.vo.GoodsVo;
import com.yangls.miaosha.web.result.ResponseStatus;
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
    public void reduceStock(long goodsId) throws GlobalException {
//        MiaoshaGoods miaoshaGoods = miaoshaGoodsMapper.selectByPrimaryKey(goodsId);
        //这里先拿出来再插进去的时候是有可能会出现有另外一个线程也把这个值设为同一个的，所以这种先查出来再把值减掉的操作是不可取的，是会出现卖同一个的。
//        miaoshaGoods.setStockCount(miaoshaGoods.getStockCount() -1);
//
//        miaoshaGoodsMapper.updateByPrimaryKeySelective(miaoshaGoods);
//        MiaoshaGoodsExample example = new MiaoshaGoodsExample();
//        example.createCriteria().andStockCountGreaterThan(0);
//        miaoshaGoodsMapper.updateByExampleSelective(miaoshaGoods,example);
       //上面即使限制了stock>0也会出现卖超，看上面解释
        MiaoshaGoods g = new MiaoshaGoods();
        g.setGoodsId(goodsId);
        int reduceSize = miaoshaGoodsMapperExp.reduceStock(g);
        //如果不做判断的话，也会存在异常
        if(reduceSize <= 0){
            throw new GlobalException(ResponseStatus.MIAO_SHA_OVER);
        }


    }

    @Override
    public void resetStock(List<GoodsVo> goodsList) {
        for(GoodsVo goods : goodsList ) {
            MiaoshaGoods g = new MiaoshaGoods();
            g.setStockCount(goods.getStockCount());

            MiaoshaGoodsExample example = new MiaoshaGoodsExample();
            example.createCriteria().andGoodsIdEqualTo(goods.getId());
            miaoshaGoodsMapper.updateByExampleSelective(g,example);
        }
    }
}
