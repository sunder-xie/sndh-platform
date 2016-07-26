package com.nhry.data.milktrans.dao;

import com.nhry.data.milktrans.domain.TSsmSalOrderItems;
import java.util.Map;
import java.util.List;

/**
 * Created by gongjk on 2016/7/16.
 */
public interface TSsmSalOrderItemMapper {
    int addSalOrderItem(TSsmSalOrderItems salOrderItems);
    List<TSsmSalOrderItems> selectItemsBySalOrderNo(Map map);
    List<Map<String,String>> findItemsForPI(String orderNo);

    int delSalOrderItemsByOrderNo(String orderNo);
}
