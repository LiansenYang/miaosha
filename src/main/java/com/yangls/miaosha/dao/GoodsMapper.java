package com.yangls.miaosha.dao;

import com.yangls.miaosha.model.Goods;
import com.yangls.miaosha.model.GoodsExample;
import java.util.List;

import org.apache.ibatis.annotations.*;
import org.apache.ibatis.type.JdbcType;

public interface GoodsMapper {
    @SelectProvider(type=GoodsSqlProvider.class, method="countByExample")
    long countByExample(GoodsExample example);

    @DeleteProvider(type=GoodsSqlProvider.class, method="deleteByExample")
    int deleteByExample(GoodsExample example);

    @Delete({
        "delete from goods",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    //
//    @Options(useGeneratedKeys = false, keyProperty = "id", keyColumn = "id")
    @Insert({
        "insert into goods (id, goods_name, ",
        "goods_title, goods_img, ",
        "goods_price, goods_stock, ",
        "goods_detail)",
        "values (#{id,jdbcType=BIGINT}, #{goodsName,jdbcType=VARCHAR}, ",
        "#{goodsTitle,jdbcType=VARCHAR}, #{goodsImg,jdbcType=VARCHAR}, ",
        "#{goodsPrice,jdbcType=DECIMAL}, #{goodsStock,jdbcType=INTEGER}, ",
        "#{goodsDetail,jdbcType=LONGVARCHAR})"
    })
    int insert(Goods record);

    @InsertProvider(type=GoodsSqlProvider.class, method="insertSelective")
    int insertSelective(Goods record);

    @SelectProvider(type=GoodsSqlProvider.class, method="selectByExampleWithBLOBs")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="goods_name", property="goodsName", jdbcType=JdbcType.VARCHAR),
        @Result(column="goods_title", property="goodsTitle", jdbcType=JdbcType.VARCHAR),
        @Result(column="goods_img", property="goodsImg", jdbcType=JdbcType.VARCHAR),
        @Result(column="goods_price", property="goodsPrice", jdbcType=JdbcType.DECIMAL),
        @Result(column="goods_stock", property="goodsStock", jdbcType=JdbcType.INTEGER),
        @Result(column="goods_detail", property="goodsDetail", jdbcType=JdbcType.LONGVARCHAR)
    })
    List<Goods> selectByExampleWithBLOBs(GoodsExample example);

    @SelectProvider(type=GoodsSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="goods_name", property="goodsName", jdbcType=JdbcType.VARCHAR),
        @Result(column="goods_title", property="goodsTitle", jdbcType=JdbcType.VARCHAR),
        @Result(column="goods_img", property="goodsImg", jdbcType=JdbcType.VARCHAR),
        @Result(column="goods_price", property="goodsPrice", jdbcType=JdbcType.DECIMAL),
        @Result(column="goods_stock", property="goodsStock", jdbcType=JdbcType.INTEGER)
    })
    List<Goods> selectByExample(GoodsExample example);

    @Select({
        "select",
        "id, goods_name, goods_title, goods_img, goods_price, goods_stock, goods_detail",
        "from goods",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="goods_name", property="goodsName", jdbcType=JdbcType.VARCHAR),
        @Result(column="goods_title", property="goodsTitle", jdbcType=JdbcType.VARCHAR),
        @Result(column="goods_img", property="goodsImg", jdbcType=JdbcType.VARCHAR),
        @Result(column="goods_price", property="goodsPrice", jdbcType=JdbcType.DECIMAL),
        @Result(column="goods_stock", property="goodsStock", jdbcType=JdbcType.INTEGER),
        @Result(column="goods_detail", property="goodsDetail", jdbcType=JdbcType.LONGVARCHAR)
    })
    Goods selectByPrimaryKey(Long id);

    @UpdateProvider(type=GoodsSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") Goods record, @Param("example") GoodsExample example);

    @UpdateProvider(type=GoodsSqlProvider.class, method="updateByExampleWithBLOBs")
    int updateByExampleWithBLOBs(@Param("record") Goods record, @Param("example") GoodsExample example);

    @UpdateProvider(type=GoodsSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") Goods record, @Param("example") GoodsExample example);

    @UpdateProvider(type=GoodsSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(Goods record);

    @Update({
        "update goods",
        "set goods_name = #{goodsName,jdbcType=VARCHAR},",
          "goods_title = #{goodsTitle,jdbcType=VARCHAR},",
          "goods_img = #{goodsImg,jdbcType=VARCHAR},",
          "goods_price = #{goodsPrice,jdbcType=DECIMAL},",
          "goods_stock = #{goodsStock,jdbcType=INTEGER},",
          "goods_detail = #{goodsDetail,jdbcType=LONGVARCHAR}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKeyWithBLOBs(Goods record);

    @Update({
        "update goods",
        "set goods_name = #{goodsName,jdbcType=VARCHAR},",
          "goods_title = #{goodsTitle,jdbcType=VARCHAR},",
          "goods_img = #{goodsImg,jdbcType=VARCHAR},",
          "goods_price = #{goodsPrice,jdbcType=DECIMAL},",
          "goods_stock = #{goodsStock,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(Goods record);
}