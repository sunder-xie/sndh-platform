package com.nhry.data.stud.impl;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.stud.dao.TMstOrderStudMapper;
import com.nhry.data.stud.domain.TMstOrderStud;
import com.nhry.model.stud.OrderStudQueryModel;

public class TMstOrderStudMapperImpl implements TMstOrderStudMapper {
	
	@Autowired
	private DynamicSqlSessionTemplate sqlSessionTemplate;

	@Override
	public int insertOrder(TMstOrderStud mstOrderStud) {
		return sqlSessionTemplate.insert("insertOrder", mstOrderStud);
	}

	@Override
	public TMstOrderStud selectByOrderId(String orderId) {
		return sqlSessionTemplate.selectOne("selectByOrderId", orderId);
	}

	@Override
	public int updateByOrder(TMstOrderStud mstOrderStud) {
		return sqlSessionTemplate.update("updateByOrder", mstOrderStud);
	}

	@Override
	public PageInfo<TMstOrderStud> findOrderPage(OrderStudQueryModel queryModel) {
		return sqlSessionTemplate.selectListByPages("findOrderPage", queryModel, Integer.parseInt(queryModel.getPageNum()), Integer.parseInt(queryModel.getPageSize()));
	}

}
