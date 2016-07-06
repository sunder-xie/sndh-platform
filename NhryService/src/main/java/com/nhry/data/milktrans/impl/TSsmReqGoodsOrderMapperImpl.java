package com.nhry.data.milktrans.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.milktrans.dao.TSsmReqGoodsOrderMapper;
import com.nhry.data.milktrans.domain.TSsmReqGoodsOrder;
import com.nhry.model.milktrans.RequireOrderSearch;

/**
 * Created by gongjk on 2016/6/22.
 */
public class TSsmReqGoodsOrderMapperImpl implements TSsmReqGoodsOrderMapper {

    private DynamicSqlSessionTemplate sqlSessionTemplate;
    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }


    @Override
    public int insertRequireOrder(TSsmReqGoodsOrder order) {
        return sqlSessionTemplate.insert("insertRequireOrder",order);
    }

    @Override
    public TSsmReqGoodsOrder searchRequireOrder(RequireOrderSearch rModel) {
        return sqlSessionTemplate.selectOne("searchRequireOrder",rModel);
    }

    @Override
    public TSsmReqGoodsOrder getRequireOrderByNo(String orderNo) {
        return sqlSessionTemplate.selectOne("getRequireOrderByNo",orderNo);
    }

    @Override
    public int uptRequireGoodsModifyInfo(TSsmReqGoodsOrder orderModel) {
        return sqlSessionTemplate.update("uptRequireGoodsModifyInfo",orderModel);
    }

/*

    @Override
    public int uptRequireOrder(TMstRequireOrder tMstRequireOrder) {
        return sqlSessionTemplate.update("uptRequireOrder",tMstRequireOrder);
    }

    @Override
    public int delRequireOrder(TMstRequireOrder tMstRequireOrder) {
        return sqlSessionTemplate.delete("delRequireOrder",tMstRequireOrder);
    }
*/


}
