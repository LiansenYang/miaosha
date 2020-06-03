package com.yangls.miaosha.dao;

import com.yangls.miaosha.model.MiaoshaGoods;
import com.yangls.miaosha.model.MiaoshaGoodsExample.Criteria;
import com.yangls.miaosha.model.MiaoshaGoodsExample.Criterion;
import com.yangls.miaosha.model.MiaoshaGoodsExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.jdbc.SQL;

public class MiaoshaGoodsSqlProvider {

    public String countByExample(MiaoshaGoodsExample example) {
        SQL sql = new SQL();
        sql.SELECT("count(*)").FROM("miaosha_goods");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String deleteByExample(MiaoshaGoodsExample example) {
        SQL sql = new SQL();
        sql.DELETE_FROM("miaosha_goods");
        applyWhere(sql, example, false);
        return sql.toString();
    }

    public String insertSelective(MiaoshaGoods record) {
        SQL sql = new SQL();
        sql.INSERT_INTO("miaosha_goods");
        
        if (record.getId() != null) {
            sql.VALUES("id", "#{id,jdbcType=BIGINT}");
        }
        
        if (record.getGoodsId() != null) {
            sql.VALUES("goods_id", "#{goodsId,jdbcType=BIGINT}");
        }
        
        if (record.getMiaoshaPrice() != null) {
            sql.VALUES("miaosha_price", "#{miaoshaPrice,jdbcType=DECIMAL}");
        }
        
        if (record.getStockCount() != null) {
            sql.VALUES("stock_count", "#{stockCount,jdbcType=INTEGER}");
        }
        
        if (record.getStartDate() != null) {
            sql.VALUES("start_date", "#{startDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndDate() != null) {
            sql.VALUES("end_date", "#{endDate,jdbcType=TIMESTAMP}");
        }
        
        return sql.toString();
    }

    public String selectByExample(MiaoshaGoodsExample example) {
        SQL sql = new SQL();
        if (example != null && example.isDistinct()) {
            sql.SELECT_DISTINCT("id");
        } else {
            sql.SELECT("id");
        }
        sql.SELECT("goods_id");
        sql.SELECT("miaosha_price");
        sql.SELECT("stock_count");
        sql.SELECT("start_date");
        sql.SELECT("end_date");
        sql.FROM("miaosha_goods");
        applyWhere(sql, example, false);
        
        if (example != null && example.getOrderByClause() != null) {
            sql.ORDER_BY(example.getOrderByClause());
        }
        
        return sql.toString();
    }

    public String updateByExampleSelective(Map<String, Object> parameter) {
        MiaoshaGoods record = (MiaoshaGoods) parameter.get("record");
        MiaoshaGoodsExample example = (MiaoshaGoodsExample) parameter.get("example");
        
        SQL sql = new SQL();
        sql.UPDATE("miaosha_goods");
        
        if (record.getId() != null) {
            sql.SET("id = #{record.id,jdbcType=BIGINT}");
        }
        
        if (record.getGoodsId() != null) {
            sql.SET("goods_id = #{record.goodsId,jdbcType=BIGINT}");
        }
        
        if (record.getMiaoshaPrice() != null) {
            sql.SET("miaosha_price = #{record.miaoshaPrice,jdbcType=DECIMAL}");
        }
        
        if (record.getStockCount() != null) {
            sql.SET("stock_count = #{record.stockCount,jdbcType=INTEGER}");
        }
        
        if (record.getStartDate() != null) {
            sql.SET("start_date = #{record.startDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndDate() != null) {
            sql.SET("end_date = #{record.endDate,jdbcType=TIMESTAMP}");
        }
        
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByExample(Map<String, Object> parameter) {
        SQL sql = new SQL();
        sql.UPDATE("miaosha_goods");
        
        sql.SET("id = #{record.id,jdbcType=BIGINT}");
        sql.SET("goods_id = #{record.goodsId,jdbcType=BIGINT}");
        sql.SET("miaosha_price = #{record.miaoshaPrice,jdbcType=DECIMAL}");
        sql.SET("stock_count = #{record.stockCount,jdbcType=INTEGER}");
        sql.SET("start_date = #{record.startDate,jdbcType=TIMESTAMP}");
        sql.SET("end_date = #{record.endDate,jdbcType=TIMESTAMP}");
        
        MiaoshaGoodsExample example = (MiaoshaGoodsExample) parameter.get("example");
        applyWhere(sql, example, true);
        return sql.toString();
    }

    public String updateByPrimaryKeySelective(MiaoshaGoods record) {
        SQL sql = new SQL();
        sql.UPDATE("miaosha_goods");
        
        if (record.getGoodsId() != null) {
            sql.SET("goods_id = #{goodsId,jdbcType=BIGINT}");
        }
        
        if (record.getMiaoshaPrice() != null) {
            sql.SET("miaosha_price = #{miaoshaPrice,jdbcType=DECIMAL}");
        }
        
        if (record.getStockCount() != null) {
            sql.SET("stock_count = #{stockCount,jdbcType=INTEGER}");
        }
        
        if (record.getStartDate() != null) {
            sql.SET("start_date = #{startDate,jdbcType=TIMESTAMP}");
        }
        
        if (record.getEndDate() != null) {
            sql.SET("end_date = #{endDate,jdbcType=TIMESTAMP}");
        }
        
        sql.WHERE("id = #{id,jdbcType=BIGINT}");
        
        return sql.toString();
    }

    protected void applyWhere(SQL sql, MiaoshaGoodsExample example, boolean includeExamplePhrase) {
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