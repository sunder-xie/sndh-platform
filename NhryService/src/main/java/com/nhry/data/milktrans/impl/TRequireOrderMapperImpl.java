package com.nhry.data.milktrans.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.milktrans.dao.TRequireOrderMapper;
import com.nhry.data.milktrans.domain.TMstRequireOrder;
import com.nhry.model.milktrans.RequireOrderSearch;

import java.util.List;

/**
 * Created by gongjk on 2016/6/22.
 */
public class TRequireOrderMapperImpl implements TRequireOrderMapper {

    private DynamicSqlSessionTemplate sqlSessionTemplate;
    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }


    @Override
    public int insertRequireOrder(TMstRequireOrder order) {
        return sqlSessionTemplate.insert("insertRequireOrder",order);
    }

    @Override
    public List<TMstRequireOrder> searchRequireOrder(RequireOrderSearch rModel) {
        return sqlSessionTemplate.selectList("searchRequireOrder",rModel);
    }

    @Override
    public TMstRequireOrder selectRequireOrderItemByitem(TMstRequireOrder tMstRequireOrder) {
        return sqlSessionTemplate.selectOne("selectRequireOrderItemByitem",tMstRequireOrder);
    }

    @Override
    public int uptRequireOrder(TMstRequireOrder tMstRequireOrder) {
        return sqlSessionTemplate.update("uptRequireOrder",tMstRequireOrder);
    }

    @Override
    public int delRequireOrder(TMstRequireOrder tMstRequireOrder) {
        return sqlSessionTemplate.delete("delRequireOrder",tMstRequireOrder);
    }


}
