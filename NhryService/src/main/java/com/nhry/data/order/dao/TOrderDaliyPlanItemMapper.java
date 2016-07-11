package com.nhry.data.order.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TOrderDaliyPlanItemKey;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.milktrans.RequireOrderSearch;
import com.nhry.model.order.OrderSearchModel;

import java.util.Date;
import java.util.List;

public interface TOrderDaliyPlanItemMapper {
	
	 PageInfo selectDaliyOrdersByPages(OrderSearchModel smodel);
	
	 int deleteFromDateToDate(TOrderDaliyPlanItem record);

    int insert(TOrderDaliyPlanItem record);

    int insertSelective(TOrderDaliyPlanItem record);

    List<TOrderDaliyPlanItem> selectbyDispLineNo(String empNo , String date,String reachTimeType);

    int updateByPrimaryKeySelective(TOrderDaliyPlanItem record);

    int updateByPrimaryKey(TOrderDaliyPlanItem record);
    
    int updateDaliyPlanItemStatus(TOrderDaliyPlanItem record);
    
    List<TOrderDaliyPlanItem> selectDaliyPlansByEntryNo(String itemNo);
    
    int updateDaliyPlanItem(TOrderDaliyPlanItem record);
    
    TOrderDaliyPlanItem selectDaliyPlansByEntryNoAndNo(TOrderDaliyPlanItemKey record);
    
    List<TOrderDaliyPlanItem> selectDaliyPlansByOrderNo(String orderNo);
    
    List<TOrderDaliyPlanItem> selectDaliyPlansByOrderNoAsc(String orderNo);
    
    int selectMaxDaliyPlansNoByOrderNo(String orderNo);

    String getDayOrderStat(String orderNo, Date date);

    int updateDaliyPlansToStop(TPreOrder record);

    int updateDaliyPlansToBack(TPreOrder record);
    
    List<TOrderDaliyPlanItem> selectDaliyPlansByBranchAndDay(RequireOrderSearch rModel);
}