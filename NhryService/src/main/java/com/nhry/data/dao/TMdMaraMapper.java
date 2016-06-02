package com.nhry.data.dao;

import com.nhry.domain.TMdMara;

public interface TMdMaraMapper {
    int deleteByPrimaryKey(String matnr);

    int insert(TMdMara record);

    int insertSelective(TMdMara record);

    TMdMara selectByPrimaryKey(String matnr);

    int updateByPrimaryKeySelective(TMdMara record);

    int updateByPrimaryKey(TMdMara record);
}