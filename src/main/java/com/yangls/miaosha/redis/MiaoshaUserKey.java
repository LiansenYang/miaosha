package com.yangls.miaosha.redis;

/**
 * @description: 秒杀用户key
 * @author: yangLs
 * @create: 2020-06-02 20:13
 **/
public class MiaoshaUserKey extends BasePrefix{


    public MiaoshaUserKey(int expireSeconds, String prefix) {
        super(expireSeconds, prefix);
    }

    public static MiaoshaUserKey token = new MiaoshaUserKey(2*60*60*1000,"token");
}
