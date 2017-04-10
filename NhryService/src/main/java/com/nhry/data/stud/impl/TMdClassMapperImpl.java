package com.nhry.data.stud.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.stud.dao.TMdClassMapper;
import com.nhry.data.stud.domain.TMdClass;
import com.nhry.model.stud.ClassQueryModel;

public class TMdClassMapperImpl implements TMdClassMapper {
	
	@Autowired
	private DynamicSqlSessionTemplate sqlSessionTemplate;

	@Override
	public int deleteByClassCode(String classCode) {
		return sqlSessionTemplate.delete("deleteByClassCode", classCode);
	}

	@Override
	public int insertClass(TMdClass mdClass) {
		return sqlSessionTemplate.insert("insertClass", mdClass);
	}

	@Override
	public TMdClass selectByClassCode(String classCode) {
		return sqlSessionTemplate.selectOne("selectByClassCode", classCode);
	}

	@Override
	public int updateTMdClass(TMdClass mdClass) {
		return sqlSessionTemplate.update("updateTMdClass", mdClass);
	}

	@Override
	public List<TMdClass> findClassListBySalesOrg(String salesOrg) {
		return sqlSessionTemplate.selectList("findClassListBySalesOrg", salesOrg);
	}

	@Override
	public PageInfo<TMdClass> findClassPage(ClassQueryModel model) {
		return sqlSessionTemplate.selectListByPages("findClassPage", Integer.parseInt(model.getPageNum()), Integer.parseInt(model.getPageSize()));
	}

}
