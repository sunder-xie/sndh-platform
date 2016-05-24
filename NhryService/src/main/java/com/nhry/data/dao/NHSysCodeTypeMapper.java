package com.nhry.data.dao;

import com.nhry.domain.NHSysCodeType;

public interface NHSysCodeTypeMapper {
    int deleteByPrimaryKey(String typeCode);

    int insert(NHSysCodeType record);

    int insertSelective(NHSysCodeType record);

    NHSysCodeType selectByPrimaryKey(String typeCode);

    int updateByPrimaryKeySelective(NHSysCodeType record);

    int updateByPrimaryKey(NHSysCodeType record);
}