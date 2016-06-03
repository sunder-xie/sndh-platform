package com.nhry.data.basic.impl;

import java.util.List;

import com.nhry.data.basic.dao.TMdVipCustInfoMapper;
import com.nhry.data.basic.domain.TMdVipCustInfo;
import com.nhry.datasource.DynamicSqlSessionTemplate;

public class TMdVipCustInfoMapperImpl implements TMdVipCustInfoMapper {
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int deleteByPrimaryKey(String vipCustNo) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete("deleteByPrimaryKey",vipCustNo);
	}

	@Override
	public int insert(TMdVipCustInfo record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("addCust", record);
	}

	@Override
	public int insertSelective(TMdVipCustInfo record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("insertSelective",record);
	}

	@Override
	public TMdVipCustInfo selectByPrimaryKey(String vipCustNo) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("findCustByNo", vipCustNo);
	}

	@Override
	public int updateByPrimaryKeySelective(TMdVipCustInfo record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("updateByPrimaryKeySelective", record);
	}

	@Override
	public int updateByPrimaryKey(TMdVipCustInfo record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("updateCustmess", record);
	}
	
	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public List<TMdVipCustInfo> all() {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("getAllCust");
	}

	@Override
	public TMdVipCustInfo findCustByPhone(String phone) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("findCustByPhone",phone);
	}

}
