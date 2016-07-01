package com.nhry.service.basic.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.basic.dao.TMaraPriceRelMapper;
import com.nhry.data.basic.dao.TMdBranchMapper;
import com.nhry.data.basic.dao.TMdDealerMapper;
import com.nhry.data.basic.dao.TMdPriceBranchMapper;
import com.nhry.data.basic.dao.TMdPriceMapper;
import com.nhry.data.basic.domain.TMaraPriceRel;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.basic.domain.TMdDealer;
import com.nhry.data.basic.domain.TMdPrice;
import com.nhry.data.basic.domain.TMdPriceBranch;
import com.nhry.model.basic.PriceQueryModel;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.PriceService;
import com.nhry.service.basic.pojo.PriceGroup;
import com.nhry.utils.PrimaryKeyUtils;
import com.nhry.utils.date.Date;

public class PriceServiceImpl extends BaseService implements PriceService {
	private TMdPriceMapper tMdPriceMapper;
	private TMaraPriceRelMapper maraPriceMapper;
	private TMdPriceBranchMapper priceBranchMapper;
	private TMdBranchMapper branchMapper;
	private TMdDealerMapper dealerMapper;

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
		record.setStatus("Y");
		record.setSalesOrg(this.userSessionService.getCurrentUser().getSalesOrg());
		tMdPriceMapper.addNewPriceGroup(record);
		//维护产品与价格组关系
		mergeMaraPriceRel(record.getId(),record.getMprices());
		return 1;
	}

	@Override
	public int disablePriceGroup(String id) {
		TMdPrice priceGroup = this.selectPriceGroupByCode(id);
		if (priceGroup == null) {
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该价格组不存在!");
		}
		priceGroup.setLastModified(new Date());
		priceGroup.setLastModifiedBy(this.userSessionService.getCurrentUser().getLoginName());
		priceGroup.setLastModifiedByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		return tMdPriceMapper.disablePriceGroup(priceGroup);
	}

	@Override
	public int updatePriceGroup(TMdPrice record) {
		if(StringUtils.isEmpty(record.getId()) || StringUtils.isEmpty(record.getPriceGroup()) || StringUtils.isEmpty(record.getPriceType())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "价格组编号(id)、价格组名称(priceGroup)、价格组类型(priceType)不能为空!");
		}
		TMdPrice priceGroup = this.selectPriceGroupByCode(record.getId());
		if (priceGroup == null) {
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该价格组不存在!");
		}
		record.setLastModified(new Date());
		record.setLastModifiedBy(this.userSessionService.getCurrentUser().getLoginName());
		record.setLastModifiedByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		tMdPriceMapper.updatePriceGroup(record);
		//维护产品与价格组关系
		mergeMaraPriceRel(record.getId(),record.getMprices());
		return 1;
	}

	@Override
	public PageInfo searchPriceGroups(PriceQueryModel smodel) {
		if (StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())) {
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		smodel.setSalesOrg(this.userSessionService.getCurrentUser().getSalesOrg());
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
	public int mergeMaraPriceRel(String id,List<TMaraPriceRel> records) {
		// TODO Auto-generated method stub
		//移除原先关联的商品信息
		this.maraPriceMapper.delMaraPricesById(id);
		//添加新的商品关联信息
		if(records != null && records.size() > 0){
			for(TMaraPriceRel mp : records){
				mp.setRelNo(PrimaryKeyUtils.generateUuidKey());
				mp.setId(id);
				mp.setCreateAt(new Date());
				mp.setCreateBy(this.userSessionService.getCurrentUser().getLoginName());
				mp.setCreateByTxt(this.userSessionService.getCurrentUser().getDisplayName());
				this.maraPriceMapper.addMaraPrice(mp);
			}
		}
		return 1;
	}

	@Override
	public int addPriceBranch(TMdPriceBranch record) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(record.getBranchNo()) || StringUtils.isEmpty(record.getId()) || StringUtils.isEmpty(record.getPriceType())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "奶站编号(branchNo)、价格编号(id)和价格组类型(priceType)不能为空!");
		}
		TMdBranch branch = this.branchMapper.selectBranchByNo(record.getBranchNo());
		if(branch == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该奶站编号(branchNo)对应的奶站不存在!");
		}
		TMdPrice price = tMdPriceMapper.selectPriceGroupById(record.getId());
		if(price == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该价格组编号(branchNo)对应的价格组不存在!");
		}
		int count = priceBranchMapper.findPriceBrachCountByPt(record);
		if(count > 0){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该奶站下面存在该价格组类型的价格组，不允许重复添加同一类型价格组!");
		}
		record.setCreateAt(new Date());
		record.setCreateBy(this.userSessionService.getCurrentUser().getLoginName());
		record.setCreateByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		return this.priceBranchMapper.addPriceBranch(record);
	}

	@Override
	public int delPriceBranchByNo(String branchNo) {
		// TODO Auto-generated method stub
		Map<String,String> attrs = new HashMap<String,String>();
		attrs.put("branchNo", branchNo);
		int count = this.priceBranchMapper.findPriceBranchCount(attrs);
		if(count == 0){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该奶站编号(branchNo)对应的奶站价格组关系记录不存在!");
		}
		return this.priceBranchMapper.delPriceBranchByNo(branchNo);
	}

	@Override
	public int delPriceBranchById(String id) {
		// TODO Auto-generated method stub
		Map<String,String> attrs = new HashMap<String,String>();
		attrs.put("id", id);
		int count = this.priceBranchMapper.findPriceBranchCount(attrs);
		if(count == 0){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该价格编号(id)对应的奶站价格组关系记录不存在!");
		}
		return this.priceBranchMapper.delPriceBranchById(id);
	}

	@Override
	public int delPriceBranch(String branchNo,String id) {
		// TODO Auto-generated method stub
		Map<String,String> attrs = new HashMap<String,String>();
		attrs.put("branchNo", branchNo);
		attrs.put("id", id);
		int count = this.priceBranchMapper.findPriceBranchCount(attrs);
		if(count == 0){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该奶站编号(branchNo)和价格编号(id)对应的奶站价格组关系记录不存在!");
		}
		return priceBranchMapper.delPriceBranch(attrs);
	}

	public void setPriceBranchMapper(TMdPriceBranchMapper priceBranchMapper) {
		this.priceBranchMapper = priceBranchMapper;
	}

	public void setBranchMapper(TMdBranchMapper branchMapper) {
		this.branchMapper = branchMapper;
	}

	@Override
	public float getMaraPrice(String branchNo, String matnr, String deliveryType) {
		// TODO Auto-generated method stub
		Map<String,String> attrs = new HashMap<String,String>();
		attrs.put("branchNo", branchNo);
		attrs.put("matnr", matnr);
		//先查询该奶站上该商品关联的价格组列表
		List<PriceGroup> prices = this.tMdPriceMapper.findMaraPriceBymatnrAndNo(attrs);
		PriceGroup price = null;
		if(prices != null && prices.size() > 0){
			price = prices.get(0);
		}else{
			//奶站上没有关联如何价格组、查询公司上的价格组(渠道价格组)
			price = this.tMdPriceMapper.findMaraPriceBymatnrAndOrg(attrs);
		}
		if(price == null){
			//如果公司和奶站对于该商品都适用的价格组
			return -1.0f;
		}
		if("10".equals(deliveryType)){
			//自取价
			return prices.get(0).getPrice1();
		}else if("20".equals(deliveryType)){
			//订户价
			return prices.get(0).getPrice2();
		}
		return -1.0f;
	}

	public void setDealerMapper(TMdDealerMapper dealerMapper) {
		this.dealerMapper = dealerMapper;
	}

	@Override
	public List<TMdDealer> getDealers() {
		// TODO Auto-generated method stub
		List<TMdDealer> list = this.dealerMapper.findDealersBySalesOrg(this.userSessionService.getCurrentUser().getSalesOrg());
		if(list == null){
			list = new ArrayList<TMdDealer>();
		}
		TMdDealer dealer = new TMdDealer();
		dealer.setDealerNo("-1");
		dealer.setDealerName("自营奶站");
		list.add(dealer);
		return list;
	}
}
