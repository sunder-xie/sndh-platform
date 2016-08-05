package com.nhry.service.basic.impl;

import com.nhry.data.basic.dao.TVipCrmAddressMapper;
import com.nhry.data.basic.dao.TVipCrmMapper;
import com.nhry.data.basic.domain.TVipCrmAddress;
import com.nhry.data.basic.domain.TVipCrmInfo;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.TVipCrmInfoService;

import java.util.Map;

public class TVipCrmInfoServiceImpl extends BaseService implements TVipCrmInfoService {
	private TVipCrmMapper vipCrmMapper;
	private TVipCrmAddressMapper vipcrmAddressMapper;

	public void setVipCrmMapper(TVipCrmMapper vipCrmMapper) {
		this.vipCrmMapper = vipCrmMapper;
	}

	@Override
	public void addVipCrm(TVipCrmInfo record) {
		vipCrmMapper.addVipCrm(record);
	}

	@Override
	public TVipCrmInfo findVipCrmByNo(String vipCrmNo) {
		TVipCrmInfo crm = this.vipCrmMapper.findVipCrmByNo(vipCrmNo);
		return crm;
	}

	@Override
	public int updateVipCrmByNo(TVipCrmInfo record) {
		return vipCrmMapper.updateVipCrmByNo(record);
	}
	
	

	@Override
	public String getCrmNoByPhone(Map<String, String> attrs) {
		return vipCrmMapper.getCrmNoByPhone(attrs);
	}

	public void setVipcrmAddressMapper(TVipCrmAddressMapper vipcrmAddressMapper) {
		this.vipcrmAddressMapper = vipcrmAddressMapper;
	}

	@Override
	public int updateVipCrmAddress(TVipCrmAddress address) {
		// TODO Auto-generated method stub
		return vipcrmAddressMapper.uptVipCrmAddress(address);
	}
}
