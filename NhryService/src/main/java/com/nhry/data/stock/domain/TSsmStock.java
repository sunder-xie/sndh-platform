package com.nhry.data.stock.domain;

import java.math.BigDecimal;

public class TSsmStock extends TSsmStockKey {
    private String unit;

    private BigDecimal qty;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }
}