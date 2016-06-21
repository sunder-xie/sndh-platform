package com.nhry.model.order;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;

import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.wordnik.swagger.annotations.ApiModel;

@ApiModel(value = "OrderCreateModel", description = "订单创建对象")
public class OrderCreateModel {
	
	private TPreOrder order;
    
   private ArrayList<TPlanOrderItem> entries = new ArrayList<TPlanOrderItem>();

	public TPreOrder getOrder()
	{
		return order;
	}

	public void setOrder(TPreOrder order)
	{
		this.order = order;
	}

	public ArrayList<TPlanOrderItem> getEntries()
	{
		return entries;
	}

	public void setEntries(ArrayList<TPlanOrderItem> entries)
	{
		this.entries = entries;
	}


    

}