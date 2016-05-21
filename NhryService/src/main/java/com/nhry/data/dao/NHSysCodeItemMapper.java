package com.nhry.data.dao;

import com.nhry.domain.NHSysCodeItem;
import com.nhry.domain.NHSysCodeItemKey;

public interface NHSysCodeItemMapper {
    int deleteByPrimaryKey(NHSysCodeItemKey key);

    int insert(NHSysCodeItem record);

    int insertSelective(NHSysCodeItem record);

    NHSysCodeItem selectByPrimaryKey(NHSysCodeItemKey key);

    int updateByPrimaryKeySelective(NHSysCodeItem record);

    int updateByPrimaryKey(NHSysCodeItem record);
}