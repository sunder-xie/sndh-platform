package com.nhry.pojo.query;

import java.io.Serializable;

/**
 * Created by gongjk on 2016/6/3.
 */
public class ResidentialAreaModel extends BaseQueryModel implements Serializable {
    private String province;
    private String city;
    private String status;

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
