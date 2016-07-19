package com.nhry.data.order.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TOrderDaliyPlanItemKey;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.milktrans.RequireOrderSearch;
import com.nhry.model.order.OrderSearchModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface TOrderDaliyPlanItemMapper {
	
	 List<TOrderDaliyPlanItem> searchDaliyPlansByStatus(String orderNo, String status1,String status2,String status3);
	
	 PageInfo selectDaliyOrdersByPages(OrderSearchModel smodel);
	
	 int deleteFromDateToDate(TOrderDaliyPlanItem record);

    int insert(TOrderDaliyPlanItem record);

    BigDecimal selectDaliyPlansRemainAmt(TOrderDaliyPlanItemKey record);

    List<TOrderDaliyPlanItem> selectbyDispLineNo(String empNo , String date,String reachTimeType);

    int updateByPrimaryKeySelective(TOrderDaliyPlanItem record);

    int updateFromDateToDate(TOrderDaliyPlanItem record);
    
    int updateDaliyPlanItemStatus(TOrderDaliyPlanItem record);
    
    List<TOrderDaliyPlanItem> selectDaliyPlansByEntryNo(String itemNo);
    
    int updateDaliyPlanItem(TOrderDaliyPlanItem record);
    
    TOrderDaliyPlanItem selectDaliyPlansByEntryNoAndNo(TOrderDaliyPlanItemKey record);
    
    List<TOrderDaliyPlanItem> selectDaliyPlansByOrderNo(String orderNo);
    
    List<TOrderDaliyPlanItem> selectDaliyPlansByOrderNoAsc(String orderNo);
    
    int selectMaxDaliyPlansNoByOrderNo(String orderNo);

    String getDayOrderStat(String orderNo, Date date);
    
    List<TOrderDaliyPlanItem> searchDaliyOrdersByOrderNoAndFinalStop(OrderSearchModel smodel);
    
    int updateDaliyPlansToStop(TPreOrder record);

    int updateDaliyPlansToBack(TPreOrder record);
    
    List<TOrderDaliyPlanItem> selectDaliyPlansByBranchAndDay(RequireOrderSearch rModel);

    List<String> getDailOrderPromOfDealerBranch(RequireOrderSearch rModel);

    List<TOrderDaliyPlanItem> selectProDayPlanOfDealerBranch(RequireOrderSearch rModel);

    List<TOrderDaliyPlanItem> selectNoProDayPlanOfDealerBranch(RequireOrderSearch rModel);

    List<String> getDailOrderPromOfSelfBranch(RequireOrderSearch rModel);

    List<TOrderDaliyPlanItem> selectNoProDayPlanOfSelfBranch(RequireOrderSearch rModel);

    List<TOrderDaliyPlanItem> selectProDayPlanOfSelfBranch(RequireOrderSearch rModel);

    List<TOrderDaliyPlanItem> getProductItemsByOrderNo(String orderCode,String salesOrg);
}