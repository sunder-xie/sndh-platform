package com.nhry.data.basic.domain;

import java.io.Serializable;

public class TMaraSalesKey implements Serializable{
    private String maraId;

    private String salesOrg;

    public String getMaraId() {
        return maraId;
    }

    public void setMaraId(String maraId) {
        this.maraId = maraId == null ? null : maraId.trim();
    }

    public String getSalesOrg() {
        return salesOrg;
    }

    public void setSalesOrg(String salesOrg) {
        this.salesOrg = salesOrg == null ? null : salesOrg.trim();
    }
}