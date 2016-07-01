package com.nhry.model.bill;

import java.util.Date;

/**
 * Created by gongjk on 2016/6/30.
 */
public class EmpDispDetailModel {
    private String branchNo;
    private String branchName;
    private String empNo;
    private String empName;
    private Date startDate;
    private Date endDate;
    private String dispMatnr;
    private String dispMatnrTxt;
    private int dispQty;
    private String giftMatnr;
    private String giftMatnrTxt;
    private int  giftQty;
    private String inSaleMatnr;
    private String inSaleMatnrTxt;
    private int inSaleQty;

    public String getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(String branchNo) {
        this.branchNo = branchNo;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
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

    public String getDispMatnr() {
        return dispMatnr;
    }

    public void setDispMatnr(String dispMatnr) {
        this.dispMatnr = dispMatnr;
    }

    public String getDispMatnrTxt() {
        return dispMatnrTxt;
    }

    public void setDispMatnrTxt(String dispMatnrTxt) {
        this.dispMatnrTxt = dispMatnrTxt;
    }

    public int getDispQty() {
        return dispQty;
    }

    public void setDispQty(int dispQty) {
        this.dispQty = dispQty;
    }

    public String getGiftMatnr() {
        return giftMatnr;
    }

    public void setGiftMatnr(String giftMatnr) {
        this.giftMatnr = giftMatnr;
    }

    public String getGiftMatnrTxt() {
        return giftMatnrTxt;
    }

    public void setGiftMatnrTxt(String giftMatnrTxt) {
        this.giftMatnrTxt = giftMatnrTxt;
    }

    public int getGiftQty() {
        return giftQty;
    }

    public void setGiftQty(int giftQty) {
        this.giftQty = giftQty;
    }

    public String getInSaleMatnr() {
        return inSaleMatnr;
    }

    public void setInSaleMatnr(String inSaleMatnr) {
        this.inSaleMatnr = inSaleMatnr;
    }

    public String getInSaleMatnrTxt() {
        return inSaleMatnrTxt;
    }

    public void setInSaleMatnrTxt(String inSaleMatnrTxt) {
        this.inSaleMatnrTxt = inSaleMatnrTxt;
    }

    public int getInSaleQty() {
        return inSaleQty;
    }

    public void setInSaleQty(int inSaleQty) {
        this.inSaleQty = inSaleQty;
    }
}
