package com.yangls.miaosha.dao;

import com.yangls.miaosha.model.MiaoshaOrder;
import com.yangls.miaosha.model.MiaoshaOrderExample;
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

public interface MiaoshaOrderMapper {
    @SelectProvider(type=MiaoshaOrderSqlProvider.class, method="countByExample")
    long countByExample(MiaoshaOrderExample example);

    @DeleteProvider(type=MiaoshaOrderSqlProvider.class, method="deleteByExample")
    int deleteByExample(MiaoshaOrderExample example);

    @Delete({
        "delete from miaosha_order",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into miaosha_order (id, user_id, ",
        "order_id, goods_id)",
        "values (#{id,jdbcType=BIGINT}, #{userId,jdbcType=BIGINT}, ",
        "#{orderId,jdbcType=BIGINT}, #{goodsId,jdbcType=BIGINT})"
    })
    int insert(MiaoshaOrder record);

    @InsertProvider(type=MiaoshaOrderSqlProvider.class, method="insertSelective")
    int insertSelective(MiaoshaOrder record);

    @SelectProvider(type=MiaoshaOrderSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="order_id", property="orderId", jdbcType=JdbcType.BIGINT),
        @Result(column="goods_id", property="goodsId", jdbcType=JdbcType.BIGINT)
    })
    List<MiaoshaOrder> selectByExample(MiaoshaOrderExample example);

    @Select({
        "select",
        "id, user_id, order_id, goods_id",
        "from miaosha_order",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="user_id", property="userId", jdbcType=JdbcType.BIGINT),
        @Result(column="order_id", property="orderId", jdbcType=JdbcType.BIGINT),
        @Result(column="goods_id", property="goodsId", jdbcType=JdbcType.BIGINT)
    })
    MiaoshaOrder selectByPrimaryKey(Long id);

    @UpdateProvider(type=MiaoshaOrderSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") MiaoshaOrder record, @Param("example") MiaoshaOrderExample example);

    @UpdateProvider(type=MiaoshaOrderSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") MiaoshaOrder record, @Param("example") MiaoshaOrderExample example);

    @UpdateProvider(type=MiaoshaOrderSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MiaoshaOrder record);

    @Update({
        "update miaosha_order",
        "set user_id = #{userId,jdbcType=BIGINT},",
          "order_id = #{orderId,jdbcType=BIGINT},",
          "goods_id = #{goodsId,jdbcType=BIGINT}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(MiaoshaOrder record);
}