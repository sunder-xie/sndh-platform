package com.nhry.data.basic.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.basic.dao.TMdMaraExMapper;
import com.nhry.data.basic.domain.TMdMaraEx;

import java.util.List;

public class TMdMaraExMapperImpl implements TMdMaraExMapper {
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@Override
	public int uptProductExByCode(TMdMaraEx record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("uptProductExByCode", record);
	}

	@Override
	public int addMaraEx(TMdMaraEx record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("addMaraEx", record);
	}

	@Override
	public TMdMaraEx getProductTransRateByCode(String matnr, String salesOrg) {
		TMdMaraEx ex = new TMdMaraEx();
		ex.setMatnr(matnr);
		ex.setSalesOrg(salesOrg);
		return this.sqlSessionTemplate.selectOne("getProductTransRateByCode",ex);
	}

	@Override
	public int uptProductExByCodeAndSalesOrg(TMdMaraEx ex) {
		return this.sqlSessionTemplate.update("uptProductExByCodeAndSalesOrg", ex);
	}

	@Override
	public List<TMdMaraEx> getProductsBySalesOrg(String salesOrg) {
		return sqlSessionTemplate.selectList("getProductsBySalesOrg",salesOrg);
	}

}
