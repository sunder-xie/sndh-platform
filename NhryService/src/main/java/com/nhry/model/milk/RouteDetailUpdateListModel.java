package com.nhry.model.milk;

import java.util.ArrayList;
import java.util.List;

import com.wordnik.swagger.annotations.ApiModel;

@ApiModel(value = "RouteDetailUpdateListModel", description = "路单详细更新对象List")
public class RouteDetailUpdateListModel {
	
	private List<RouteDetailUpdateModel> list = new ArrayList<RouteDetailUpdateModel>();;

	public List<RouteDetailUpdateModel> getList()
	{
		return list;
	}

	public void setList(List<RouteDetailUpdateModel> list)
	{
		this.list = list;
	}
	
}