package com.nhry.data.basic.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.basic.dao.TVipCrmMapper;
import com.nhry.data.basic.domain.TVipCrmInfo;

import java.util.Map;

public class TVipCrmMapperImpl implements TVipCrmMapper {
	private DynamicSqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int addVipCust(TVipCrmInfo record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("addVipCust",record);
	}

	@Override
	public TVipCrmInfo findVipCustByNo(String vipCustNo) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("findVipCustByNo", vipCustNo);
	}

	@Override
	public int updateVipCustByNo(TVipCrmInfo record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("updateVipCustByNo", record);
	}

	@Override
	public TVipCrmInfo findVipCustByNoForUpt(String vipCustNo) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("findVipCustByNoForUpt", vipCustNo);
	}

	@Override
	public TVipCrmInfo findVipCustOnlyByNo(String vipCustNo) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("findVipCustOnlyByNo", vipCustNo);
	}

	@Override
	public int uptCustStatus(TVipCrmInfo record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("uptCustStatus", record);
	}

	@Override
	public String getCustNoByPhone(Map<String, String> attrs) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("getCustNoByPhone", attrs);
	}
	
}
