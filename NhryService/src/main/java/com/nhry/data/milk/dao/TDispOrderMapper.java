package com.nhry.data.milk.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.milk.domain.TDispOrder;
import com.nhry.data.milk.domain.TDispOrderKey;
import com.nhry.model.milk.RouteOrderSearchModel;
import com.nhry.model.milktrans.CreateEmpReturnboxModel;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public interface TDispOrderMapper {
	 List<TDispOrder> selectConfirmedDispOrderByDate(String branchNo,String date);
	
	 int deleteDispOrderByOrderNo(List<String> codeList);
    //查询今天奶站下的路单数
    List<TDispOrder> selectTodayDispOrderByBranchNo(String branchNo,Date date);

    int insert(TDispOrder record);

    PageInfo searchRoutePlansByPage(RouteOrderSearchModel smodel);

    TDispOrder selectByPrimaryKey(TDispOrderKey key);

    TDispOrder selectYestodayDispOrderByEmp(TDispOrder record);

    int updateDispOrderStatus(String orderCode,String status);
    BigDecimal creatRecBot(CreateEmpReturnboxModel cModel);

    TDispOrder getDispOrderByNo(String dispOrderNo);
    
    int updateDispOrderEmp(TDispOrder order);

    //查询日期下奶站下已确认的路单数
    List<TDispOrder> selectConfirmDispOrderByBranchNoAndDay(String branchNo,Date date);
    //查询日期下奶站下的路单数
    List<TDispOrder> selectDispOrderByBranchNoAndDay(String branchNo,Date date);

}