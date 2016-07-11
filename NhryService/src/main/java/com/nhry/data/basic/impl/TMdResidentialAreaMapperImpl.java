package com.nhry.data.basic.impl;


import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.basic.dao.TMdResidentialAreaMapper;
import com.nhry.data.basic.domain.TMdResidentialArea;
import com.nhry.model.basic.BranchAreaSearch;
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
    public PageInfo searchAreaByBranchNo(BranchAreaSearch bSearch)  {
        return sqlSessionTemplate.selectListByPages("getAreaByBranchNo",bSearch,
                Integer.parseInt(bSearch.getPageNum()), Integer.parseInt(bSearch.getPageSize()));
    }

    /**
     * 更新小区状态为未分配
     * @param residentialAreaId
     * @return
     */
    @Override
    public int updateStatusToUnDistById(String residentialAreaId) {
        return sqlSessionTemplate.update("updateStatusToUnDistById",residentialAreaId);
    }

    @Override
    public TMdResidentialArea getAreaById(String id) {
        return sqlSessionTemplate.selectOne("getAreaById",id);
    }

    /**
     * 更新小区状态为已分配
     * @param id
     * @return
     */
    @Override
    public int updateStatusToDistedById(String id) {
        return sqlSessionTemplate.update("updateStatusToDistedById",id);
    }

    @Override
    public List<TMdResidentialArea> getUnDistAreas(String salesOrg) {
        return sqlSessionTemplate.selectList("getUnDistAreas",salesOrg);
    }

    @Override
    public PageInfo findAreaListByPage(ResidentialAreaModel residentialAreaModel) {
        return sqlSessionTemplate.selectListByPages("selectAreaByPage",residentialAreaModel,
                Integer.parseInt(residentialAreaModel.getPageNum()), Integer.parseInt(residentialAreaModel.getPageSize()));
    }

    @Override
    public int addResidentialArea(TMdResidentialArea tMdResidentialArea) {
        return sqlSessionTemplate.insert("insertArea",tMdResidentialArea);
    }

    @Override
    public int uptResidentialArea(TMdResidentialArea tMdResidentialArea) {
        int area = sqlSessionTemplate.update("uptArea",tMdResidentialArea);
        int areaBranch =  sqlSessionTemplate.update("uptBranchArea",tMdResidentialArea);
        return area + areaBranch;
    }

    @Override
    public int deleteAreaById(String id) {
        int area = sqlSessionTemplate.delete("delArea",id);
        int areaBranch =  sqlSessionTemplate.delete("delBranchArea",id);
        return area + areaBranch;
    }


    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
}
