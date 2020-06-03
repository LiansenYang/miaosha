package com.yangls.miaosha.dao.exp;

import com.yangls.miaosha.model.OrderInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.SelectKey;

public interface OrderInfoMapperExp {
    @Insert("insert into order_info(user_id, goods_id, goods_name, goods_count, goods_price, order_channel, status, create_date)values("
            + "#{userId}, #{goodsId}, #{goodsName}, #{goodsCount}, #{goodsPrice}, #{orderChannel},#{status},#{createDate} )")
    @SelectKey(keyColumn="id", keyProperty="id", resultType=long.class, before=false, statement="select last_insert_id()")
    public long insert(OrderInfo orderInfo);
}
