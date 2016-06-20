package com.nhry.data.auth.impl;

import java.util.List;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.auth.dao.TSysResourceMapper;
import com.nhry.data.auth.domain.TSysResource;
import com.nhry.data.auth.domain.TSysRoleResource;

public class TSysResourceMapperImpl implements TSysResourceMapper {
	private DynamicSqlSessionTemplate sqlSessionTemplate;

	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public int deleteResByCode(String resCode) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("deleteResByCode", resCode);
	}

	@Override
	public int addRes(TSysResource record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("addRes", record);
	}

	@Override
	public TSysResource selectResByCode(String resCode) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("selectResByCode", resCode);
	}

	@Override
	public int updateResByCode(TSysResource record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.update("updateResByCode", record);
	}

	@Override
	public int addRoleRes(TSysRoleResource record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.insert("addRoleRes", record);
	}

	@Override
	public int deleteRoleRes(TSysRoleResource record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.delete("deleteRoleResByRc", record);
	}

	@Override
	public TSysRoleResource findRoleResByRc(TSysRoleResource record) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectOne("findRoleResByRc", record);
	}

	@Override
	public List<TSysRoleResource> findRoleResByRid(String rid) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("findRoleResByRid", rid);
	}

	@Override
	public List<TSysRoleResource> findRoleResByResCode(String resCode) {
		// TODO Auto-generated method stub
		return this.sqlSessionTemplate.selectList("findRoleResByResCode", resCode);
	}
}
