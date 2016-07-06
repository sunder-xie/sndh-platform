package com.nhry.data.basic.dao;

import com.nhry.data.basic.domain.TMdMaraEx;

import java.util.List;
import java.util.Map;

public interface TMdMaraExMapper {

    int uptProductExByCode(TMdMaraEx record);
    
    int addMaraEx(TMdMaraEx record);

    TMdMaraEx getProductTransRateByCode(String matnr, String salesOrg);

    int uptProductExByCodeAndSalesOrg(TMdMaraEx ex);

    List<TMdMaraEx> getProductsBySalesOrg(String salesOrg);

    TMdMaraEx findProductExByCode(Map<String,String> attrs);
}