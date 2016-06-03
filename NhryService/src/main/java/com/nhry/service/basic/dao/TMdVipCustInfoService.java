package com.nhry.service.basic.dao;

import java.util.List;

import org.codehaus.jettison.json.JSONObject;

import com.nhry.data.basic.domain.TMdVipCustInfo;

public interface TMdVipCustInfoService {
	int deleteByPrimaryKey(String vipCustNo);

    int insert(TMdVipCustInfo cust);

    TMdVipCustInfo selectByPrimaryKey(String vipCustNo);

    int updateByPrimaryKey(TMdVipCustInfo record);
    
    List<TMdVipCustInfo> allCust();
    
    TMdVipCustInfo findCustByPhone(String phone);
}
