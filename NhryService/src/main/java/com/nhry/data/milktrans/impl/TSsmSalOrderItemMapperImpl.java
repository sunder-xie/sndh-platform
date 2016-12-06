package com.nhry.data.milktrans.impl;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.milktrans.dao.TSsmSalOrderItemMapper;
import com.nhry.data.milktrans.domain.TSsmSalOrderItems;
import com.nhry.model.order.OrderPointModel;

import java.util.Map;

import java.util.List;

/**
 * Created by gongjk on 2016/7/16.
 */
public class TSsmSalOrderItemMapperImpl implements TSsmSalOrderItemMapper {

    private DynamicSqlSessionTemplate sqlSessionTemplate;
    public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
        this.sqlSessionTemplate = sqlSessionTemplate;
    }


    @Override
    public int addSalOrderItem(TSsmSalOrderItems salOrderItems) {
        return sqlSessionTemplate.insert("addSalOrderItem",salOrderItems);
    }

    @Override
    public List<TSsmSalOrderItems> selectItemsBySalOrderNo(Map  map) {
        return sqlSessionTemplate.selectList("selectItemsBySalOrderNo",map);
    }

    @Override
    public List<Map<String, String>> findItemsForPI(String orderNo) {
        return sqlSessionTemplate.selectList("findItemsForPI",orderNo);
    }

    @Override
    public List<Map<String, String>> findDealerItemsForPI(String orderNo) {
        return sqlSessionTemplate.selectList("findDealerItemsForPI",orderNo);
    }

    @Override
    public int delSalOrderItemsByOrderNo(String orderNo) {
        return sqlSessionTemplate.delete("delSalOrderItemsByOrderNo",orderNo);
    }

    @Override
    public int uptYfrechAndYGrowthByOrderNoAndItemNo(OrderPointModel model) {
        return sqlSessionTemplate.update("uptYfrechAndYGrowthByOrderNoAndItemNo",model);
    }

    @Override
    public OrderPointModel getSumYfrechAndYGrowByOrderNo(String orderNo) {
        return sqlSessionTemplate.selectOne("getSumYfrechAndYGrowByOrderNo",orderNo);
    }
}
