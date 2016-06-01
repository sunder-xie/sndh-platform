package com.nhry.data.dao;

import com.nhry.domain.NHSysParameter;

public interface NHSysParameterMapper {
    int deleteSysParamByCode(String paramCode);

    int insert(NHSysParameter record);

    NHSysParameter selectSysParamByCode(String paramCode);

    int uptSysParamByCode(NHSysParameter record);
}