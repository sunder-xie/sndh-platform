package com.nhry.service.impl;

import com.nhry.data.dao.NHSysParameterMapper;
import com.nhry.domain.NHSysParameter;
import com.nhry.service.BaseService;
import com.nhry.service.dao.SysParamService;
import com.nhry.utils.Date;

public class SysParamServiceImpl extends BaseService implements SysParamService {
    private NHSysParameterMapper sysParamMapper;
    
	@Override
	public int deleteSysParamByCode(String paramCode) {
		// TODO Auto-generated method stub
		return sysParamMapper.deleteSysParamByCode(paramCode);
	}

	@Override
	public int insert(NHSysParameter record) {
		// TODO Auto-generated method stub
		record.setCreateAt(new Date());
		record.setCreateBy(this.userSessionService.getCurrentUser().getLoginName());
		record.setCreateByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		return sysParamMapper.insert(record);
	}

	@Override
	public NHSysParameter selectSysParamByCode(String paramCode) {
		// TODO Auto-generated method stub
		return sysParamMapper.selectSysParamByCode(paramCode);
	}

	@Override
	public int uptSysParamByCode(NHSysParameter record) {
		// TODO Auto-generated method stub
		record.setLastModified(new Date());
		record.setLastModifiedBy(this.userSessionService.getCurrentUser().getLoginName());
		record.setLastModifiedByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		return sysParamMapper.uptSysParamByCode(record);
	}

	public void setSysParamMapper(NHSysParameterMapper sysParamMapper) {
		this.sysParamMapper = sysParamMapper;
	}
}
