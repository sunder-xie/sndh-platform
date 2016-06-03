package com.nhry.data.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.data.dao.TMdBranchMapper;
import com.nhry.datasource.DynamicSqlSessionTemplate;
import com.nhry.domain.TMdBranch;
import com.nhry.pojo.query.BranchQueryModel;

import java.util.List;

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
		return sqlSessionTemplate.selectOne("selectBranchByNo",branchNo);
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

	@Override
	public PageInfo findBranchListByPage(BranchQueryModel branchModel) {
		return sqlSessionTemplate.selectListByPages("searchBranch",branchModel, Integer.parseInt(branchModel.getPageNum()), Integer.parseInt(branchModel.getPageSize()));

	}

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
