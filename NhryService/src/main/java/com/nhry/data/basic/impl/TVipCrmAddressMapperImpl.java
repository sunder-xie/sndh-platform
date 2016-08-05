package com.nhry.data.basic.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.basic.dao.TVipCrmAddressMapper;
import com.nhry.data.basic.domain.TVipCrmAddress;

public class TVipCrmAddressMapperImpl implements TVipCrmAddressMapper {
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	@Override
	public int addVipCrmAddress(TVipCrmAddress record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("addVipCrmAddress", record);
	}

	@Override
	public TVipCrmAddress findVipCrmAddress(String addressId) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("findVipCrmAddress",addressId);
	}

	@Override
	public int uptVipCrmAddress(TVipCrmAddress record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("uptVipCrmAddress", record);
	}

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
}
