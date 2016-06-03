package com.nhry.data.basic.impl;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.dao.TMdBranchEmpMapper;
import com.nhry.data.basic.domain.TMdBranchEmp;
import com.nhry.datasource.DynamicSqlSessionTemplate;
import com.nhry.model.basic.EmpQueryModel;

public class TMdBranchEmpMapperImpl implements TMdBranchEmpMapper {
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int deleteBranchEmp(TMdBranchEmp record) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update("deleteBranchEmpByNo", record);
	}

	@Override
	public int addBranchEmp(TMdBranchEmp record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("addBranchEmp", record);
	}

	@Override
	public TMdBranchEmp selectBranchEmpByNo(String empNo) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("selectBranchEmpByNo", empNo);
	}

	@Override
	public int uptBranchEmpByNo(TMdBranchEmp record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("uptBranchEmpByNo", record);
	}

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public PageInfo searchBranchEmp(EmpQueryModel smodel) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectListByPages("searchBranchEmp",smodel, Integer.parseInt(smodel.getPageNum()), Integer.parseInt(smodel.getPageSize()));
	}
}
