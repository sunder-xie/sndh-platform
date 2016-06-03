package com.nhry.data.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.data.dao.TMdMaraMapper;
import com.nhry.datasource.DynamicSqlSessionTemplate;
import com.nhry.domain.TMdMara;
import com.nhry.pojo.query.ProductInfoExModel;
import com.nhry.pojo.query.ProductQueryModel;

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
	public int uptPubProductByCode(){
		// TODO Auto-generated method stub
		return 0;
	}
}
