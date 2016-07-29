package com.nhry.model.webService;

import java.io.Serializable;

/**
 * Created by gongjk on 2016/7/28.
 */
public class CustInfoModel implements Serializable{
    private String province;
    private String city;
    private String district;
    private String street;
    private String townName;
    private String address;
    private String addrGuid;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getTownName() {
        return townName;
    }

    public void setTownName(String townName) {
        this.townName = townName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddrGuid() {
        return addrGuid;
    }

    public void setAddrGuid(String addrGuid) {
        this.addrGuid = addrGuid;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }
}
