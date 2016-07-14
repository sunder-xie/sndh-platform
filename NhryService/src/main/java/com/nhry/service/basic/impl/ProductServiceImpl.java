package com.nhry.service.basic.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.dao.TBranchNotsellListMapper;
import com.nhry.data.basic.dao.TMdMaraExMapper;
import com.nhry.data.basic.dao.TMdMaraMapper;
import com.nhry.data.basic.domain.TBranchNotsellList;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.data.basic.domain.TMdMaraEx;
import com.nhry.model.basic.ProductQueryModel;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.BranchService;
import com.nhry.service.basic.dao.ProductService;
import com.nhry.utils.PrimaryKeyUtils;
import com.nhry.utils.date.Date;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductServiceImpl extends BaseService implements ProductService {

	private TMdMaraMapper tMdMaraMapper;
	private TMdMaraExMapper tMdMaraExMapper;
	private TBranchNotsellListMapper notsellListMapper;
	private BranchService branchSevice;

	public void settMdMaraMapper(TMdMaraMapper tMdMaraMapper) {
		this.tMdMaraMapper = tMdMaraMapper;
	}

	public void settMdMaraExMapper(TMdMaraExMapper tMdMaraExMapper) {
		this.tMdMaraExMapper = tMdMaraExMapper;
	}

	@Override
	public TMdMara selectProductByCode(String productCode) {
		// TODO Auto-generated method stub
		Map<String,String> attrs = new HashMap<String,String>();
		attrs.put("salesOrg", this.userSessionService.getCurrentUser().getSalesOrg());
		attrs.put("matnr", productCode);
		return tMdMaraMapper.selectProductByCode(attrs);
	}

	@Override
	public int uptProductByCode(TMdMara record) {
		// TODO Auto-generated method stub
		TMdMara product = this.selectProductByCode(record.getMatnr());
		if (product == null) {
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该商品信息不存在!");
		}
		this.notsellListMapper.delBranchNotsellByMatnr(record.getMatnr());
		if(record.getNotsellList() != null && record.getNotsellList().size() > 0){
			for(TBranchNotsellList nl : record.getNotsellList()){
				nl.setListNo(PrimaryKeyUtils.generateUuidKey());
				nl.setSalesOrg(this.userSessionService.getCurrentUser().getSalesOrg());
				nl.setCreateAt(new Date());
				nl.setMatnr(record.getMatnr());
				nl.setCreateBy(this.userSessionService.getCurrentUser().getLoginName());
				nl.setCreateByTxt(this.userSessionService.getCurrentUser().getDisplayName());
				notsellListMapper.addBranchNotSell(nl);
			}
		}
		if(record.getMaraEx() != null){
			this.uptProductExByCode(record.getMatnr(),record.getMaraEx());
		}
		return 1;
	}

	@Override
	public PageInfo searchProducts(ProductQueryModel smodel) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(this.userSessionService.getCurrentUser().getSalesOrg())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"当前用户的关联的组织信息不全,请先维护好当前用户信息!");
		}
		if (StringUtils.isEmpty(smodel.getPageNum()) || StringUtils.isEmpty(smodel.getPageSize())) {
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		smodel.setSalesOrg(this.userSessionService.getCurrentUser().getSalesOrg());
//		smodel.setDealerNo(this.userSessionService.getCurrentUser().getDealerId());
		return tMdMaraMapper.searchProducts(smodel);
	}

	@Override
	public TMdMara selectProductAndExByCode(String matnr) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(this.userSessionService.getCurrentUser().getSalesOrg())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"当前用户的关联的组织信息不全,请先维护好当前用户信息!");
		}
		Map<String,String> attrs = new HashMap<String,String>();
		attrs.put("matnr",matnr);
		attrs.put("salesOrg",this.userSessionService.getCurrentUser().getSalesOrg());
		TMdMara mara = tMdMaraMapper.selectProductAndExByCode(attrs);
		if(mara != null){
			this.notsellListMapper.getNotSellListByMatnr(matnr);
		}
		return tMdMaraMapper.selectProductAndExByCode(attrs);
	}

	@Override
	public int pubProductByCode(String code) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(this.userSessionService.getCurrentUser().getSalesOrg())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"当前用户的关联的组织信息不全,请先维护好当前用户信息!");
		}
		Map<String,String> attrs = new HashMap<String,String>();
		attrs.put("code", code);
		attrs.put("salesOrg",this.userSessionService.getCurrentUser().getSalesOrg());
		return tMdMaraMapper.pubProductByCode(attrs);
	}

	@Override
	public List<TMdMara> selectProductAndExListByCode(String productCode) {
		if(StringUtils.isEmpty(this.userSessionService.getCurrentUser().getSalesOrg())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"当前用户的关联的组织信息不全,请先维护好当前用户信息!");
		}
		Map<String,String> attrs = new HashMap<String,String>();
		if(!StringUtils.isEmpty(productCode)){
			attrs.put("productCode","%"+productCode+"%");
		}
		attrs.put("salesOrg",this.userSessionService.getCurrentUser().getSalesOrg());
		return tMdMaraMapper.selectProductAndExListByCode(attrs);
	}

	@Override
	public int uptProductExByCode(String matnr,TMdMaraEx maraEx) {
		// TODO Auto-generated method stub
		Map<String,String> attrs = new HashMap<String,String>();
		attrs.put("matnr", matnr);
		attrs.put("salesOrg",this.userSessionService.getCurrentUser().getSalesOrg());
		TMdMaraEx ex = this.tMdMaraExMapper.findProductExByCode(attrs);
		if(ex == null){
			maraEx.setMatnr(matnr);
			maraEx.setSalesOrg(this.userSessionService.getCurrentUser().getSalesOrg());
			maraEx.setCreateAt(new Date());
			maraEx.setCreateBy(this.userSessionService.getCurrentUser().getLoginName());
			maraEx.setCreateByTxt(this.userSessionService.getCurrentUser().getDisplayName());
			this.tMdMaraExMapper.addMaraEx(maraEx);
		}else{
			maraEx.setMatnr(matnr);
			maraEx.setSalesOrg(this.userSessionService.getCurrentUser().getSalesOrg());
			maraEx.setLastModified(new Date());
			maraEx.setLastModifiedBy(this.userSessionService.getCurrentUser().getLoginName());
			maraEx.setLastModifiedByTxt(this.userSessionService.getCurrentUser().getDisplayName());
			this.tMdMaraExMapper.uptProductExByCode(maraEx);
		}
		return 1;
	}

	public void setNotsellListMapper(TBranchNotsellListMapper notsellListMapper) {
		this.notsellListMapper = notsellListMapper;
	}

	@Override
	public PageInfo getBranchSaleMaras(ProductQueryModel pm) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(pm.getBranchNo())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"奶站编号(branchNo)不能为空!");
		}
		TMdBranch branch = branchSevice.selectBranchByNo(pm.getBranchNo());
		if(branch != null){
			pm.setSalesOrg(branch.getSalesOrg());
			if(StringUtils.isEmpty(branch.getDealerNo())){
				//自营奶站
				return this.tMdMaraMapper.getCompMaras(pm);
			}else{
				//经销商奶站
				pm.setDealerNo(branch.getDealerNo());
				return this.tMdMaraMapper.getDealerMaras(pm);
			}
		}
		return null;
	}

	@Override
	public List<TMdMara>  getProductByCodeOrName(String product) {
		TSysUser user = userSessionService.getCurrentUser();
		Map<String,String > map = new HashMap<String,String>();
			map.put("matnr",product);
			map.put("matnrTxt",product);
			map.put("salesOrg",user.getSalesOrg());
		return tMdMaraMapper.getProductByCodeOrNameAndSalesOrg(map);
	}

	public void setBranchSevice(BranchService branchSevice) {
		this.branchSevice = branchSevice;
	}

	@Override
	public List<TMdMara> findMarasBySalesCodeAndOrg(String id) {
		// TODO Auto-generated method stub
		Map<String,String> attrs = new HashMap<String,String>();
		attrs.put("salesOrg",this.userSessionService.getCurrentUser().getSalesOrg());
//		attrs.put("dealerNo",this.userSessionService.getCurrentUser().getDealerId());
		if(!StringUtils.isEmpty(id) && !"-1".equals(id)){
			attrs.put("id",id);
		}
		return this.tMdMaraMapper.findMarasBySalesCodeAndOrg(attrs);
	}
}
