package com.nhry.service.basic.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.dao.TMdBranchEmpMapper;
import com.nhry.data.basic.dao.TMdMaraExMapper;
import com.nhry.data.basic.dao.TMdMaraMapper;
import com.nhry.data.basic.domain.TMdBranchEmp;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.data.basic.domain.TMdMaraEx;
import com.nhry.exception.MessageCode;
import com.nhry.exception.ServiceException;
import com.nhry.model.basic.EmpQueryModel;
import com.nhry.model.basic.ProductQueryModel;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.BranchEmpService;
import com.nhry.service.basic.dao.ProductService;
import com.nhry.utils.Date;

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
}
