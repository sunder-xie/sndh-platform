package com.nhry.data.basic.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.basic.dao.TMdBranchMapper;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.model.basic.BranchQueryModel;

import java.util.List;
import java.util.Map;

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
		return sqlSessionTemplate.insert("addBranch",record);
	}



	@Override
	public TMdBranch selectBranchByNo(String branchNo) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("selectBranchByNo",branchNo);
	}

	@Override
	public int updateBranch(TMdBranch tMdBranch) {
		return sqlSessionTemplate.update("uptBranch",tMdBranch);
	}

	@Override
	public List<TMdBranch> getBranchByCodeOrName(Map<String, String> map) {
		return sqlSessionTemplate.selectList("getBranchByCodeOrName",map);
	}

	@Override
	public List<TMdBranch> findBranchListByOrgAndAuto(String salesOrg) {
		return sqlSessionTemplate.selectList("findBranchListByOrgAndAuto",salesOrg);
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

	@Override
	public List<TMdBranch> findBranchByDno(Map<String, String> attrs) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("findBranchByDno", attrs);
	}



}
