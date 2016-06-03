package com.nhry.data.config.dao;

import com.nhry.data.config.domain.NHSysCodeType;

public interface NHSysCodeTypeMapper {
    int deleteByPrimaryKey(String typeCode);

    int insert(NHSysCodeType record);

    int insertSelective(NHSysCodeType record);

    NHSysCodeType selectByPrimaryKey(String typeCode);

    int updateByPrimaryKeySelective(NHSysCodeType record);

    int updateByPrimaryKey(NHSysCodeType record);
}