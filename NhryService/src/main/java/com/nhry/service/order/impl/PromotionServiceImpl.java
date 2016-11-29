package com.nhry.service.order.impl;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

import com.nhry.model.order.PromotionOrderModel;
import com.nhry.model.order.PromotionPlanDetail;
import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.order.dao.TOrderDaliyPlanItemMapper;
import com.nhry.data.order.dao.TPromotionMapper;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.order.domain.TPlanOrderItem;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.data.order.domain.TPromotion;
import com.nhry.model.order.OrderSearchModel;
import com.nhry.service.BaseService;
import com.nhry.service.order.dao.PromotionService;

public class PromotionServiceImpl extends BaseService implements PromotionService
{
	private final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private TPromotionMapper tPromotionMapper;
	private TOrderDaliyPlanItemMapper tOrderDaliyPlanItemMapper;
	
	public void settPromotionMapper(TPromotionMapper tPromotionMapper)
	{
		this.tPromotionMapper = tPromotionMapper;
	}
	public void settOrderDaliyPlanItemMapper(TOrderDaliyPlanItemMapper tOrderDaliyPlanItemMapper)
	{
		this.tOrderDaliyPlanItemMapper = tOrderDaliyPlanItemMapper;
	}

	/* (non-Javadoc) 
	* @title: selectPromotionsrsByPage
	* @description: 查询销售组织下的所有促销规则
	* @param smodel
	* @return 
	* @see com.nhry.service.order.dao.PromotionService#selectPromotionsrsByPage(com.nhry.model.order.OrderSearchModel) 
	*/
	@Override
	public PageInfo selectPromotionsrsByPage(OrderSearchModel smodel)
	{
		smodel.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());
		smodel.setBranchNo(userSessionService.getCurrentUser().getBranchNo());
		return tPromotionMapper.selectPromotionsrsByPage(smodel);
	}

	/* (non-Javadoc) 
	* @title: getPromotionByMatnr
	* @description: 根据选择的商品号查询促销信息
	* @param code
	* @return 
	* @see com.nhry.service.order.dao.PromotionService#getPromotionByMatnr(java.lang.String) 
	*/
	@Override
	public List<TPromotion> getPromotionByMatnr(String code)
	{

		TPromotion record = new TPromotion();
		record.setOrgMatnr(code);
		record.setBranchNo(userSessionService.getCurrentUser().getBranchNo());
		record.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());
		record.setCreateBy(format.format(new Date()));
		return tPromotionMapper.selectPromotionByMatnr(record);
	}

	/* (non-Javadoc) 
	* @title: selectPromotionByPromNo
	* @description: 根据促销号查询唯一的促销信息
	* @param code
	* @return 
	* @see com.nhry.service.order.dao.PromotionService#selectPromotionByPromNo(java.lang.String) 
	*/
	@Override
	public TPromotion selectPromotionByPromNo(String code)
	{
		TPromotion record = new TPromotion();
		record.setPromNo(code);
		record.setBranchNo(userSessionService.getCurrentUser().getBranchNo());
		record.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());
		return tPromotionMapper.selectPromotionByPromNo(record);
	}
	
 	//行项目促销判断
	@Override
 	public void calculateEntryPromotion(TPlanOrderItem entry){
		
		if(StringUtils.isBlank(entry.getPromotion()))return;
		if(StringUtils.isNotBlank(entry.getGiftMatnr()))return;
 		TPromotion promotion = selectPromotionByPromNo(entry.getPromotion());
 		if(promotion==null)throw new ServiceException(MessageCode.LOGIC_ERROR,"没有此促销!");
 		if(promotion.getBuyStartTime().after(entry.getStartDispDate()) || promotion.getBuyStopTime().before(entry.getEndDispDate())){
 			throw new ServiceException(MessageCode.LOGIC_ERROR,entry.getMatnr()+"产品的促销,只能在"+promotion.getBuyStartTime().toString()+"到"+promotion.getBuyStopTime()+"之间订购!");
 		}
 		
 		//处理,满赠,满n个a产品赠m个b产品
 		if("Z008".equals(promotion.getPromType())){
 			int giftQty = entry.getBuyQty()/promotion.getOrgQty().intValue()*promotion.getGiftQty().intValue();
 			if(giftQty<=0)throw new ServiceException(MessageCode.LOGIC_ERROR,entry.getMatnr()+"产品没有达到满赠要求，请修改正品数量或不参加促销!");
 			entry.setGiftQty(giftQty);//赠送赠品的数量
 			entry.setGiftMatnr(promotion.getGiftMatnr());
 			entry.setGiftUnit(promotion.getGiftUnit());
 		}
 		
 		//处理，满减金额...
 		//TODO
 	}
	
 	//行项目促销判断
	@Override
 	public void calculateEntryPromotionForStop(TPlanOrderItem entry){
		
		if(StringUtils.isBlank(entry.getPromotion()))return;
 		TPromotion promotion = selectPromotionByPromNo(entry.getPromotion());
// 		if(promotion==null)throw new ServiceException(MessageCode.LOGIC_ERROR,"没有此促销!");
 		if(promotion==null || promotion.getBuyStartTime().after(entry.getStartDispDate()) || promotion.getBuyStopTime().before(entry.getEndDispDate())){
 			entry.setPromotion("");
 			entry.setGiftQty(0);//赠送赠品的数量
 			entry.setGiftMatnr("");
 			entry.setGiftUnit("");
 			return;
 		}
 		
 		//处理,满赠,满n个a产品赠m个b产品
 		if("Z008".equals(promotion.getPromType())){
 			int giftQty = entry.getBuyQty()/promotion.getOrgQty().intValue()*promotion.getGiftQty().intValue();
 			if(giftQty<=0)return;
 			entry.setGiftQty(giftQty);//赠送赠品的数量
 			entry.setGiftMatnr(promotion.getGiftMatnr());
 			entry.setGiftUnit(promotion.getGiftUnit());
 		}
 		
 		//处理，满减金额...
 		//TODO
 	}
	
	//根据行项目的赠品数量生成日计划,daliyPlans已经从晚到早排好顺序
	@Override
 	public void createDaliyPlanByPromotion(TPreOrder order, List<TPlanOrderItem> entriesList,List<TOrderDaliyPlanItem> daliyPlans){
		
		//预付款的要付款+装箱才生成日计划
		if("20".equals(order.getPaymentmethod()) && !"20".equals(order.getPaymentStat())){
			return;
		}
				
		//生成每日计划,当订户订单装箱状态为已装箱或无需装箱，则系统默认该订单可生成订户日订单
		if("20".equals(order.getMilkboxStat())){
			return;
		}
		
		Map<String,TPlanOrderItem> entryMap = new HashMap<String,TPlanOrderItem>();
//		//如果是复订的
//		entriesList.stream().filter((entry)->order.getStopDateStart()!=null)
//		  .forEach((e)->{
//			  calculateEntryPromotionForStop(e);
//			  entryMap.put(e.getItemNo(), e);
//		});
		
		//需要生成促销日计划的订单行项目
		entriesList.stream().filter((entry)->StringUtils.isNotBlank(entry.getPromotion()))
								  .forEach((e)->{
									  entryMap.put(e.getItemNo(), e);
//									  calculateEntryPromotion(e);
		});
		
		if(entryMap.size()<=0)return;
		
		int daliyEntryNo = Integer.parseInt(daliyPlans.get(0).getPlanItemNo()) + 1;//日计划行号
		
		for(String itemNo : entryMap.keySet()){
			for(TOrderDaliyPlanItem plan : daliyPlans){
				if(plan.getItemNo().equals(itemNo)){
					TPlanOrderItem orgEntry = entryMap.get(plan.getItemNo());
					
					int totalGift = orgEntry.getGiftQty();
					if(totalGift<=0)break;
					
					//复制日计划
					TOrderDaliyPlanItem giftPlan = new TOrderDaliyPlanItem();
					giftPlan.setOrderNo(plan.getOrderNo());//订单编号
					giftPlan.setOrderDate(plan.getOrderDate());//订单日期
					giftPlan.setPlanItemNo(String.valueOf(daliyEntryNo));//预订单计划行项
					giftPlan.setItemNo(plan.getItemNo());//预订单日计划行
					giftPlan.setDispDate(plan.getDispDate());//配送日期
					giftPlan.setReachTimeType(plan.getReachTimeType());//送达时段类型
					giftPlan.setMatnr(orgEntry.getGiftMatnr());//产品编号
					giftPlan.setUnit(orgEntry.getGiftUnit());//配送单位
					giftPlan.setPromotionFlag(orgEntry.getPromotion());//促销号
					
					//产品数量
					if(totalGift>=plan.getQty()){
						giftPlan.setQty(plan.getQty());
					}else{
						giftPlan.setQty(totalGift);
					}
					giftPlan.setGiftQty(giftPlan.getQty());//赠品数量
					giftPlan.setStatus("10");//状态
					giftPlan.setCreateAt(new Date());//创建时间
					giftPlan.setCreateBy(userSessionService.getCurrentUser().getLoginName());//创建人
					giftPlan.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());//创建人姓名
					
					tOrderDaliyPlanItemMapper.insert(giftPlan);
					
					//赠品数量减少
					orgEntry.setGiftQty(totalGift-giftPlan.getGiftQty());
					
					if(orgEntry.getGiftQty()<=0)break;//赠完为止
					
					daliyEntryNo++;
				}
			}
		}
		
		
	}

	@Override
	public List<TPromotion> selectAllPromotionByOrder(PromotionOrderModel model) {
		List<TPromotion> promotions = new ArrayList<TPromotion>();
		List<PromotionPlanDetail> details = model.getDetails();
		TPromotion param = new TPromotion();
		param.setBranchNo(userSessionService.getCurrentUser().getBranchNo());
		param.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());
		param.setBuyAmt(model.getInitAmt());
		param.setCreateBy(format.format(model.getOrderDate()));
		param.setBuyStartTime(model.getStartDate());
		param.setBuyStopTime(model.getEndDate());
		//年卡促销
		List<TPromotion> yearProms = this.selectPromotionByYear(param);
		if(yearProms!=null) {
			promotions.addAll(yearProms);
		}
		//整单促销
		List<TPromotion> orderProms = this.selectPromotionByOrder(param);
		if(orderProms!=null) {
			promotions.addAll(orderProms);
		}
		//单品促销
		for(PromotionPlanDetail detail : details) {
			param.setOrgMatnr(detail.getMatnr());
			param.setBuyAmt(detail.getAmt());
			List<TPromotion> matnrProms = this.selectPromotionByOneMatnr(param);
			if (matnrProms != null) {
				promotions.addAll(matnrProms);
			}
		}
		return promotions;
	}

	@Override
	public List<TPromotion> selectPromotionByOneMatnr(TPromotion promotion) {
		BigDecimal buyAmt = promotion.getBuyAmt();
		List<TPromotion> promotions = tPromotionMapper.selectPromationByOneMatnr(promotion);
		for(TPromotion p1 : promotions){
			p1.setDiscountAmt(buyAmt.divide(p1.getLowAmt(),BigDecimal.ROUND_UP).multiply(p1.getDiscountAmt()));
		}
		return promotions;
	}

	@Override
	public List<TPromotion> selectPromotionByOrder(TPromotion promotion) {
		BigDecimal buyAmt = promotion.getBuyAmt();
		List<TPromotion> promotions = tPromotionMapper.selectPromationByOrder(promotion);
		for(TPromotion p1 : promotions){
			p1.setDiscountAmt(buyAmt.divide(p1.getLowAmt(),BigDecimal.ROUND_UP).multiply(p1.getDiscountAmt()));
		}
		return promotions;
	}

	@Override
	public List<TPromotion> selectPromotionByYear(TPromotion promotion) {
		List<TPromotion> promotions = tPromotionMapper.selectPromotionByYear(promotion);
		return promotions;
	}


}
