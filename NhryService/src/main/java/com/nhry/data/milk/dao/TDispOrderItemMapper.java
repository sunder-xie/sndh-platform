package com.nhry.data.milk.dao;

import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.data.milk.domain.TDispOrderItemKey;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.model.milk.RouteDetailUpdateModel;
import com.nhry.service.milk.pojo.TDispOrderChangeItem;
import com.nhry.data.milktrans.domain.TRecBotDetail;
import com.nhry.model.milk.RouteDetailUpdateModel;
import com.nhry.model.milktrans.CreateEmpReturnboxModel;

import java.util.List;

public interface TDispOrderItemMapper {
    int deleteByPrimaryKey(TDispOrderItemKey key);

    int insert(TDispOrderItem record);

    List<TDispOrderItem> selectItemsByKeys(TDispOrderItemKey record);

    List<TDispOrderChangeItem> selectDispItemsChange(String yestoday,String today);

    int updateByPrimaryKeySelective(TDispOrderItem record);

    int updateDispOrderItem(RouteDetailUpdateModel record,TPlanOrderItem entry);
    
    int batchinsert(List<TDispOrderItem> records);
    
    List<TDispOrderItem> selectItemsByConfirmed();

    List<TRecBotDetail> selectItemsByReturnBox(CreateEmpReturnboxModel cModel);

}