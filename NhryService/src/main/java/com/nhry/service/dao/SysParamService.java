package com.nhry.service.dao;

import com.nhry.domain.NHSysParameter;

public interface SysParamService  {
	int deleteSysParamByCode(String paramCode);

    int insert(NHSysParameter record);

    NHSysParameter selectSysParamByCode(String paramCode);

    int uptSysParamByCode(NHSysParameter record);
}
