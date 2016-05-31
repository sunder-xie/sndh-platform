package com.nhry.data.impl;

import com.nhry.data.dao.TMdBranchEmpMapper;
import com.nhry.datasource.DynamicSqlSessionTemplate;
import com.nhry.domain.TMdBranchEmp;

public class TMdBranchEmpMapperImpl implements TMdBranchEmpMapper {
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int deleteBranchEmpByNo(String empNo) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete("deleteBranchEmpByNo", empNo);
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

}
