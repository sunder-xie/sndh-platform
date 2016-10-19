package com.nhry.data.milktrans.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gongjk on 2016/10/18.
 *拒收复送记录表
 */
public class TMstRefuseResend implements Serializable {
    private String resendOrderNo;       //主键
    private String salesOrg;            //
    private String branchNo;
    private String empNo;       //送奶员编号
    private String empName;     //送奶员名称
    private String matnr;
    private String matnrTxt;
    private String status;
    private BigDecimal qty;
    private BigDecimal confirmQty;
    private BigDecimal remainQty;
    private Date  dispDate;
    private String dispOrderNo;


    public BigDecimal getRemainQty() {
        return remainQty;
    }

    public void setRemainQty(BigDecimal remainQty) {
        this.remainQty = remainQty;
    }

    public String getResendOrderNo() {
        return resendOrderNo;
    }

    public void setResendOrderNo(String resendOrderNo) {
        this.resendOrderNo = resendOrderNo;
    }

    public String getSalesOrg() {
        return salesOrg;
    }

    public void setSalesOrg(String salesOrg) {
        this.salesOrg = salesOrg;
    }

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getMatnrTxt() {
        return matnrTxt;
    }

    public void setMatnrTxt(String matnrTxt) {
        this.matnrTxt = matnrTxt;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public BigDecimal getQty() {
        return qty;
    }

    public void setQty(BigDecimal qty) {
        this.qty = qty;
    }

    public BigDecimal getConfirmQty() {
        return confirmQty;
    }

    public void setConfirmQty(BigDecimal confirmQty) {
        this.confirmQty = confirmQty;
    }

    public Date getDispDate() {
        return dispDate;
    }

    public void setDispDate(Date dispDate) {
        this.dispDate = dispDate;
    }

    public String getDispOrderNo() {
        return dispOrderNo;
    }

    public void setDispOrderNo(String dispOrderNo) {
        this.dispOrderNo = dispOrderNo;
    }
}
