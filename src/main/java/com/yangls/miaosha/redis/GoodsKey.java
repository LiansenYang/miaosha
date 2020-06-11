package com.yangls.miaosha.redis;

/**
 * @description: 商品key
 * @author: yangLs
 * @create: 2020-06-05 11:33
 **/
public class GoodsKey extends BasePrefix{
    public GoodsKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }
    public static GoodsKey getGoodsList = new GoodsKey(60, "gl");
    public static GoodsKey getGoodsDetail = new GoodsKey(60, "gd");
    public static GoodsKey getMiaoshaGoodsStock = new GoodsKey(0, "gs");
}
