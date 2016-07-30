package com.nhry.service.external.dao;

import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.order.OrderSearchModel;

public interface EcService {
    
	/**
	 * 奶站更新时，往电商推送
	 */
	public void pushBranch2EcForUpt(TMdBranch branch);
	
	/**
	 * 获取订单列表
	 */
	public void getOrderList(OrderSearchModel model);
	
	/**
	 * 获取订单明细
	 */
	public void getOrderdtlinfo(OrderSearchModel model);
	
	/**
	 * 订单停订/续订
	 */
	public void sendOrderStopRe(OrderSearchModel model);
	
	/**
	 * 更新订单状态
	 */
	public void sendOrderStatus(TPreOrder order);
	
	/**
	 * 获取订户订单备注信息
	 */
	public void getOrderComments(TPreOrder order);
	
	/**
	 * 创建订户订单备注
	 */
	public void sendOrderComments(TPreOrder order);
}