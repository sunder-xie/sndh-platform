package com.nhry.data.milktrans.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gongjk on 2016/6/30.
 */
public class TMstInsideSalOrderItem implements Serializable {
    private String insOrderNo;
    private Date orderDate;
    private String itemNo;
    private String orgOrderNo;
    private String matnr;
    private BigDecimal qty;
    private String reason;
    private BigDecimal price;
    private String spec;
    private String secCate;
    private String zbotCode;

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getSecCate() {
        return secCate;
    }

    public void setSecCate(String secCate) {
        this.secCate = secCate;
    }

    public String getZbotCode() {
        return zbotCode;
    }

    public void setZbotCode(String zbotCode) {
        this.zbotCode = zbotCode;
    }

    public String getInsOrderNo() {
        return insOrderNo;
    }

    public void setInsOrderNo(String insOrderNo) {
        this.insOrderNo = insOrderNo;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    public String getItemNo() {
        return itemNo;
    }

    public void setItemNo(String itemNo) {
        this.itemNo = itemNo;
    }

    public String getOrgOrderNo() {
        return orgOrderNo;
    }

    public void setOrgOrderNo(String orgOrderNo) {
        this.orgOrderNo = orgOrderNo;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
