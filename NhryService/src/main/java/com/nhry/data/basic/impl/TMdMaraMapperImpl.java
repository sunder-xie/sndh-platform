package com.nhry.data.basic.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.dao.TMdMaraMapper;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.datasource.DynamicSqlSessionTemplate;
import com.nhry.service.basic.pojo.ProductInfoExModel;
import com.nhry.model.basic.ProductQueryModel;

public class TMdMaraMapperImpl implements TMdMaraMapper {
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	
	@Override
	public TMdMara selectProductByCode(String productCode) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("selectProductByCode", productCode);
	}

	@Override
	public int uptProductByCode(TMdMara record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("uptProductByCode", record);
	}


	@Override
	public PageInfo searchProducts(ProductQueryModel smodel) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectListByPages("searchProducts",smodel, Integer.parseInt(smodel.getPageNum()), Integer.parseInt(smodel.getPageSize()));
	}

	@Override
	public ProductInfoExModel selectProductAndExByCode(String productCode){
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("selectProductAndExByCode", productCode);
	}

	@Override
	public int pubProductByCode(String code){
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("pubProductByCode", code);
	}
}
