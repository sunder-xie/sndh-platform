package com.nhry.data.basic.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.basic.dao.TBranchSalesListMapper;
import com.nhry.data.basic.domain.TBranchSalesList;

public class TBranchSalesListMapperImpl implements TBranchSalesListMapper {
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	@Override
	public int delBranchSalesByNo(String listNo) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("delBranchSalesByNo", listNo);
	}

	@Override
	public int addBranchSales(TBranchSalesList record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("addBranchSales", record);
	}

	@Override
	public TBranchSalesList findBranchSalesByNo(String listNo) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("findBranchSalesByNo", listNo);
	}

	@Override
	public int uptBranchSales(TBranchSalesList record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("uptBranchSales", record);
	}

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int delSalesListByMatnr(String matnr) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("delSalesListByMatnr",matnr);
	}
}
