package com.nhry.data.bill.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.bill.dao.BranchBillMapper;
import com.nhry.model.bill.BranchBillSearch;

/**
 * Created by gongjk on 2016/6/27.
 */
public class BranchBillMapperImpl implements BranchBillMapper {
    private DynamicSqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public PageInfo branchBillSearch(BranchBillSearch bsearch) {
        return sqlSessionTemplate.selectListByPages("branchBillSearch",bsearch, Integer.parseInt(bsearch.getPageNum()), Integer.parseInt(bsearch.getPageSize()));

    }

    @Override
    public PageInfo branchBillEmpSearch(BranchBillSearch bsearch) {
        return null;
    }
}
