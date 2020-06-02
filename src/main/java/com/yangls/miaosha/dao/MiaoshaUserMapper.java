package com.yangls.miaosha.dao;

import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.model.MiaoshaUserExample;
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

public interface MiaoshaUserMapper {
    @SelectProvider(type=MiaoshaUserSqlProvider.class, method="countByExample")
    long countByExample(MiaoshaUserExample example);

    @DeleteProvider(type=MiaoshaUserSqlProvider.class, method="deleteByExample")
    int deleteByExample(MiaoshaUserExample example);

    @Delete({
        "delete from miaosha_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int deleteByPrimaryKey(Long id);

    @Insert({
        "insert into miaosha_user (id, nickname, ",
        "password, salt, ",
        "head, register_date, ",
        "last_login_date, login_count)",
        "values (#{id,jdbcType=BIGINT}, #{nickname,jdbcType=VARCHAR}, ",
        "#{password,jdbcType=VARCHAR}, #{salt,jdbcType=VARCHAR}, ",
        "#{head,jdbcType=VARCHAR}, #{registerDate,jdbcType=TIMESTAMP}, ",
        "#{lastLoginDate,jdbcType=TIMESTAMP}, #{loginCount,jdbcType=INTEGER})"
    })
    int insert(MiaoshaUser record);

    @InsertProvider(type=MiaoshaUserSqlProvider.class, method="insertSelective")
    int insertSelective(MiaoshaUser record);

    @SelectProvider(type=MiaoshaUserSqlProvider.class, method="selectByExample")
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="nickname", property="nickname", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="salt", property="salt", jdbcType=JdbcType.VARCHAR),
        @Result(column="head", property="head", jdbcType=JdbcType.VARCHAR),
        @Result(column="register_date", property="registerDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_login_date", property="lastLoginDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="login_count", property="loginCount", jdbcType=JdbcType.INTEGER)
    })
    List<MiaoshaUser> selectByExample(MiaoshaUserExample example);

    @Select({
        "select",
        "id, nickname, password, salt, head, register_date, last_login_date, login_count",
        "from miaosha_user",
        "where id = #{id,jdbcType=BIGINT}"
    })
    @Results({
        @Result(column="id", property="id", jdbcType=JdbcType.BIGINT, id=true),
        @Result(column="nickname", property="nickname", jdbcType=JdbcType.VARCHAR),
        @Result(column="password", property="password", jdbcType=JdbcType.VARCHAR),
        @Result(column="salt", property="salt", jdbcType=JdbcType.VARCHAR),
        @Result(column="head", property="head", jdbcType=JdbcType.VARCHAR),
        @Result(column="register_date", property="registerDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="last_login_date", property="lastLoginDate", jdbcType=JdbcType.TIMESTAMP),
        @Result(column="login_count", property="loginCount", jdbcType=JdbcType.INTEGER)
    })
    MiaoshaUser selectByPrimaryKey(Long id);

    @UpdateProvider(type=MiaoshaUserSqlProvider.class, method="updateByExampleSelective")
    int updateByExampleSelective(@Param("record") MiaoshaUser record, @Param("example") MiaoshaUserExample example);

    @UpdateProvider(type=MiaoshaUserSqlProvider.class, method="updateByExample")
    int updateByExample(@Param("record") MiaoshaUser record, @Param("example") MiaoshaUserExample example);

    @UpdateProvider(type=MiaoshaUserSqlProvider.class, method="updateByPrimaryKeySelective")
    int updateByPrimaryKeySelective(MiaoshaUser record);

    @Update({
        "update miaosha_user",
        "set nickname = #{nickname,jdbcType=VARCHAR},",
          "password = #{password,jdbcType=VARCHAR},",
          "salt = #{salt,jdbcType=VARCHAR},",
          "head = #{head,jdbcType=VARCHAR},",
          "register_date = #{registerDate,jdbcType=TIMESTAMP},",
          "last_login_date = #{lastLoginDate,jdbcType=TIMESTAMP},",
          "login_count = #{loginCount,jdbcType=INTEGER}",
        "where id = #{id,jdbcType=BIGINT}"
    })
    int updateByPrimaryKey(MiaoshaUser record);
}