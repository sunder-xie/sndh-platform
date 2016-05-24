package com.nhry.service.impl;

import java.rmi.ServerException;
import java.util.List;

import org.codehaus.jettison.json.JSONObject;

import com.nhry.data.dao.TMdVipCustInfoMapper;
import com.nhry.domain.TMdVipCustInfo;
import com.nhry.exception.MessageCode;
import com.nhry.exception.ServiceException;
import com.nhry.service.dao.TMdVipCustInfoService;
import com.nhry.utils.json.JackJson;

public class TMdVipCustInfoServiceImpl implements TMdVipCustInfoService {
	private TMdVipCustInfoMapper tmdVipcust;
	
	@Override
	public int deleteByPrimaryKey(String vipCustNo) {
		// TODO Auto-generated method stub
		return tmdVipcust.deleteByPrimaryKey(vipCustNo);
	}

	@Override
	public int insert(TMdVipCustInfo cust) {
		// TODO Auto-generated method stub
		//TMdVipCustInfo
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
