package com.nhry.model.milk;

import java.util.ArrayList;

import com.nhry.data.milk.domain.TDispOrder;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.wordnik.swagger.annotations.ApiModel;

@ApiModel(value = "RouteOrderModel", description = "路单对象")
public class RouteOrderModel {
	
	private TDispOrder order;
    
   private ArrayList<TDispOrderItem> entries = new ArrayList<TDispOrderItem>();

	public TDispOrder getOrder()
	{
		return order;
	}

	public void setOrder(TDispOrder order)
	{
		this.order = order;
	}

	public ArrayList<TDispOrderItem> getEntries()
	{
		return entries;
	}

	public void setEntries(ArrayList<TDispOrderItem> entries)
	{
		this.entries = entries;
	}

}