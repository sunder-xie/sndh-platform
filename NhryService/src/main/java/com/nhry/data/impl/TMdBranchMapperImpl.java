package com.nhry.data.impl;

import java.util.List;

import com.nhry.data.dao.TMdBranchMapper;
import com.nhry.datasource.DynamicSqlSessionTemplate;
import com.nhry.domain.TMdBranch;

public class TMdBranchMapperImpl implements TMdBranchMapper {
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	@Override
	public int deleteBranchByNo(String branchNo) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int addBranch(TMdBranch record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TMdBranch selectBranchByNo(String branchNo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int updateBranchByNo(TMdBranch record) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<TMdBranch> findBranchListByOrg(String salesOrg) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList("findBranchListByOrg", salesOrg);
	}

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
