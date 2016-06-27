package com.nhry.service.milk.impl;

import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.milk.dao.TDispOrderItemMapper;
import com.nhry.data.milk.dao.TDispOrderMapper;
import com.nhry.model.milk.RouteOrderSearchModel;
import com.nhry.service.BaseService;
import com.nhry.service.milk.dao.DeliverMilkService;

public class DeliverMilkServiceImpl extends BaseService implements DeliverMilkService
{
	private TDispOrderItemMapper tDispOrderItemMapper;
	private TDispOrderMapper tDispOrderMapper;

	public void settDispOrderItemMapper(TDispOrderItemMapper tDispOrderItemMapper)
	{
		this.tDispOrderItemMapper = tDispOrderItemMapper;
	}

	public void settDispOrderMapper(TDispOrderMapper tDispOrderMapper)
	{
		this.tDispOrderMapper = tDispOrderMapper;
	}

	/* (non-Javadoc) 
	* @title: searchOrders
	* @description: 查询路单列表
	* @param smodel
	* @return 
	* @see com.nhry.service.milk.dao.DeliverMilkService#searchOrders(com.nhry.model.milk.RouteOrderSearchModel) 
	*/
	@Override
	public PageInfo searchRouteOrders(RouteOrderSearchModel smodel)
	{
		if(StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		return tDispOrderMapper.selectMilkboxsByPage(smodel);
	}

	/* (non-Javadoc) 
	* @title: searchRouteDetails
	* @description: 搜索路单详情
	* @param smodel
	* @return 
	* @see com.nhry.service.milk.dao.DeliverMilkService#searchRouteDetails(com.nhry.model.milk.RouteOrderSearchModel) 
	*/
	@Override
	public int searchRouteDetails(RouteOrderSearchModel smodel)
	{
//		tDispOrderMapper.s
//		tDispOrderItemMapper.selectItemsByKeys(record);
		return 0;
	}
	
	
	
}
