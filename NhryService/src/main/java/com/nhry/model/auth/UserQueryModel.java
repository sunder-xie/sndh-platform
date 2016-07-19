package com.nhry.model.auth;

import com.nhry.model.basic.BaseQueryModel;

import java.io.Serializable;
import java.util.Date;

public class UserQueryModel extends BaseQueryModel implements Serializable {
    private String uname;
    private String roleId;
    private String salesOrg;

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getSalesOrg() {
        return salesOrg;
    }

    public void setSalesOrg(String salesOrg) {
        this.salesOrg = salesOrg;
    }
}
