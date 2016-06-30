package com.nhry.service.basic.impl;

import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.basic.dao.TBranchSalesListMapper;
import com.nhry.data.basic.dao.TMdMaraExMapper;
import com.nhry.data.basic.dao.TMdMaraMapper;
import com.nhry.data.basic.domain.TBranchSalesList;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.data.basic.domain.TMdMaraEx;
import com.nhry.model.basic.ProductQueryModel;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.ProductService;
import com.nhry.service.basic.pojo.ProductInfoExModel;
import com.nhry.utils.PrimaryKeyUtils;
import com.nhry.utils.date.Date;

import java.util.List;

public class ProductServiceImpl extends BaseService implements ProductService {

	private TMdMaraMapper tMdMaraMapper;
	private TMdMaraExMapper tMdMaraExMapper;
	private TBranchSalesListMapper salesListMapper;

	public void settMdMaraMapper(TMdMaraMapper tMdMaraMapper) {
		this.tMdMaraMapper = tMdMaraMapper;
	}

	public void settMdMaraExMapper(TMdMaraExMapper tMdMaraExMapper) {
		this.tMdMaraExMapper = tMdMaraExMapper;
	}

	@Override
	public TMdMara selectProductByCode(String productCode) {
		// TODO Auto-generated method stub
		return tMdMaraMapper.selectProductByCode(productCode);
	}

	@Override
	public int uptProductByCode(TMdMara record) {
		// TODO Auto-generated method stub
		TMdMara product = this.selectProductByCode(record.getMatnr());
		if (product == null) {
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该商品信息不存在!");
		}
		salesListMapper.delSalesListByMatnr(record.getMatnr());
		if(record.getSalesList() != null && record.getSalesList().size() > 0){
			for(TBranchSalesList sl : record.getSalesList()){
				sl.setListNo(PrimaryKeyUtils.generateUuidKey());
				sl.setCreateAt(new Date());
				sl.setCreateBy(this.userSessionService.getCurrentUser().getLoginName());
				sl.setCreateByTxt(this.userSessionService.getCurrentUser().getDisplayName());
				salesListMapper.addBranchSales(sl);
			}
		}
		record.setLastModified(new Date());
		if(record.getMaraEx() != null){
			this.uptProductExByCode(record.getMaraEx());
		}
		return tMdMaraMapper.updateProduct(record);
	}

	@Override
	public PageInfo searchProducts(ProductQueryModel smodel) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())) {
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		return tMdMaraMapper.searchProducts(smodel);
	}

	@Override
	public TMdMara selectProductAndExByCode(String matnr) {
		// TODO Auto-generated method stub
		return tMdMaraMapper.selectProductAndExByCode(matnr);
	}

	@Override
	public int pubProductByCode(String code) {
		// TODO Auto-generated method stub
		return tMdMaraMapper.pubProductByCode(code);
	}

	@Override
	public List<TMdMara> selectProductAndExListByCode(String productCode) {
		return tMdMaraMapper.selectProductAndExListByCode(productCode);
	}

	public void setSalesListMapper(TBranchSalesListMapper salesListMapper) {
		this.salesListMapper = salesListMapper;
	}

	@Override
	public int uptProductExByCode(TMdMaraEx maraEx) {
		// TODO Auto-generated method stub
		if(!StringUtils.isEmpty(maraEx.getMatnr())){
			maraEx.setLastModified(new Date());
			maraEx.setLastModifiedBy(this.userSessionService.getCurrentUser().getLoginName());
			maraEx.setLastModifiedByTxt(this.userSessionService.getCurrentUser().getDisplayName());
			this.tMdMaraExMapper.uptProductExByCode(maraEx);
		}else{
			this.tMdMaraExMapper.addMaraEx(maraEx);
		}
		return 1;
	}
}
