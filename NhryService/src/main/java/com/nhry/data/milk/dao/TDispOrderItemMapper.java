package com.nhry.data.milk.dao;

import java.util.List;

import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.data.milk.domain.TDispOrderItemKey;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.model.milk.RouteDetailUpdateModel;
import com.nhry.service.milk.pojo.TDispOrderChangeItem;

public interface TDispOrderItemMapper {
    int deleteByPrimaryKey(TDispOrderItemKey key);

    int insert(TDispOrderItem record);

    List<TDispOrderItem> selectItemsByKeys(TDispOrderItemKey record);

    List<TDispOrderChangeItem> selectDispItemsChange(String yestoday,String today);

    int updateByPrimaryKeySelective(TDispOrderItem record);

    int updateDispOrderItem(RouteDetailUpdateModel record,TPlanOrderItem entry);
    
    int batchinsert(List<TDispOrderItem> records);
    
    List<TDispOrderItem> selectItemsByConfirmed();
}