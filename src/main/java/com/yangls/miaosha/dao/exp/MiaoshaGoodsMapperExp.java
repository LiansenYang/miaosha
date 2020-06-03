package com.yangls.miaosha.dao.exp;

import com.yangls.miaosha.model.Goods;
import com.yangls.miaosha.vo.GoodsVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import java.util.List;

/**
 * @description: 秒杀商品拓展sql接口
 * @author: yangLs
 * @create: 2020-06-03 15:56
 **/
public interface MiaoshaGoodsMapperExp {

    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id")
    public List<GoodsVo> listGoodsVo();

    @Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where mg.goods_id =#{goodsId}")
    public GoodsVo getMiaoshaGoodsByGoodsId(@Param("goodsId") long goodsId);


}
