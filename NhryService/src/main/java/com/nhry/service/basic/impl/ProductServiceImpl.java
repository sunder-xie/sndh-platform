package com.nhry.service.basic.impl;


import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.basic.dao.TMdMaraExMapper;
import com.nhry.data.basic.dao.TMdMaraMapper;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.data.basic.domain.TMdMaraEx;
import com.nhry.model.basic.ProductQueryModel;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.ProductService;
import com.nhry.service.basic.pojo.ProductInfoExModel;
import com.nhry.utils.date.Date;

import java.util.List;

public class ProductServiceImpl extends BaseService implements ProductService {
	
	private TMdMaraMapper tMdMaraMapper;
	private TMdMaraExMapper tMdMaraExMapper;
	
	public void settMdMaraMapper(TMdMaraMapper tMdMaraMapper)
	{
		this.tMdMaraMapper = tMdMaraMapper;
	}

	public void settMdMaraExMapper(TMdMaraExMapper tMdMaraExMapper)
	{
		this.tMdMaraExMapper = tMdMaraExMapper;
	}

	@Override
	public TMdMara selectProductByCode(String productCode) {
		// TODO Auto-generated method stub
		return tMdMaraMapper.selectProductByCode(productCode);
	}

	@Override
	public int uptProductExByCode(TMdMaraEx record) {
		// TODO Auto-generated method stub
		TMdMara product = this.selectProductByCode(record.getMatnr());
		if(product == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该商品信息不存在!");
		}
		record.setLastModified(new Date());
		return tMdMaraExMapper.uptProductExByCode(record);
	}

	@Override
	public PageInfo searchProducts(ProductQueryModel smodel) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		return tMdMaraMapper.searchProducts(smodel);
	}

	@Override
	public ProductInfoExModel selectProductAndExByCode(String matnr)
	{
		// TODO Auto-generated method stub
		return tMdMaraMapper.selectProductAndExByCode(matnr);
	}

	@Override
	public int pubProductByCode(String code)
	{
		// TODO Auto-generated method stub
		return tMdMaraMapper.pubProductByCode(code);
	}
	
	@Override
	public List<ProductInfoExModel> selectProductAndExListByCode(String productCode) {
		return tMdMaraMapper.selectProductAndExListByCode(productCode);
	}
}
