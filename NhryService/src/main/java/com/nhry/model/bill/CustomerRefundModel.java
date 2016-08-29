package com.nhry.model.bill;

import java.math.BigDecimal;

/**
 * Created by gongjk on 2016/8/29.
 */
public class CustomerRefundModel {
    private String vipCustNo;
    private String remark;
    private BigDecimal refundAmount;

    public String getVipCustNo() {
        return vipCustNo;
    }

    public void setVipCustNo(String vipCustNo) {
        this.vipCustNo = vipCustNo;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public BigDecimal getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(BigDecimal refundAmount) {
        this.refundAmount = refundAmount;
    }
}
