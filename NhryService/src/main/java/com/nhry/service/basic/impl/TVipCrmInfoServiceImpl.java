package com.nhry.service.basic.impl;

import com.nhry.data.basic.dao.TVipCrmMapper;
import com.nhry.data.basic.domain.TVipCrmInfo;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.TVipCrmInfoService;

import java.util.Map;

public class TVipCrmInfoServiceImpl extends BaseService implements TVipCrmInfoService {
	private TVipCrmMapper vipCrmMapper;

	public void setVipCrmMapper(TVipCrmMapper vipCrmMapper) {
		this.vipCrmMapper = vipCrmMapper;
	}

	@Override
	public void addVipCust(TVipCrmInfo record) {
		vipCrmMapper.addVipCust(record);
	}

	@Override
	public TVipCrmInfo findVipCustByNo(String vipCustNo) {
		TVipCrmInfo cust = this.vipCrmMapper.findVipCustByNo(vipCustNo);
		return cust;
	}

	@Override
	public int updateVipCustByNo(TVipCrmInfo record) {
		return vipCrmMapper.updateVipCustByNo(record);
	}

	@Override
	public String getCustNoByPhone(Map<String, String> attrs) {
		return vipCrmMapper.getCustNoByPhone(attrs);
	}

}
