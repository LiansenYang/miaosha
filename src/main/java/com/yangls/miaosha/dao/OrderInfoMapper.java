package com.yangls.miaosha.dao;

import com.yangls.miaosha.model.OrderInfo;
import com.yangls.miaosha.model.OrderInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.annotations.Update;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.type.JdbcType;

public interface OrderInfoMapper {
    @SelectProvider(type=OrderInfoSqlProvider.class, method="countByExample")
    long countByExample(OrderInfoExample example);

    @DeleteProvider(type=OrderInfoSqlProvider.class, method="deleteByExample")
    int deleteByExample(OrderInfoExample example);

    @Delete({
        "delete from order_info",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into order_info (id, user_id, ",
        "goods_id, delivery_addr_id, ",
        "goods_name, goods_count, ",
        "goods_price, order_channel, ",
        "status, create_date, ",
        "pay_date)",
        "values (#{id,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
        "#{goodsId,jdbcType=BIGINT}, #{deliveryAddrId,jdbcType=BIGINT}, ",
        "#{goodsName,jdbcType=VARCHAR}, #{goodsCount,jdbcType=INTEGER}, ",
        "#{goodsPrice,jdbcType=DECIMAL}, #{orderChannel,jdbcType=TINYINT}, ",
        "#{status,jdbcType=TINYINT}, #{createDate,jdbcType=TIMESTAMP}, ",
        "#{payDate,jdbcType=TIMESTAMP})"
    })
    int insert(OrderInfo record);

    @InsertProvider(type=OrderInfoSqlProvider.class, method="insertSelective")
    int insertSelective(OrderInfo record);

    @SelectProvider(type=OrderInfoSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="goods_id", property="goodsId", jdbcType=JdbcType.BIGINT),
        @Result(column="delivery_addr_id", property="deliveryAddrId", jdbcType=JdbcType.BIGINT),
        @Result(column="goods_name", property="goodsName", jdbcType=JdbcType.VARCHAR),
        @Result(column="goods_count", property="goodsCount", jdbcType=JdbcType.INTEGER),
        @Result(column="goods_price", property="goodsPrice", jdbcType=JdbcType.DECIMAL),
        @Result(column="order_channel", property="orderChannel", jdbcType=JdbcType.TINYINT),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="create_date", property="createDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="pay_date", property="payDate", jdbcType=JdbcType.TIMESTAMP)
    })
    List<OrderInfo> selectByExample(OrderInfoExample example);

    @Select({
        "select",
        "id, user_id, goods_id, delivery_addr_id, goods_name, goods_count, goods_price, ",
        "order_channel, status, create_date, pay_date",
        "from order_info",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="goods_id", property="goodsId", jdbcType=JdbcType.BIGINT),
        @Result(column="delivery_addr_id", property="deliveryAddrId", jdbcType=JdbcType.BIGINT),
        @Result(column="goods_name", property="goodsName", jdbcType=JdbcType.VARCHAR),
        @Result(column="goods_count", property="goodsCount", jdbcType=JdbcType.INTEGER),
        @Result(column="goods_price", property="goodsPrice", jdbcType=JdbcType.DECIMAL),
        @Result(column="order_channel", property="orderChannel", jdbcType=JdbcType.TINYINT),
        @Result(column="status", property="status", jdbcType=JdbcType.TINYINT),
        @Result(column="create_date", property="createDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="pay_date", property="payDate", jdbcType=JdbcType.TIMESTAMP)
    })
    OrderInfo selectByPrimaryKey(Long id);

    @UpdateProvider(type=OrderInfoSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") OrderInfo record, @Param("example") OrderInfoExample example);

    @UpdateProvider(type=OrderInfoSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") OrderInfo record, @Param("example") OrderInfoExample example);

    @UpdateProvider(type=OrderInfoSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(OrderInfo record);

    @Update({
        "update order_info",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "goods_id = #{goodsId,jdbcType=BIGINT},",
          "delivery_addr_id = #{deliveryAddrId,jdbcType=BIGINT},",
          "goods_name = #{goodsName,jdbcType=VARCHAR},",
          "goods_count = #{goodsCount,jdbcType=INTEGER},",
          "goods_price = #{goodsPrice,jdbcType=DECIMAL},",
          "order_channel = #{orderChannel,jdbcType=TINYINT},",
          "status = #{status,jdbcType=TINYINT},",
          "create_date = #{createDate,jdbcType=TIMESTAMP},",
          "pay_date = #{payDate,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(OrderInfo record);
}