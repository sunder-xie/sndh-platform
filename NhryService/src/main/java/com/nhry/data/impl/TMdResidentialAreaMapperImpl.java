package com.nhry.data.impl;

import com.nhry.data.dao.TMdResidentialAreaMapper;
import com.nhry.datasource.DynamicSqlSessionTemplate;
import com.nhry.domain.TMdResidentialArea;

import java.util.List;

/**
 * Created by gongjk on 2016/6/3.
 */
public class TMdResidentialAreaMapperImpl implements TMdResidentialAreaMapper {

    private DynamicSqlSessionTemplate sqlSessionTemplate;

    @Override
    public int deleteByPrimaryKey(String id) {
        return 0;
    }

    @Override
    public int insert(TMdResidentialArea record) {
        return 0;
    }

    @Override
    public int insertSelective(TMdResidentialArea record) {
        return 0;
    }

    @Override
    public TMdResidentialArea selectById(String id) {
        return sqlSessionTemplate.selectOne("selectById",id);
    }

    @Override
    public int updateByPrimaryKeySelective(TMdResidentialArea record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(TMdResidentialArea record) {
        return 0;
    }

    @Override
    public List<TMdResidentialArea> getAreaByBranchNo(String branchNo) {
        return sqlSessionTemplate.selectList("getAreaByBranchNo",branchNo);
    }

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
}
