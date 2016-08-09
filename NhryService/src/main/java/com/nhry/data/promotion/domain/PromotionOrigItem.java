package com.nhry.data.promotion.domain;

import java.math.BigDecimal;

public class PromotionOrigItem extends PromotionOrigItemKey {
    private String goodNo;

    private BigDecimal origNum;

    private String unit;

    public String getGoodNo() {
        return goodNo;
    }

    public void setGoodNo(String goodNo) {
        this.goodNo = goodNo == null ? null : goodNo.trim();
    }

    public BigDecimal getOrigNum() {
        return origNum;
    }

    public void setOrigNum(BigDecimal origNum) {
        this.origNum = origNum;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }
}