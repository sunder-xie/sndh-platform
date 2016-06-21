package com.nhry.data.basic.impl;


import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.basic.dao.TMdResidentialAreaMapper;
import com.nhry.data.basic.domain.TMdResidentialArea;
import com.nhry.service.basic.pojo.ResidentialAreaModel;

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

    @Override
    public PageInfo findAreaListByPage(ResidentialAreaModel residentialAreaModel) {
        return sqlSessionTemplate.selectListByPages("selectAreaByPage",residentialAreaModel,
                Integer.parseInt(residentialAreaModel.getPageNum()), Integer.parseInt(residentialAreaModel.getPageSize()));
    }

    @Override
    public int addResidentialArea(ResidentialAreaModel residentialAreaModel) {

        return sqlSessionTemplate.insert("insert",residentialAreaModel);
    }



    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
}