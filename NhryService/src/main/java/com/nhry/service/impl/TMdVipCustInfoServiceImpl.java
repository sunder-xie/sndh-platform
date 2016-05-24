package com.nhry.service.impl;

import java.rmi.ServerException;

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
	public int insert(JSONObject record) {
		// TODO Auto-generated method stub
		//TMdVipCustInfo
		TMdVipCustInfo cust = JackJson.fromJsonToObject(record.toString(), TMdVipCustInfo.class);
		if(cust == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"json数据格式异常");
		}
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

}
