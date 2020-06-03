package com.yangls.miaosha.vo;

import com.yangls.miaosha.model.Goods;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @description: 秒杀商品列表
 * @author: yangLs
 * @create: 2020-06-03 15:47
 **/
public class GoodsVo extends Goods{
    private BigDecimal miaoshaPrice;

    private Integer stockCount;

    private Date startDate;

    private Date endDate;

    public BigDecimal getMiaoshaPrice() {
        return miaoshaPrice;
    }

    public void setMiaoshaPrice(BigDecimal miaoshaPrice) {
        this.miaoshaPrice = miaoshaPrice;
    }

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
