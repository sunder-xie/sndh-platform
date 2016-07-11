package com.nhry.data.milk.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.data.milk.domain.TDispOrderItemKey;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.model.milk.RouteDetailUpdateModel;
import com.nhry.model.milk.RouteOrderSearchModel;
import com.nhry.service.milk.pojo.TDispOrderChangeItem;
import com.nhry.data.milktrans.domain.TRecBotDetail;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.model.milk.RouteDetailUpdateModel;
import com.nhry.model.milktrans.CreateEmpReturnboxModel;
import com.nhry.model.milktrans.UnDeliverProductSearch;
import com.nhry.service.milk.pojo.TDispOrderChangeItem;

import java.util.List;

public interface TDispOrderItemMapper {
	
	 PageInfo selectRouteDetailsByPage(RouteOrderSearchModel smodel);
	 
	 List selectRouteDetails(String routeCode);
	
    int deleteByPrimaryKey(TDispOrderItemKey key);

    int insert(TDispOrderItem record);

    List<TDispOrderItem> selectItemsByKeys(TDispOrderItemKey record);

    List<TDispOrderItem> selectNotDeliveryItemsByKeys(String code);
    
    List<TDispOrderChangeItem> selectDispItemsChange(String yestoday,String today,String orderNo);

    int updateByPrimaryKeySelective(TDispOrderItem record);

    int updateDispOrderItem(RouteDetailUpdateModel record,TPlanOrderItem entry);
    
    int batchinsert(List<TDispOrderItem> records);
    
    List<TDispOrderItem> selectItemsByConfirmed();

    List<TRecBotDetail> selectItemsByReturnBox(CreateEmpReturnboxModel cModel);
    
    TDispOrderItem selectDispOrderItemByKey(TDispOrderItemKey code);

    PageInfo searchUndeliverProduct(UnDeliverProductSearch uSearch);

    List<TDispOrderItem> selectItemsByOrderNo(String dispOrderNo);
}