package com.yangls.miaosha.util;

import java.util.UUID;

/**
 * @description: 生成UUID, 并格式化UUID
 * @author: yangLs
 * @create: 2020-06-02 21:08
 **/
public class UUIDUtil
{
    public static String getUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }
}
