package com.nhry.data.basic.impl;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.dao.TVipCustInfoMapper;
import com.nhry.data.basic.domain.TMdAddress;
import com.nhry.data.basic.domain.TVipCustInfo;
import com.nhry.model.basic.CustQueryModel;

public class TVipCustInfoMapperImpl implements TVipCustInfoMapper {
	private DynamicSqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int addVipCust(TVipCustInfo record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("addVipCust",record);
	}

	@Override
	public TVipCustInfo findVipCustByNo(String vipCustNo) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("findVipCustByNo", vipCustNo);
	}

	@Override
	public int updateVipCustByNo(TVipCustInfo record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("updateVipCustByNo", record);
	}

	@Override
	public List<TVipCustInfo> findStaCustByPhone(Map<String, String> attrs) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("findStaCustByPhone", attrs);
	}

	@Override
	public List<TVipCustInfo> findCompanyCustByPhone(Map<String, String> attrs) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("findCompanyCustByPhone", attrs);
	}

	@Override
	public TVipCustInfo findVipCustByNoForUpt(String vipCustNo) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("findVipCustByNoForUpt", vipCustNo);
	}

	@Override
	public TVipCustInfo findVipCustOnlyByNo(String vipCustNo) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("findVipCustOnlyByNo", vipCustNo);
	}

	@Override
	public int uptCustStatus(TVipCustInfo record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("uptCustStatus", record);
	}

	@Override
	public PageInfo findcustMixedTerms(CustQueryModel cust) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectListByPages("findcustMixedTerms",cust, Integer.parseInt(cust.getPageNum()), Integer.parseInt(cust.getPageSize()));
	}
}
