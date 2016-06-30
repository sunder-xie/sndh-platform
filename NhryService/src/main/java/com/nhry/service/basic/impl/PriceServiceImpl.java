package com.nhry.service.basic.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.basic.dao.TMaraPriceRelMapper;
import com.nhry.data.basic.dao.TMdPriceMapper;
import com.nhry.data.basic.domain.TMaraPriceRel;
import com.nhry.data.basic.domain.TMdPrice;
import com.nhry.model.basic.PriceQueryModel;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.PriceService;
import com.nhry.utils.PrimaryKeyUtils;
import com.nhry.utils.date.Date;

public class PriceServiceImpl extends BaseService implements PriceService {

	private TMdPriceMapper tMdPriceMapper;
	private TMaraPriceRelMapper maraPriceMapper;

	public void settMdPriceMapper(TMdPriceMapper tMdPriceMapper) {
		this.tMdPriceMapper = tMdPriceMapper;
	}

	@Override
	public int addNewPriceGroup(TMdPrice record) {
		// TODO Auto-generated method stub
		record.setId(PrimaryKeyUtils.generateUuidKey());
		record.setCreateBy(this.userSessionService.getCurrentUser().getLoginName());
		record.setCreateByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		record.setCreateAt(new Date());
		tMdPriceMapper.addNewPriceGroup(record);
		if(record.getMprices() != null && record.getMprices().size() > 0){
			mergeMaraPriceRel(record.getMprices());
		}
		return 1;
	}

	@Override
	public int disablePriceGroup(TMdPrice record) {
		TMdPrice priceGroup = this.selectPriceGroupByCode(record.getId());
		if (priceGroup == null) {
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该价格组不存在!");
		}
		record.setLastModified(new Date());
		record.setLastModifiedBy(this.userSessionService.getCurrentUser().getLoginName());
		record.setLastModifiedByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		return tMdPriceMapper.disablePriceGroup(record);
	}

	@Override
	public int updatePriceGroup(TMdPrice record) {
		TMdPrice priceGroup = this.selectPriceGroupByCode(record.getId());
		if (priceGroup == null) {
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该价格组不存在!");
		}
		record.setLastModified(new Date());
		record.setLastModifiedBy(this.userSessionService.getCurrentUser().getLoginName());
		record.setLastModifiedByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		tMdPriceMapper.updatePriceGroup(record);
		if(record.getMprices() != null && record.getMprices().size() > 0){
			mergeMaraPriceRel(record.getMprices());
		}
		return 1;
	}

	@Override
	public PageInfo searchPriceGroups(PriceQueryModel smodel) {
		if (StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())) {
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		return tMdPriceMapper.searchPriceGroups(smodel);
	}

	@Override
	public TMdPrice selectPriceGroupByCode(String id) {
		// TODO Auto-generated method stub
		return tMdPriceMapper.selectPriceGroupById(id);
	}

	public void setMaraPriceMapper(TMaraPriceRelMapper maraPriceMapper) {
		this.maraPriceMapper = maraPriceMapper;
	}

	@Override
	public int mergeMaraPriceRel(List<TMaraPriceRel> records) {
		// TODO Auto-generated method stub
		for(TMaraPriceRel mp : records){
			if(mp == null){
				continue;
			}
			if("add".equals(mp.getEditStatus())){
				mp.setRelNo(PrimaryKeyUtils.generateUuidKey());
				mp.setCreateAt(new Date());
				mp.setCreateBy(this.userSessionService.getCurrentUser().getLoginName());
				mp.setCreateByTxt(this.userSessionService.getCurrentUser().getDisplayName());
				this.maraPriceMapper.addMaraPrice(mp);
			}else if("delete".equals(mp.getEditStatus())){
				if(!StringUtils.isEmpty(mp.getRelNo())){
					this.maraPriceMapper.delMaraPriceByNo(mp.getRelNo());
				}
			}else if("update".equals(mp.getEditStatus())){
				if(!StringUtils.isEmpty(mp.getRelNo())){
					mp.setLastModified(new Date());
					mp.setLastModifiedBy(this.userSessionService.getCurrentUser().getLoginName());
					mp.setLastModifiedByTxt(this.userSessionService.getCurrentUser().getDisplayName());
					this.maraPriceMapper.uptMaraPricerel(mp);
				}
			}
		}
		return 1;
	}
}
