package com.nhry.data.bill.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by gongjk on 2016/7/12.
 */
public class TMdEmpSal implements Serializable {
    private String empSalLsh;   //员工工资单流水号
    private String empNo;       //送奶工编号
    private int dispNum;        //总配送数量
    private BigDecimal totalSal;//总工资
    private Date setDate;       //结算日期
    private BigDecimal compRate;//投诉率
    private BigDecimal recRate; //回收率
    private BigDecimal dispRate; //配送率
    private BigDecimal compSal; //投诉费
    private BigDecimal recSal;  //回收费
    private BigDecimal dispSal; //配送费


    private Date createAt;
    private String createBy;
    private String createByTxt;
    public String getEmpSalLsh() {
        return empSalLsh;
    }


    public BigDecimal getCompRate() {
        return compRate;
    }

    public void setCompRate(BigDecimal compRate) {
        this.compRate = compRate;
    }

    public BigDecimal getCompSal() {
        return compSal;
    }

    public void setCompSal(BigDecimal compSal) {
        this.compSal = compSal;
    }

    public BigDecimal getDispRate() {
        return dispRate;
    }

    public void setDispRate(BigDecimal dispRate) {
        this.dispRate = dispRate;
    }

    public BigDecimal getDispSal() {
        return dispSal;
    }

    public void setDispSal(BigDecimal dispSal) {
        this.dispSal = dispSal;
    }

    public BigDecimal getRecRate() {
        return recRate;
    }

    public void setRecRate(BigDecimal recRate) {
        this.recRate = recRate;
    }

    public BigDecimal getRecSal() {
        return recSal;
    }

    public void setRecSal(BigDecimal recSal) {
        this.recSal = recSal;
    }

    public void setEmpSalLsh(String empSalLsh) {
        this.empSalLsh = empSalLsh;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public int getDispNum() {
        return dispNum;
    }

    public void setDispNum(int dispNum) {
        this.dispNum = dispNum;
    }

    public BigDecimal getTotalSal() {
        return totalSal;
    }

    public void setTotalSal(BigDecimal totalSal) {
        this.totalSal = totalSal;
    }

    public Date getSetDate() {
        return setDate;
    }

    public void setSetDate(Date setDate) {
        this.setDate = setDate;
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
}
