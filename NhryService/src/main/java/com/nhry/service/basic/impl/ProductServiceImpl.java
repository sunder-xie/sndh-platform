package com.nhry.service.basic.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.dao.TBranchNotsellListMapper;
import com.nhry.data.basic.dao.TMdBranchMapper;
import com.nhry.data.basic.dao.TMdMaraExMapper;
import com.nhry.data.basic.dao.TMdMaraMapper;
import com.nhry.data.basic.domain.TBranchNotsellList;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.data.basic.domain.TMdMaraEx;
import com.nhry.model.basic.BranchSalesOrgModel;
import com.nhry.model.basic.ProductQueryModel;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.BranchService;
import com.nhry.service.basic.dao.ProductService;
import com.nhry.service.basic.dao.TSysMessageService;
import com.nhry.service.basic.pojo.BotType;
import com.nhry.service.stock.dao.TSsmStockService;
import com.nhry.utils.PrimaryKeyUtils;
import com.nhry.utils.SysContant;
import com.nhry.utils.date.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.task.TaskExecutor;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProductServiceImpl extends BaseService implements ProductService {
	private TMdMaraMapper tMdMaraMapper;
	private TMdMaraExMapper tMdMaraExMapper;
	private TBranchNotsellListMapper notsellListMapper;
	private BranchService branchSevice;
	private TMdBranchMapper branchMapper;
	private TSysMessageService messService;
	private TaskExecutor taskExecutor;
	private TSsmStockService stockService;

	public void settMdMaraMapper(TMdMaraMapper tMdMaraMapper) {
		this.tMdMaraMapper = tMdMaraMapper;
	}

	public void settMdMaraExMapper(TMdMaraExMapper tMdMaraExMapper) {
		this.tMdMaraExMapper = tMdMaraExMapper;
	}

	@Override
	public TMdMara selectProductByCode(String productCode) {
		// TODO Auto-generated method stub
		Map<String, String> attrs = new HashMap<String, String>();
		attrs.put("salesOrg", this.userSessionService.getCurrentUser()
				.getSalesOrg());
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
		TSysUser sysuser = this.userSessionService.getCurrentUser();
		if (record.getNotsellList() != null
				&& record.getNotsellList().size() > 0) {
			for (TBranchNotsellList nl : record.getNotsellList()) {
				nl.setListNo(PrimaryKeyUtils.generateUuidKey());
				nl.setSalesOrg(sysuser.getSalesOrg());
				nl.setCreateAt(new Date());
				nl.setMatnr(record.getMatnr());
				nl.setCreateBy(sysuser.getLoginName());
				nl.setCreateByTxt(sysuser.getDisplayName());
				notsellListMapper.addBranchNotSell(nl);
			}
		}
		if (record.getMaraEx() != null) {
			this.uptProductExByCode(record.getMatnr(), record.getMaraEx());
		}
		if (!StringUtils.isBlank(record.getStatus())) {
			// 状态不为空时，更新产品状态
			pubProductByCode(record.getMatnr(), record.getStatus(), false);
			//以线程池的方式发送系统
			taskExecutor.execute(new Thread() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					this.setName("sendProductsMessagesForUpt");
					messService.sendProductsMessages("产品更新了！", product,sysuser);
				}
			});
		}
		return 1;
	}

	@Override
	public PageInfo searchProducts(ProductQueryModel smodel) {
		// TODO Auto-generated method stub
		if (StringUtils.isEmpty(smodel.getPageNum())
				|| StringUtils.isEmpty(smodel.getPageSize())) {
			throw new ServiceException(MessageCode.LOGIC_ERROR,
					"pageNum和pageSize不能为空！");
		}
		smodel.setSalesOrg(this.userSessionService.getCurrentUser()
				.getSalesOrg());
		// smodel.setDealerNo(this.userSessionService.getCurrentUser().getDealerId());
		return tMdMaraMapper.searchProducts(smodel);
	}

	@Override
	public TMdMara selectProductAndExByCode(String matnr) {
		// TODO Auto-generated method stub
		TSysUser sysuser = this.userSessionService.getCurrentUser();
		if (StringUtils.isEmpty(sysuser.getSalesOrg())) {
			throw new ServiceException(MessageCode.LOGIC_ERROR,"当前用户的关联的组织信息不全,请先维护好当前用户信息!");
		}
		Map<String, String> attrs = new HashMap<String, String>(2);
		attrs.put("matnr", matnr);
		attrs.put("salesOrg", sysuser.getSalesOrg());
		TMdMara mara = tMdMaraMapper.selectProductAndExByCode(attrs);
		if (mara != null) {
			mara.setNotsellList(this.notsellListMapper.getNotSellListByMatnr(attrs));
		}
		return mara;
	}

	@Override
	public int pubProductByCode(String code, String status, boolean flag) {
		// TODO Auto-generated method stub
		TSysUser user = this.userSessionService.getCurrentUser();
		Map attrs = new HashMap();
		attrs.put("matnr", code);
		attrs.put("salesOrg", user.getSalesOrg());
		attrs.put("status", status);
		attrs.put("lastModified", new Date());
		TMdMara mara = tMdMaraMapper.selectProductAndExByCode(attrs);
		if (mara == null) {
			throw new ServiceException(MessageCode.LOGIC_ERROR,
					"该产品不存在，请检查输入的产品编号!");
		}
		tMdMaraMapper.pubProductByCode(attrs);
		// 发送通知
		if (flag) {
			taskExecutor.execute(new Thread() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					if ("Y".equals(status)) {
						this.setName("sendProductsMessagesForChangeSta1");
						messService.sendProductsMessages("新品发布通知！", mara,user);
						//调用库存接口
						BranchSalesOrgModel bModel = new BranchSalesOrgModel();
						bModel.setSalesOrg(user.getSalesOrg());
						List<TMdBranch> branchs = branchMapper.findBranchListByOrg(bModel);
						if(branchs != null && branchs.size() > 0){
							for(TMdBranch b : branchs){
								stockService.updateStock(b.getBranchNo(), code, new BigDecimal(0), user.getSalesOrg());
							}
						}
					} else if ("N".equals(status)) {
						this.setName("sendProductsMessagesForChangeSta2");
						messService.sendProductsMessages("产品下架通知！", mara,user);
					}
				}
			});
		}else{
			//以线程方式更新库存
			taskExecutor.execute(new Thread() {
				@Override
				public void run() {
					// TODO Auto-generated method stub
					super.run();
					if ("Y".equals(status)) {
						this.setName("updateStock");
						//调用库存接口
						List<TMdBranch> branchs = branchSevice.findBranchListByOrg();
						if(branchs != null && branchs.size() > 0){
							for(TMdBranch b : branchs){
								stockService.updateStock(b.getBranchNo(), code, null, user.getSalesOrg());
							}
						}
					} 
				}
			});
		}
		return 1;
	}

	@Override
	public List<TMdMara> selectProductAndExListByCode(String productCode) {
		TSysUser user = this.userSessionService.getCurrentUser();
		if (StringUtils.isEmpty(user.getSalesOrg())) {
			throw new ServiceException(MessageCode.LOGIC_ERROR,
					"当前用户的关联的组织信息不全,请先维护好当前用户信息!");
		}
		Map<String, String> attrs = new HashMap<String, String>();
		if (!StringUtils.isEmpty(productCode)) {
			attrs.put("productCode", "%" + productCode + "%");
		}
		attrs.put("salesOrg", user.getSalesOrg());
		return tMdMaraMapper.selectProductAndExListByCode(attrs);
	}

	@Override
	public int uptProductExByCode(String matnr, TMdMaraEx maraEx) {
		// TODO Auto-generated method stub
		Map<String, String> attrs = new HashMap<String, String>();
		TSysUser user = this.userSessionService.getCurrentUser();
		attrs.put("matnr", matnr);
		attrs.put("salesOrg", user.getSalesOrg());
		TMdMaraEx ex = this.tMdMaraExMapper.findProductExByCode(attrs);
		if (ex == null) {
			maraEx.setMatnr(matnr);
			maraEx.setSalesOrg(user.getSalesOrg());
			maraEx.setCreateAt(new Date());
			maraEx.setCreateBy(user.getLoginName());
			maraEx.setCreateByTxt(user.getDisplayName());
			this.tMdMaraExMapper.addMaraEx(maraEx);
		} else {
			maraEx.setMatnr(matnr);
			maraEx.setSalesOrg(user.getSalesOrg());
			maraEx.setLastModified(new Date());
			maraEx.setLastModifiedBy(user.getLoginName());
			maraEx.setLastModifiedByTxt(user.getDisplayName());
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
		if (StringUtils.isEmpty(pm.getBranchNo())) {
			throw new ServiceException(MessageCode.LOGIC_ERROR,
					"奶站编号(branchNo)不能为空!");
		}
		TMdBranch branch = branchSevice.selectBranchByNo(pm.getBranchNo());
		if (branch != null) {
			pm.setSalesOrg(branch.getSalesOrg());
			if (SysContant.getSystemConst("own_Branch").equals(
					branch.getBranchGroup())) {
				// 自营奶站
				return this.tMdMaraMapper.getCompMaras(pm);
			} else if (SysContant.getSystemConst("dealer_Branch").equals(
					branch.getBranchGroup())) {
				// 经销商奶站
				pm.setDealerNo(branch.getDealerNo());
				return this.tMdMaraMapper.getDealerMaras(pm);
			}
		}
		return null;
	}

	@Override
	public List<TMdMara> getProductByCodeOrName(String product) {
		TSysUser user = userSessionService.getCurrentUser();
		Map<String, String> map = new HashMap<String, String>();
		map.put("matnr", product);
		map.put("matnrTxt", product);
		map.put("salesOrg", user.getSalesOrg());
		return tMdMaraMapper.getProductByCodeOrNameAndSalesOrg(map);
	}

	public void setBranchSevice(BranchService branchSevice) {
		this.branchSevice = branchSevice;
	}

	@Override
	public List<TMdMara> findMarasBySalesCodeAndOrg(String id) {
		// TODO Auto-generated method stub
		Map<String, String> attrs = new HashMap<String, String>();
		attrs.put("salesOrg", this.userSessionService.getCurrentUser()
				.getSalesOrg());
		// attrs.put("dealerNo",this.userSessionService.getCurrentUser().getDealerId());
		if (!StringUtils.isEmpty(id) && !"-1".equals(id)) {
			attrs.put("id", id);
		}
		return this.tMdMaraMapper.findMarasBySalesCodeAndOrg(attrs);
	}

	@Override
	public Map<String, String> getMataBotTypes() {
		// TODO Auto-generated method stub
		Map<String, String> attrs = new HashMap<String, String>();
		List<BotType> list = this.tMdMaraExMapper
				.getMataBotTypes(this.userSessionService.getCurrentUser()
						.getSalesOrg());
		if (list != null) {
			for (BotType bt : list) {
				attrs.put(bt.getMatnr(), bt.getBotType());
			}
		}
		return attrs;
	}

	@Override
	public PageInfo listsBySalesOrg(ProductQueryModel pm) {
		TSysUser user = userSessionService.getCurrentUser();
		pm.setSalesOrg(user.getSalesOrg());
		return tMdMaraMapper.listsBySalesOrg(pm);
	}

	public void setMessService(TSysMessageService messService) {
		this.messService = messService;
	}

	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}

	public void setStockService(TSsmStockService stockService) {
		this.stockService = stockService;
	}

	public void setBranchMapper(TMdBranchMapper branchMapper) {
		this.branchMapper = branchMapper;
	}
}
