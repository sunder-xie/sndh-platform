package com.nhry.data.dao;

import java.util.List;

import com.nhry.domain.TMdVipCustInfo;

public interface TMdVipCustInfoMapper {
    int deleteByPrimaryKey(String vipCustNo);

    int insert(TMdVipCustInfo record);

    int insertSelective(TMdVipCustInfo record);

    TMdVipCustInfo selectByPrimaryKey(String vipCustNo);

    int updateByPrimaryKeySelective(TMdVipCustInfo record);

    int updateByPrimaryKey(TMdVipCustInfo record);
    
    List<TMdVipCustInfo> all();
}