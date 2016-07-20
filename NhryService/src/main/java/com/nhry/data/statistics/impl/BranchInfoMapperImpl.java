package com.nhry.data.statistics.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.statistics.dao.BranchInfoMapper;
import com.nhry.model.statistics.BranchInfoModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by cbz on 7/18/2016.
 */
public class BranchInfoMapperImpl implements BranchInfoMapper{

    private DynamicSqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public PageInfo branchDayInfo(BranchInfoModel model) {
        return sqlSessionTemplate.selectListByPages("branchDayInfo",model,Integer.valueOf(model.getPageNum()),Integer.valueOf(model.getPageSize()));
    }

    @Override
    public Map<String, String> getBranchs(BranchInfoModel model) {
        return null;
    }

    @Override
    public List<Map<String, String>> branchDayInfoList(BranchInfoModel model) {
        return sqlSessionTemplate.selectList("branchDayInfoList",model);
    }

    @Override
    public PageInfo findOrderRatio(BranchInfoModel model) {
        return sqlSessionTemplate.selectListByPages("findOrderRatio",model,Integer.valueOf(model.getPageNum()),Integer.valueOf(model.getPageSize()));
    }
}
