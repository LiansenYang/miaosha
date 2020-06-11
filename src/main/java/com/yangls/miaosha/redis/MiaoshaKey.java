package com.yangls.miaosha.redis;

/**
 * @description: 用于记录秒杀的信息
 * @author: yangLs
 * @create: 2020-06-09 10:49
 **/
public class MiaoshaKey extends BasePrefix {
    public MiaoshaKey(String prefix) {
        super(prefix);
    }

    public static MiaoshaKey isGoodsOver = new MiaoshaKey("go");
}
