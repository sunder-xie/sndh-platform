package com.nhry.data.milktrans.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.milktrans.domain.TSsmSalOrder;
import com.nhry.data.milktrans.dao.TSsmSalOrderMapper;
import com.nhry.model.milktrans.SalOrderModel;
import java.util.List;

import java.util.Map;

/**
 * Created by gongjk on 2016/7/16.
 */
public class TSsmSalOrderMapperImpl implements TSsmSalOrderMapper {

    private DynamicSqlSessionTemplate sqlSessionTemplate;
    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }

    @Override
    public int addsalOrder(TSsmSalOrder order) {
        return sqlSessionTemplate.insert("addsalOrder",order);
    }

    @Override
    public int uptVouCherNoByOrderNo(Map map) {
        return sqlSessionTemplate.update("uptVouCherNoByOrderNo",map);
    }

    @Override
    public List<TSsmSalOrder> selectSalOrderByDateAndNo(SalOrderModel model) {
        return sqlSessionTemplate.selectList("selectSalOrderByDateAndNo",model);
    }
}
