package com.yangls.miaosha.dao;

import com.yangls.miaosha.model.MiaoshaUser;
import com.yangls.miaosha.model.MiaoshaUserExample.Criteria;
import com.yangls.miaosha.model.MiaoshaUserExample.Criterion;
import com.yangls.miaosha.model.MiaoshaUserExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class MiaoshaUserSqlProvider {

    public String countByExample(MiaoshaUserExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("miaosha_user");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(MiaoshaUserExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("miaosha_user");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(MiaoshaUser record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("miaosha_user");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getNickname() != null) {
            sql.VALUES("nickname", "#{nickname,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.VALUES("password", "#{password,jdbcType=VARCHAR}");
        }
        
        if (record.getSalt() != null) {
            sql.VALUES("salt", "#{salt,jdbcType=VARCHAR}");
        }
        
        if (record.getHead() != null) {
            sql.VALUES("head", "#{head,jdbcType=VARCHAR}");
        }
        
        if (record.getRegisterDate() != null) {
            sql.VALUES("register_date", "#{registerDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLastLoginDate() != null) {
            sql.VALUES("last_login_date", "#{lastLoginDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLoginCount() != null) {
            sql.VALUES("login_count", "#{loginCount,jdbcType=INTEGER}");
        }
        
        return sql.toString();
    }

    public String selectByExample(MiaoshaUserExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("nickname");
        sql.SELECT("password");
        sql.SELECT("salt");
        sql.SELECT("head");
        sql.SELECT("register_date");
        sql.SELECT("last_login_date");
        sql.SELECT("login_count");
        sql.FROM("miaosha_user");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        MiaoshaUser record = (MiaoshaUser) parameter.get("record");
        MiaoshaUserExample example = (MiaoshaUserExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("miaosha_user");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=BIGINT}");
        }
        
        if (record.getNickname() != null) {
            sql.SET("nickname = #{record.nickname,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.SET("password = #{record.password,jdbcType=VARCHAR}");
        }
        
        if (record.getSalt() != null) {
            sql.SET("salt = #{record.salt,jdbcType=VARCHAR}");
        }
        
        if (record.getHead() != null) {
            sql.SET("head = #{record.head,jdbcType=VARCHAR}");
        }
        
        if (record.getRegisterDate() != null) {
            sql.SET("register_date = #{record.registerDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLastLoginDate() != null) {
            sql.SET("last_login_date = #{record.lastLoginDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLoginCount() != null) {
            sql.SET("login_count = #{record.loginCount,jdbcType=INTEGER}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("miaosha_user");
        
        sql.SET("id = #{record.id,jdbcType=BIGINT}");
        sql.SET("nickname = #{record.nickname,jdbcType=VARCHAR}");
        sql.SET("password = #{record.password,jdbcType=VARCHAR}");
        sql.SET("salt = #{record.salt,jdbcType=VARCHAR}");
        sql.SET("head = #{record.head,jdbcType=VARCHAR}");
        sql.SET("register_date = #{record.registerDate,jdbcType=TIMESTAMP}");
        sql.SET("last_login_date = #{record.lastLoginDate,jdbcType=TIMESTAMP}");
        sql.SET("login_count = #{record.loginCount,jdbcType=INTEGER}");
        
        MiaoshaUserExample example = (MiaoshaUserExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(MiaoshaUser record) {
        SQL sql = new SQL();
        sql.UPDATE("miaosha_user");
        
        if (record.getNickname() != null) {
            sql.SET("nickname = #{nickname,jdbcType=VARCHAR}");
        }
        
        if (record.getPassword() != null) {
            sql.SET("password = #{password,jdbcType=VARCHAR}");
        }
        
        if (record.getSalt() != null) {
            sql.SET("salt = #{salt,jdbcType=VARCHAR}");
        }
        
        if (record.getHead() != null) {
            sql.SET("head = #{head,jdbcType=VARCHAR}");
        }
        
        if (record.getRegisterDate() != null) {
            sql.SET("register_date = #{registerDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLastLoginDate() != null) {
            sql.SET("last_login_date = #{lastLoginDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getLoginCount() != null) {
            sql.SET("login_count = #{loginCount,jdbcType=INTEGER}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, MiaoshaUserExample example, boolean includeExamplePhrase) {
        if (example == null) {
            return;
        }
        
        String parmPhrase1;
        String parmPhrase1_th;
        String parmPhrase2;
        String parmPhrase2_th;
        String parmPhrase3;
        String parmPhrase3_th;
        if (includeExamplePhrase) {
            parmPhrase1 = "%s #{example.oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{example.oredCriteria[%d].allCriteria[%d].value} and #{example.oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{example.oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{example.oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{example.oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{example.oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        } else {
            parmPhrase1 = "%s #{oredCriteria[%d].allCriteria[%d].value}";
            parmPhrase1_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s}";
            parmPhrase2 = "%s #{oredCriteria[%d].allCriteria[%d].value} and #{oredCriteria[%d].criteria[%d].secondValue}";
            parmPhrase2_th = "%s #{oredCriteria[%d].allCriteria[%d].value,typeHandler=%s} and #{oredCriteria[%d].criteria[%d].secondValue,typeHandler=%s}";
            parmPhrase3 = "#{oredCriteria[%d].allCriteria[%d].value[%d]}";
            parmPhrase3_th = "#{oredCriteria[%d].allCriteria[%d].value[%d],typeHandler=%s}";
        }
        
        StringBuilder sb = new StringBuilder();
        List<Criteria> oredCriteria = example.getOredCriteria();
        boolean firstCriteria = true;
        for (int i = 0; i < oredCriteria.size(); i++) {
            Criteria criteria = oredCriteria.get(i);
            if (criteria.isValid()) {
                if (firstCriteria) {
                    firstCriteria = false;
                } else {
                    sb.append(" or ");
                }
                
                sb.append('(');
                List<Criterion> criterions = criteria.getAllCriteria();
                boolean firstCriterion = true;
                for (int j = 0; j < criterions.size(); j++) {
                    Criterion criterion = criterions.get(j);
                    if (firstCriterion) {
                        firstCriterion = false;
                    } else {
                        sb.append(" and ");
                    }
                    
                    if (criterion.isNoValue()) {
                        sb.append(criterion.getCondition());
                    } else if (criterion.isSingleValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase1, criterion.getCondition(), i, j));
                        } else {
                            sb.append(String.format(parmPhrase1_th, criterion.getCondition(), i, j,criterion.getTypeHandler()));
                        }
                    } else if (criterion.isBetweenValue()) {
                        if (criterion.getTypeHandler() == null) {
                            sb.append(String.format(parmPhrase2, criterion.getCondition(), i, j, i, j));
                        } else {
                            sb.append(String.format(parmPhrase2_th, criterion.getCondition(), i, j, criterion.getTypeHandler(), i, j, criterion.getTypeHandler()));
                        }
                    } else if (criterion.isListValue()) {
                        sb.append(criterion.getCondition());
                        sb.append(" (");
                        List<?> listItems = (List<?>) criterion.getValue();
                        boolean comma = false;
                        for (int k = 0; k < listItems.size(); k++) {
                            if (comma) {
                                sb.append(", ");
                            } else {
                                comma = true;
                            }
                            if (criterion.getTypeHandler() == null) {
                                sb.append(String.format(parmPhrase3, i, j, k));
                            } else {
                                sb.append(String.format(parmPhrase3_th, i, j, k, criterion.getTypeHandler()));
                            }
                        }
                        sb.append(')');
                    }
                }
                sb.append(')');
            }
        }
        
        if (sb.length() > 0) {
            sql.WHERE(sb.toString());
        }
    }
}