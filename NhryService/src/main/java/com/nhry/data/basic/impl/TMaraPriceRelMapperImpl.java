package com.nhry.data.basic.impl;

import java.util.List;

import org.apache.ibatis.session.SqlSessionFactory;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.basic.dao.TMaraPriceRelMapper;
import com.nhry.data.basic.domain.TMaraPriceRel;

public class TMaraPriceRelMapperImpl implements TMaraPriceRelMapper {
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	@Override
	public int delMaraPriceByNo(String relNo) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("delMaraPriceByNo",relNo);
	}

	@Override
	public int addMaraPrice(TMaraPriceRel record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("addMaraPrice", record);
	}

	@Override
	public TMaraPriceRel findMaraPriceByNo(String relNo) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("findMaraPriceByNo", relNo);
	}

	@Override
	public int uptMaraPricerel(TMaraPriceRel record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("uptMaraPricerel", record);
	}

	@Override
	public List<TMaraPriceRel> findMaraPricesById(String id) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("findMaraPricesById", id);
	}

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int delMaraPricesById(String id) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("delMaraPricesById", id);
	}
}
