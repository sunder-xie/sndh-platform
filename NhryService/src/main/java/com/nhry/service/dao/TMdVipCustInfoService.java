package com.nhry.service.dao;

import java.util.List;

import org.codehaus.jettison.json.JSONObject;

import com.nhry.domain.TMdVipCustInfo;

public interface TMdVipCustInfoService {
	int deleteByPrimaryKey(String vipCustNo);

    int insert(TMdVipCustInfo cust);

    TMdVipCustInfo selectByPrimaryKey(String vipCustNo);

    int updateByPrimaryKey(TMdVipCustInfo record);
    
    List<TMdVipCustInfo> allCust();
    
    TMdVipCustInfo findCustByPhone(String phone);
}
