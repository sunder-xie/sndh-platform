package com.nhry.data.dao;

import com.nhry.domain.TMdMaraEx;

public interface TMdMaraExMapper {
    int deleteByPrimaryKey(String matnr);

    int insert(TMdMaraEx record);

    int insertSelective(TMdMaraEx record);

    TMdMaraEx selectByPrimaryKey(String matnr);

    int updateByPrimaryKeySelective(TMdMaraEx record);

    int updateByPrimaryKey(TMdMaraEx record);
}