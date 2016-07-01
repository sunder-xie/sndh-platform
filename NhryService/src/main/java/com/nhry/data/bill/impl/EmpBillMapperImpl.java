package com.nhry.data.bill.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.bill.dao.EmpBillMapper;
import com.nhry.model.bill.EmpAccountSearch;
import com.nhry.model.bill.EmpDispDetialInfoSearch;

/**
 * Created by gongjk on 2016/7/1.
 */
public class EmpBillMapperImpl implements EmpBillMapper {
    private DynamicSqlSessionTemplate sqlSessionTemplate;

    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }
    @Override
    public PageInfo empDispDetialInfo(EmpDispDetialInfoSearch eSearch) {
        return sqlSessionTemplate.selectListByPages("empDispDetialInfo",eSearch, Integer.parseInt(eSearch.getPageNum()), Integer.parseInt(eSearch.getPageSize()));

    }

    @Override
    public PageInfo empAccountReceAmount(EmpAccountSearch eSearch) {
        return sqlSessionTemplate.selectListByPages("empAccountReceAmount",eSearch, Integer.parseInt(eSearch.getPageNum()), Integer.parseInt(eSearch.getPageSize()));
    }
}
