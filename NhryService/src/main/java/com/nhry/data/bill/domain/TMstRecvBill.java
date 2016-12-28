package com.nhry.data.bill.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gongjk on 2016/6/23.
 */
public class TMstRecvBill implements Serializable {
    private String hadOffset;
    private String hadOffsetNo;
    private String receiptNo;
    private Date receiptDate;
    private String orderNo;
    private String paymentType;
    private String status;
    private BigDecimal amt;    //记录实际收款金额
    private BigDecimal suppAmt; //记录应收钱数
    private String vipCustNo;
    private String vipCustName;
    private String remark;
    private Date startTime;
    private Date endTime;
    private String paymentYearMonth;
    private BigDecimal totalPrice;
    private BigDecimal custAccAmt; //当时订户余额
    private int totalNum;
    private String recvEmp;
    private String recvEmpName;
    private BigDecimal accAmt;
    private Date createAt;
    private  String createBy;
    private String createByTxt;
    private Date lastModified;
    private String lastModifiedBy;
    private String lastModifiedByTxt;
    private BigDecimal discountAmt;

    public String getHadOffsetNo() {
        return hadOffsetNo;
    }

    public void setHadOffsetNo(String hadOffsetNo) {
        this.hadOffsetNo = hadOffsetNo;
    }

    public BigDecimal getDiscountAmt() {
        return discountAmt;
    }

    public void setDiscountAmt(BigDecimal discountAmt) {
        this.discountAmt = discountAmt;
    }

    public BigDecimal getSuppAmt() {
        return suppAmt;
    }

    public String getHadOffset() {
        return hadOffset;
    }

    public void setHadOffset(String hadOffset) {
        this.hadOffset = hadOffset;
    }

    public void setSuppAmt(BigDecimal suppAmt) {
        this.suppAmt = suppAmt;
    }

    public BigDecimal getCustAccAmt() {
        return custAccAmt;
    }

    public void setCustAccAmt(BigDecimal custAccAmt) {
        this.custAccAmt = custAccAmt;
    }

    public BigDecimal getAccAmt() {
        return accAmt;
    }

    public void setAccAmt(BigDecimal accAmt) {
        this.accAmt = accAmt;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public String getVipCustName() {
        return vipCustName;
    }

    public void setVipCustName(String vipCustName) {
        this.vipCustName = vipCustName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getReceiptNo() {
        return receiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        this.receiptNo = receiptNo;
    }

    public Date getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(Date receiptDate) {
        this.receiptDate = receiptDate;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getAmt() {
        return amt;
    }

    public void setAmt(BigDecimal amt) {
        this.amt = amt;
    }

    public String getVipCustNo() {
        return vipCustNo;
    }

    public void setVipCustNo(String vipCustNo) {
        this.vipCustNo = vipCustNo;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getPaymentYearMonth() {
        return paymentYearMonth;
    }

    public void setPaymentYearMonth(String paymentYearMonth) {
        this.paymentYearMonth = paymentYearMonth;
    }

    public String getRecvEmp() {
        return recvEmp;
    }

    public void setRecvEmp(String recvEmp) {
        this.recvEmp = recvEmp;
    }

    public String getRecvEmpName() {
        return recvEmpName;
    }

    public void setRecvEmpName(String recvEmpName) {
        this.recvEmpName = recvEmpName;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreateByTxt() {
        return createByTxt;
    }

    public void setCreateByTxt(String createByTxt) {
        this.createByTxt = createByTxt;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(String lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public String getLastModifiedByTxt() {
        return lastModifiedByTxt;
    }

    public void setLastModifiedByTxt(String lastModifiedByTxt) {
        this.lastModifiedByTxt = lastModifiedByTxt;
    }
}
