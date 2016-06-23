package com.nhry.data.basic.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.basic.dao.TMdAddressMapper;
import com.nhry.data.basic.domain.TMdAddress;

public class TMdAddressMapperImpl implements TMdAddressMapper{
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	@Override
	public int addAddressForCust(TMdAddress address) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("addAddressForCust", address);
	}

	@Override
	public TMdAddress findAddressById(String addressId) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("findAddressById", addressId);
	}

	@Override
	public int uptCustAddress(TMdAddress record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("uptCustAddress", record);
	}

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
}
