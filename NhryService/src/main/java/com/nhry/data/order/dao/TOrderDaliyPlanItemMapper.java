package com.nhry.data.order.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.data.order.domain.TMstYearCardCompOrder;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TOrderDaliyPlanItemKey;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.milktrans.RequireOrderSearch;
import com.nhry.model.order.OrderDaliyPlanReportEntityModel;
import com.nhry.model.order.OrderDaliyPlanReportModel;
import com.nhry.model.order.OrderSearchModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface TOrderDaliyPlanItemMapper {
	
	 List<TOrderDaliyPlanItem> selectDaliyOrdersAll(OrderSearchModel smodel);
	 
	 int updateDaliyPlansToStopDateToDate(TOrderDaliyPlanItem record);
	
	 int deletePlansForLongEdit(TOrderDaliyPlanItem record);
	
	 List<TOrderDaliyPlanItem> searchDaliyPlansByStatus(String orderNo, String status1,String status2,String status3);
	
	 PageInfo selectDaliyOrdersByPages(OrderSearchModel smodel);
	
	 int deleteFromDateToDate(TOrderDaliyPlanItem record);
	 
	 int deletePlansByAmt(String orderNo);

	 int updateDaliyPlanItemRemainAmt(TOrderDaliyPlanItem record);
	 
    int insert(TOrderDaliyPlanItem record);

    BigDecimal selectDaliyPlansRemainAmt(TOrderDaliyPlanItemKey record);

    List<TOrderDaliyPlanItem> selectbyDispLineNo(String empNo , String date,String reachTimeType,String branch);
    List<TOrderDaliyPlanItem> selectbyDispLineNo2(String empNo , String date,String reachTimeType,String branch);

    int updateByPrimaryKeySelective(TOrderDaliyPlanItem record);

    int updateFromDateToDate(TOrderDaliyPlanItem record);
    
    int updateDaliyPlanItemStatus(TOrderDaliyPlanItem record);
    
    List<TOrderDaliyPlanItem> selectDaliyPlansByEntryNo(String itemNo);
    
    int updateDaliyPlanItem(TOrderDaliyPlanItem record);


    
    TOrderDaliyPlanItem selectDaliyPlansByEntryNoAndNo(TOrderDaliyPlanItemKey record);
    
    List<TOrderDaliyPlanItem> selectDaliyPlansByOrderNo(String orderNo);
    List<TOrderDaliyPlanItem> selectDaliyPlansByOrderNo2(String orderNo);
    
    List<TOrderDaliyPlanItem> selectDaliyPlansByOrderNoAsc(String orderNo);
    
    int selectMaxDaliyPlansNoByOrderNo(String orderNo);

    String getDayOrderStat(String orderNo, Date date);
    
    List<TOrderDaliyPlanItem> searchDaliyOrdersByOrderNoAndFinalStop(OrderSearchModel smodel);
    
    int updateDaliyPlansToStop(TPreOrder record);

    int updateDaliyPlansToBack(TPreOrder record);
    
    List<TOrderDaliyPlanItem> selectDaliyPlansByBranchAndDay(RequireOrderSearch rModel);

    List<String> getDailOrderPromOfDealerBranch(RequireOrderSearch rModel);

    List<TOrderDaliyPlanItem> selectProDayPlanOfDealerBranch(RequireOrderSearch rModel);

    List<Map<String,Object>> selectProDayPlanOfSelfOrg(RequireOrderSearch rModel);

    List<TOrderDaliyPlanItem> selectNoProDayPlanOfDealerBranch(RequireOrderSearch rModel);

    List<TOrderDaliyPlanItem> selectProDayPlanOfDealerBranch40(RequireOrderSearch rModel);

    List<Map<String,Object>> selectNoProDayPlanOfDealerBranch70(RequireOrderSearch rModel);

    List<TOrderDaliyPlanItem> selectNoProDayPlanOfDealerBranch40(RequireOrderSearch rModel);

    List<String> getDailOrderPromOfSelfBranch(RequireOrderSearch rModel);

    List<TOrderDaliyPlanItem> selectNoProDayPlanOfSelfBranch(RequireOrderSearch rModel);

    List<TOrderDaliyPlanItem> selectProDayPlanOfSelfBranch(RequireOrderSearch rModel);

    List<TOrderDaliyPlanItem> selectNoProDayPlanOfSelfBranch40(RequireOrderSearch rModel);

    List<TOrderDaliyPlanItem> getProductItemsByOrderNo(String orderCode,String salesOrg);

    int deletePlansByOrder(String orderNo);

    List<OrderDaliyPlanReportEntityModel> reportOrderDaliyPlanByParams(OrderDaliyPlanReportModel model);

    BigDecimal getOrderOrderDailyFinishAmtByOrderNo(String orderNo);

    List<TOrderDaliyPlanItem> selectbyDispLineNoByOrderNos(String empNo, String format, String orderType, String branchNo, List<String> orderNos);

    List<TOrderDaliyPlanItem> selectDaliyPlanByOrderAndDispDate(String orderNo, Date date);

    int deleteDailyByStopDate(OrderSearchModel model);

    BigDecimal getSumDailyBackAmtByBackDate(OrderSearchModel model);

    Date selectEndDispDate(String itemNo);

    TOrderDaliyPlanItem selectByDateAndItemNoAndNo(TOrderDaliyPlanItem plan);

    List<TOrderDaliyPlanItem> selectByBeforeDayAndNo(TOrderDaliyPlanItem oplan);

    int updateDaliyPlanItemByItemNo(TOrderDaliyPlanItem record);

    List<TOrderDaliyPlanItem> selectDaliyByAfterDayAndNo(TOrderDaliyPlanItem newPlan);

    int deleteFromDate(TOrderDaliyPlanItem item);

    int deleteOneDayItem(TOrderDaliyPlanItem plan);

    List<TOrderDaliyPlanItem> selectByDayAndNo(TOrderDaliyPlanItem plan);

    List<TOrderDaliyPlanItem> selectByDayAndNoBetweenDays(OrderSearchModel omodel);

    int updateDaliyPlansToStopByStatus(OrderSearchModel omodel);

    int updateDaliyReachTimeTypeItemNo(TOrderDaliyPlanItem item);

    int selectStatusDailyPlansCounts(String orderNo);

    BigDecimal selectInitAmtDaysByNo(String orderNo);

    BigDecimal selectCurAmtDaysByNo(String orderNo);

    int deleteFromDateByStatus(TOrderDaliyPlanItem newplan);

    int deleteFromDateByStatusAndProm(TOrderDaliyPlanItem newplan);

    TOrderDaliyPlanItem selectDaliyByDispItem(TDispOrderItem entry);

    int  updateDaliyRemainAmtAfterRouteConfirmBeforDay(Date dispDate, BigDecimal cj, String orderNo);

    List<TOrderDaliyPlanItem> selectPromDaliyBetweenDaysAndNo(String orderNo,Date startDate, Date endDate);

    int  updateDaliyPlanItemStatusBatch(HashMap<String, Object> map);

    Date selectFirstDispDateByOrder(String orderNo);

    TMstYearCardCompOrder selectYearCardBackOrder(String orderNo,Date backDate);

    int updateDaliyRemainAmtAfterAdvanceBack(String orderNo, BigDecimal backAmt);
}