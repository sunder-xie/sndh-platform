package com.nhry.model.milktrans;

import com.nhry.data.milktrans.domain.TMstRefuseResend;
import com.sun.tools.javac.util.List;

import java.math.BigDecimal;

/**
 * Created by gongjk on 2016/10/19.
 */
public class UptReqOrderByResendItemMode {
    List<TMstRefuseResend> entries;
    String matnr;
    String reqOrderNo;
    BigDecimal qty;

    public List<TMstRefuseResend> getEntries() {
        return entries;
    }

    public void setEntries(List<TMstRefuseResend> entries) {
        this.entries = entries;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getReqOrderNo() {
        return reqOrderNo;
    }

    public void setReqOrderNo(String reqOrderNo) {
        this.reqOrderNo = reqOrderNo;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }
}
