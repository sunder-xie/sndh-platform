package com.nhry.data.milktrans.dao;

import com.nhry.data.milktrans.domain.TSsmSalOrderItems;
import com.nhry.model.milktrans.RequireOrderSearch;
import com.nhry.model.order.OrderPointModel;

import java.util.Map;
import java.util.List;

/**
 * Created by gongjk on 2016/7/16.
 */
public interface TSsmSalOrderItemMapper {
    int addSalOrderItem(TSsmSalOrderItems salOrderItems);
    List<TSsmSalOrderItems> selectItemsBySalOrderNo(Map map);
    List<Map<String,String>> findItemsForPI(String orderNo);
    List<Map<String,String>> findDealerItemsForPI(String orderNo);
    int delSalOrderItemsByOrderNo(String orderNo);
    int uptYfrechAndYGrowthByOrderNoAndItemNo(OrderPointModel model);

    OrderPointModel getSumYfrechAndYGrowByOrderNo(String orderNo);

    /**
     * 经销商奶站年卡折扣金额
     * @param rModel
     * @return
     */
    List<Map<String,Object>> selectPromDaliyDiscountAmtOfDearler(RequireOrderSearch rModel);
}
