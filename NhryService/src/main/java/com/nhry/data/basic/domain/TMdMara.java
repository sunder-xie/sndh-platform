package com.nhry.data.basic.domain;

import java.math.BigDecimal;
import java.util.Date;

public class TMdMara {
    private String matnr;

    private String matnrTxt;

    private String baseUnit;

    private String status;

    private Date lastModified;

    private Date createAt;

    private String firstCat;

    private String secCate;

    private String brand;

    private BigDecimal weight;

    private String weightUnit;

    private String importantPrdFlag;

    private String spec;

    private String specUnit;

    private String botSpec;

    private String zbotCode;

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr == null ? null : matnr.trim();
    }

    public String getMatnrTxt() {
        return matnrTxt;
    }

    public void setMatnrTxt(String matnrTxt) {
        this.matnrTxt = matnrTxt == null ? null : matnrTxt.trim();
    }

    public String getBaseUnit() {
        return baseUnit;
    }

    public void setBaseUnit(String baseUnit) {
        this.baseUnit = baseUnit == null ? null : baseUnit.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Date getCreateAt() {
        return createAt;
    }

    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    public String getFirstCat() {
        return firstCat;
    }

    public void setFirstCat(String firstCat) {
        this.firstCat = firstCat == null ? null : firstCat.trim();
    }

    public String getSecCate() {
        return secCate;
    }

    public void setSecCate(String secCate) {
        this.secCate = secCate == null ? null : secCate.trim();
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand == null ? null : brand.trim();
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public String getWeightUnit() {
        return weightUnit;
    }

    public void setWeightUnit(String weightUnit) {
        this.weightUnit = weightUnit == null ? null : weightUnit.trim();
    }

    public String getImportantPrdFlag() {
        return importantPrdFlag;
    }

    public void setImportantPrdFlag(String importantPrdFlag) {
        this.importantPrdFlag = importantPrdFlag == null ? null : importantPrdFlag.trim();
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec == null ? null : spec.trim();
    }

    public String getSpecUnit() {
        return specUnit;
    }

    public void setSpecUnit(String specUnit) {
        this.specUnit = specUnit == null ? null : specUnit.trim();
    }

    public String getBotSpec() {
        return botSpec;
    }

    public void setBotSpec(String botSpec) {
        this.botSpec = botSpec == null ? null : botSpec.trim();
    }

    public String getZbotCode() {
        return zbotCode;
    }

    public void setZbotCode(String zbotCode) {
        this.zbotCode = zbotCode == null ? null : zbotCode.trim();
    }
}