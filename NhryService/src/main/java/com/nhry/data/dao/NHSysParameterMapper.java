package com.nhry.data.dao;

import com.nhry.domain.NHSysParameter;

public interface NHSysParameterMapper {
    int deleteByPrimaryKey(String paramCode);

    int insert(NHSysParameter record);

    int insertSelective(NHSysParameter record);

    NHSysParameter selectByPrimaryKey(String paramCode);

    int updateByPrimaryKeySelective(NHSysParameter record);

    int updateByPrimaryKey(NHSysParameter record);
}