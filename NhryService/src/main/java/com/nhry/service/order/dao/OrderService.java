package com.nhry.service.order.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.order.OrderCreateModel;
import com.nhry.model.order.OrderSearchModel;

import  java.util.*;
public interface OrderService {
	
	PageInfo searchOrders(OrderSearchModel smodel);
	
	int createOrder(OrderCreateModel record);
	
	int editOrder(OrderCreateModel record);

}
