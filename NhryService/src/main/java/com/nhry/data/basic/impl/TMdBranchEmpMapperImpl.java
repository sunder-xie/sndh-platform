package com.nhry.data.basic.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.basic.dao.TMdBranchEmpMapper;
import com.nhry.data.basic.domain.TMdBranchEmp;
import com.nhry.data.basic.domain.TMdResidentialArea;
import com.nhry.model.basic.EmpQueryModel;
import com.nhry.service.basic.pojo.BranchEmpModel;

import java.util.List;

public class TMdBranchEmpMapperImpl implements TMdBranchEmpMapper {
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int deleteBranchEmp(TMdBranchEmp record) {
		// TODO Auto-generated method stub
		int emp = sqlSessionTemplate.update("deleteBranchEmpByNo", record);
		int role = sqlSessionTemplate.delete("deleteEmpRole", record);
		return emp+role;
	}

	@Override
	public int addBranchEmp(TMdBranchEmp record) {
		// TODO Auto-generated method stub
		int emp = this.sqlSessionTemplate.insert("addBranchEmp", record);
		int role = this.sqlSessionTemplate.insert("addEmpRole", record);
		return emp+role;
	}

	@Override
	public TMdBranchEmp selectBranchEmpByNo(String empNo) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("selectBranchEmpByNo", empNo);
	}

	@Override
	public BranchEmpModel empDetailInfo(String empNo) {
		TMdBranchEmp emp = this.sqlSessionTemplate.selectOne("selectBranchEmpByNo", empNo);
		List<TMdResidentialArea> arealist = this.sqlSessionTemplate.selectList("selectEmpRoutes", empNo);
		BranchEmpModel branchEmpModel = new BranchEmpModel();
		branchEmpModel.setEmp(emp);
		branchEmpModel.setRoutes(arealist);
		// TODO Auto-generated method stub
		return branchEmpModel;
	}

	@Override
	public int uptBranchEmpByNo(TMdBranchEmp record) {
		// TODO Auto-generated method stub
		int emp = this.sqlSessionTemplate.update("uptBranchEmpByNo", record);
		int role = this.sqlSessionTemplate.insert("uptEmpRole", record);
		return emp+role;
	}

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public PageInfo searchBranchEmp(EmpQueryModel smodel) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectListByPages("searchBranchEmp",smodel, Integer.parseInt(smodel.getPageNum()), Integer.parseInt(smodel.getPageSize()));
	}

	@Override
	public TMdBranchEmp selectBranchEmpByEmpNo(String empNo) {
		return sqlSessionTemplate.selectOne("selectBranchEmpByEmpNo",empNo);
	}


}
