package com.nhry.data.basic.domain;

public class TSsmReqGoodsOrderItem extends TSsmReqGoodsOrderItemKey {
    private String matnr;

    private String unit;

    private Integer qty;

    private Integer increQty;

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr == null ? null : matnr.trim();
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit == null ? null : unit.trim();
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getIncreQty() {
        return increQty;
    }

    public void setIncreQty(Integer increQty) {
        this.increQty = increQty;
    }
}