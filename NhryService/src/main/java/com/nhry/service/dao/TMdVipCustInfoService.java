package com.nhry.service.dao;

import java.util.List;

import org.codehaus.jettison.json.JSONObject;

import com.nhry.domain.TMdVipCustInfo;

public interface TMdVipCustInfoService {
	int deleteByPrimaryKey(String vipCustNo);

    int insert(JSONObject record);

    TMdVipCustInfo selectByPrimaryKey(String vipCustNo);

    int updateByPrimaryKey(TMdVipCustInfo record);
    
    List<TMdVipCustInfo> allCust();
}
