package com.nhry.data.orderorg.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.orderorg.dao.TMdOrderOrgMapper;
import com.nhry.data.orderorg.domain.TMdOrderOrg;
import com.nhry.model.basic.OrderOrgModel;

import java.util.List;

/**
 * Created by huaguan on 2016/12/1.
 * 描述：机构维护
 */
public class TmdOrderOrgMapperImpl implements TMdOrderOrgMapper {
    private DynamicSqlSessionTemplate sqlSessionTemplate;
    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public int deleteOrderOrgByPrimaryKey(String id) {
        return sqlSessionTemplate.delete("deleteOrderOrgByPrimaryKey",id);
    }

    @Override
    public int insertOrderOrg(TMdOrderOrg record) {
        return sqlSessionTemplate.insert("insertOrderOrg", record);
    }

    @Override
    public int insertOrderOrgSelective(TMdOrderOrg record) {
        return sqlSessionTemplate.insert("insertOrderOrgSelective", record);
    }

    @Override
    public TMdOrderOrg selectOrderOrgByPrimaryKey(String id) {
        return sqlSessionTemplate.selectOne("selectOrderOrgByPrimaryKey", id);
    }

    @Override
    public int updateOrderOrgByPrimaryKeySelective(TMdOrderOrg record) {
        return sqlSessionTemplate.update("updateOrderOrgByPrimaryKeySelective", record);
    }

    @Override
    public int updateOrderOrgByPrimaryKey(TMdOrderOrg record) {
        return sqlSessionTemplate.update("updateOrderOrgByPrimaryKey", record);
    }

    @Override
    public PageInfo findTMdOrderOrgList(OrderOrgModel smodel) {
        return sqlSessionTemplate.selectListByPages("findTMdOrderOrgList",smodel,Integer.parseInt(smodel.getPageNum()), Integer.parseInt(smodel.getPageSize()));
    }
}