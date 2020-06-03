package com.yangls.miaosha.dao;

import com.yangls.miaosha.model.MiaoshaGoods;
import com.yangls.miaosha.model.MiaoshaGoodsExample;
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

public interface MiaoshaGoodsMapper {
    @SelectProvider(type=MiaoshaGoodsSqlProvider.class, method="countByExample")
    long countByExample(MiaoshaGoodsExample example);

    @DeleteProvider(type=MiaoshaGoodsSqlProvider.class, method="deleteByExample")
    int deleteByExample(MiaoshaGoodsExample example);

    @Delete({
        "delete from miaosha_goods",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into miaosha_goods (id, goods_id, ",
        "miaosha_price, stock_count, ",
        "start_date, end_date)",
        "values (#{id,jdbcType=BIGINT}, #{goodsId,jdbcType=BIGINT}, ",
        "#{miaoshaPrice,jdbcType=DECIMAL}, #{stockCount,jdbcType=INTEGER}, ",
        "#{startDate,jdbcType=TIMESTAMP}, #{endDate,jdbcType=TIMESTAMP})"
    })
    int insert(MiaoshaGoods record);

    @InsertProvider(type=MiaoshaGoodsSqlProvider.class, method="insertSelective")
    int insertSelective(MiaoshaGoods record);

    @SelectProvider(type=MiaoshaGoodsSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="goods_id", property="goodsId", jdbcType=JdbcType.BIGINT),
        @Result(column="miaosha_price", property="miaoshaPrice", jdbcType=JdbcType.DECIMAL),
        @Result(column="stock_count", property="stockCount", jdbcType=JdbcType.INTEGER),
        @Result(column="start_date", property="startDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_date", property="endDate", jdbcType=JdbcType.TIMESTAMP)
    })
    List<MiaoshaGoods> selectByExample(MiaoshaGoodsExample example);

    @Select({
        "select",
        "id, goods_id, miaosha_price, stock_count, start_date, end_date",
        "from miaosha_goods",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="goods_id", property="goodsId", jdbcType=JdbcType.BIGINT),
        @Result(column="miaosha_price", property="miaoshaPrice", jdbcType=JdbcType.DECIMAL),
        @Result(column="stock_count", property="stockCount", jdbcType=JdbcType.INTEGER),
        @Result(column="start_date", property="startDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="end_date", property="endDate", jdbcType=JdbcType.TIMESTAMP)
    })
    MiaoshaGoods selectByPrimaryKey(Long id);

    @UpdateProvider(type=MiaoshaGoodsSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") MiaoshaGoods record, @Param("example") MiaoshaGoodsExample example);

    @UpdateProvider(type=MiaoshaGoodsSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") MiaoshaGoods record, @Param("example") MiaoshaGoodsExample example);

    @UpdateProvider(type=MiaoshaGoodsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MiaoshaGoods record);

    @Update({
        "update miaosha_goods",
        "set goods_id = #{goodsId,jdbcType=BIGINT},",
          "miaosha_price = #{miaoshaPrice,jdbcType=DECIMAL},",
          "stock_count = #{stockCount,jdbcType=INTEGER},",
          "start_date = #{startDate,jdbcType=TIMESTAMP},",
          "end_date = #{endDate,jdbcType=TIMESTAMP}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(MiaoshaGoods record);
}