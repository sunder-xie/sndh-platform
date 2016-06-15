package com.nhry.service.basic.impl;

import java.util.List;

import com.nhry.common.auth.UserSessionService;
import com.nhry.data.basic.dao.TMdVipCustInfoMapper;
import com.nhry.data.basic.domain.TMdVipCustInfo;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.TMdVipCustInfoService;
import com.nhry.utils.date.Date;

public class TMdVipCustInfoServiceImpl extends BaseService implements TMdVipCustInfoService {
	private TMdVipCustInfoMapper tmdVipcust;
	
	@Override
	public int deleteByPrimaryKey(String vipCustNo) {
		// TODO Auto-generated method stub
		return tmdVipcust.deleteByPrimaryKey(vipCustNo);
	}

	@Override
	public int insert(TMdVipCustInfo cust) {
		// TODO Auto-generated method stub
		//nh201605251401
		cust.setVipCustNo("nh"+System.currentTimeMillis());
		cust.setCreateAt(new Date());
		cust.setCreateBy(userSessionService.getCurrentUser().getLoginName());
		return tmdVipcust.insert(cust);
	}

	@Override
	public TMdVipCustInfo selectByPrimaryKey(String vipCustNo) {
		// TODO Auto-generated method stub
		return tmdVipcust.selectByPrimaryKey(vipCustNo);
	}

	@Override
	public int updateByPrimaryKey(TMdVipCustInfo record) {
		// TODO Auto-generated method stub
		record.setLastModified(new Date());
		record.setLastModifiedBy(userSessionService.getCurrentUser().getLoginName());
		return tmdVipcust.updateByPrimaryKey(record);
	}

	public void setTmdVipcust(TMdVipCustInfoMapper tmdVipcust) {
		this.tmdVipcust = tmdVipcust;
	}

	@Override
	public List<TMdVipCustInfo> allCust() {
		// TODO Auto-generated method stub
		return tmdVipcust.all();
	}

	@Override
	public TMdVipCustInfo findCustByPhone(String phone) {
		// TODO Auto-generated method stub
		return tmdVipcust.findCustByPhone(phone);
	}

}
