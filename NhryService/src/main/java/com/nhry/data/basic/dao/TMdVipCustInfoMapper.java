package com.nhry.data.basic.dao;

import java.util.List;

import com.nhry.data.basic.domain.TMdVipCustInfo;

public interface TMdVipCustInfoMapper {
    int deleteByPrimaryKey(String vipCustNo);

    int insert(TMdVipCustInfo record);

    int insertSelective(TMdVipCustInfo record);

    TMdVipCustInfo selectByPrimaryKey(String vipCustNo);

    int updateByPrimaryKeySelective(TMdVipCustInfo record);

    int updateByPrimaryKey(TMdVipCustInfo record);
    
    List<TMdVipCustInfo> all();
    
    TMdVipCustInfo findCustByPhone(String phone);
}