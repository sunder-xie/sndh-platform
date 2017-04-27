package com.nhry.model.basic;

import com.wordnik.swagger.annotations.ApiModel;

import java.io.Serializable;

/**
 * Created by huaguan on 2017/4/26.
 */
@ApiModel(value = "OrgPriceModel", description = "机构价格信息查询对象")
public class OrgPriceModel extends BaseQueryModel implements Serializable {
    private String orgId;
    private String status;//状态
    private String isShow;//是否显示
    private String salesOrg;
    private String matnr;

    public String getMatnr() {
        return matnr;
    }

    public void setMatnr(String matnr) {
        this.matnr = matnr;
    }

    public String getSalesOrg() {
        return salesOrg;
    }

    public void setSalesOrg(String salesOrg) {
        this.salesOrg = salesOrg;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIsShow() {
        return isShow;
    }

    public void setIsShow(String isShow) {
        this.isShow = isShow;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
