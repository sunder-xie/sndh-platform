package com.nhry.data.bill.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.bill.dao.EmpBillMapper;
import com.nhry.model.bill.EmpAccoDispFeeByProduct;
import com.nhry.model.bill.EmpDispDetialInfoSearch;

import java.util.List;

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
    public PageInfo empAccountReceAmount(EmpDispDetialInfoSearch eSearch) {
        return sqlSessionTemplate.selectListByPages("empAccountReceAmount",eSearch, Integer.parseInt(eSearch.getPageNum()), Integer.parseInt(eSearch.getPageSize()));
    }

    @Override
    public PageInfo empSalaryRep(EmpDispDetialInfoSearch eSearch) {
        return sqlSessionTemplate.selectListByPages("empSalaryRep",eSearch, Integer.parseInt(eSearch.getPageNum()), Integer.parseInt(eSearch.getPageSize()));
    }

    @Override
    public List<EmpAccoDispFeeByProduct> empAccoDispFeeByProduct(EmpDispDetialInfoSearch eSearch) {
        return sqlSessionTemplate.selectList("empAccoDispFeeByProduct",eSearch);
    }

    @Override
    public int empAccoDispFeeByNum(EmpDispDetialInfoSearch eSearch) {
        return sqlSessionTemplate.selectOne("empAccoDispFeeByNum",eSearch);
    }
}
