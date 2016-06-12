package com.nhry.service.basic.impl;


import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.dao.TMdPriceMapper;
import com.nhry.data.basic.domain.TMdPrice;
import com.nhry.exception.MessageCode;
import com.nhry.exception.ServiceException;
import com.nhry.model.basic.PriceQueryModel;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.PriceService;
import com.nhry.utils.Date;

public class PriceServiceImpl extends BaseService implements PriceService {
	
	private TMdPriceMapper tMdPriceMapper;
	
	public void settMdPriceMapper(TMdPriceMapper tMdPriceMapper)
	{
		this.tMdPriceMapper = tMdPriceMapper;
	}

	@Override
	public int addNewPriceGroup(TMdPrice record)
	{
		// TODO Auto-generated method stub
		record.setId(null);
		record.setCreateAt(new Date());
		return tMdPriceMapper.addNewPriceGroup(record);
	}

	@Override
	public int disablePriceGroup(TMdPrice record)
	{
		TMdPrice priceGroup = this.selectPriceGroupByCode(record.getId());
		if(priceGroup == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该价格组不存在!");
		}
		record.setLastModified(new Date());
		return tMdPriceMapper.disablePriceGroup(record);
	}

	@Override
	public int updatePriceGroup(TMdPrice record)
	{
		TMdPrice priceGroup = this.selectPriceGroupByCode(record.getId());
		if(priceGroup == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该价格组不存在!");
		}
		record.setLastModified(new Date());
		return tMdPriceMapper.updatePriceGroup(record);
	}

	@Override
	public PageInfo searchPriceGroups(PriceQueryModel smodel)
	{
		if(StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		return tMdPriceMapper.searchPriceGroups(smodel);
	}

	@Override
	public TMdPrice selectPriceGroupByCode(Integer id)
	{
		// TODO Auto-generated method stub
		return tMdPriceMapper.selectPriceGroupByCode(id);
	}
	
}
