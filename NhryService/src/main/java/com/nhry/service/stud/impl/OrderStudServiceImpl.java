package com.nhry.service.stud.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.stud.dao.TMdClassMapper;
import com.nhry.data.stud.dao.TMdMaraStudMapper;
import com.nhry.data.stud.dao.TMdSchoolClassMapper;
import com.nhry.data.stud.dao.TMdSchoolMapper;
import com.nhry.data.stud.dao.TMstOrderStudItemMapper;
import com.nhry.data.stud.dao.TMstOrderStudLossMapper;
import com.nhry.data.stud.dao.TMstOrderStudMapper;
import com.nhry.data.stud.domain.TMdClass;
import com.nhry.data.stud.domain.TMdMaraStud;
import com.nhry.data.stud.domain.TMdSchool;
import com.nhry.data.stud.domain.TMstOrderStud;
import com.nhry.data.stud.domain.TMstOrderStudItem;
import com.nhry.data.stud.domain.TMstOrderStudLoss;
import com.nhry.model.stud.OrderBatchBuildModel;
import com.nhry.model.stud.OrderStudQueryModel;
import com.nhry.model.stud.SchoolQueryModel;
import com.nhry.service.stud.dao.OrderStudService;


/**
 * @author zhaoxijun
 * @date 2017年4月11日
 */
public class OrderStudServiceImpl implements OrderStudService {

	@Autowired
	private TMstOrderStudMapper mstOrderStudMapper;
	
	@Autowired
	private UserSessionService userSessionService;
	
	@Autowired
	private TMstOrderStudItemMapper orderStudItemMapper;
	
	@Autowired
	private TMdClassMapper classMapper;
	
	@Autowired
	private TMdSchoolClassMapper schoolClassMapper;
	
	@Autowired
	private TMdMaraStudMapper maraStudMapper;
	
	@Autowired
	private TMstOrderStudLossMapper orderStudLossMapper;
	
	@Autowired
	private TMdSchoolMapper schoolMapper;
	
    public static String getCode(){
		DateFormat format = new SimpleDateFormat("yyMMddHHmmssSSS");
        int num = new Random().nextInt(89999)+10000;
        return format.format(new Date())+num;
    }
	
	@Override
	public int createOrder(final TMstOrderStud mstOrderStud) throws Exception {
		
		if(null == mstOrderStud){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "参数必传");
		}
		if(StringUtils.isBlank(mstOrderStud.getOrderDateStr())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "订单日期必选");
		}
		if(StringUtils.isBlank(mstOrderStud.getSchoolCode())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "学校必选");
		}
		if(CollectionUtils.isNotEmpty(mstOrderStud.getList10()) && StringUtils.isBlank(mstOrderStud.getMatnr())){
			boolean flag = true;
			for(TMstOrderStudItem item : mstOrderStud.getList10()){
				if(null != item.getQty() && item.getQty() > 0){
					flag = false;
					break;
				}
			}
			if(!flag){
				throw new ServiceException(MessageCode.LOGIC_ERROR, "学校奶未选择牛奶品种");
			}
		}
		Date orderDate = new SimpleDateFormat("yyyy-MM-dd").parse(mstOrderStud.getOrderDateStr());
		Date date = new Date();
		TSysUser user = this.userSessionService.getCurrentUser();
		if(StringUtils.isNoneBlank(mstOrderStud.getOrderId())){
			TMstOrderStud updOrder = new TMstOrderStud();
			updOrder.setOrderStatus("20");
			updOrder.setOrderId(mstOrderStud.getOrderId());
			updOrder.setLastModified(date);
			updOrder.setLastModifiedBy(user.getLoginName());
			updOrder.setLastModifiedByTxt(user.getDisplayName());
			mstOrderStudMapper.updateByOrder(updOrder);//将订单失效
		}
		String orderId = getCode();
		
		
		//创建主订单
		mstOrderStud.setOrderId(orderId);
		mstOrderStud.setOrderDate(orderDate);
		mstOrderStud.setCreateAt(date);
		mstOrderStud.setCreateBy(user.getLoginName());
		mstOrderStud.setCreateByTxt(user.getDisplayName());
		mstOrderStud.setLastModified(date);
		mstOrderStud.setLastModifiedBy(user.getLoginName());
		mstOrderStud.setLastModifiedByTxt(user.getDisplayName());
		mstOrderStud.setSalesOrg(user.getSalesOrg());
		mstOrderStud.setOrderStatus("10");
		mstOrderStudMapper.insertOrder(mstOrderStud);
		
		List<TMstOrderStudItem> list10 = mstOrderStud.getList10();//学生奶
	    if(CollectionUtils.isNotEmpty(list10)){
	    	for(TMstOrderStudItem item : list10){
	    		if(StringUtils.isBlank(item.getClassCode())){
	    			continue;
	    		}
	    		if(item.getQty() == null || item.getQty() < 0){
	    			item.setQty(0);
	    		}
	    		item.setSchoolCode(mstOrderStud.getSchoolCode());
	    		item.setOrderId(orderId);
	    		item.setMid(UUID.randomUUID().toString().replace("-", ""));
	    		item.setMatnr(mstOrderStud.getMatnr());
	    		item.setOrderDate(orderDate);
	    		item.setOrderType("10");
	    		item.setCreateAt(date);
	    		item.setCreateBy(user.getLoginName());
	    		item.setCreateByTxt(user.getDisplayName());
	    		item.setLastModified(date);
	    		item.setLastModifiedBy(user.getLoginName());
	    		item.setLastModifiedByTxt(user.getDisplayName());
	    		item.setSalesOrg(user.getSalesOrg());
	    		orderStudItemMapper.insertSdutOrderItem(item);
	    	}
	    }
	    
	    List<TMstOrderStudItem> list20 = mstOrderStud.getList20();//老师奶
	    if(CollectionUtils.isNotEmpty(list20)){
	    	for(TMstOrderStudItem item : list20){
	    		if(StringUtils.isBlank(item.getMatnr())){
	    			continue;
	    		}
	    		if(item.getQty() == null || item.getQty() < 0){
	    			item.setQty(0);
	    		}
	    		item.setSchoolCode(mstOrderStud.getSchoolCode());
	    		item.setOrderId(orderId);
	    		item.setMid(UUID.randomUUID().toString().replace("-", ""));
	    		item.setOrderDate(orderDate);
	    		item.setOrderType("20");
	    		item.setCreateAt(date);
	    		item.setCreateBy(user.getLoginName());
	    		item.setCreateByTxt(user.getDisplayName());
	    		item.setLastModified(date);
	    		item.setLastModifiedBy(user.getLoginName());
	    		item.setLastModifiedByTxt(user.getDisplayName());
	    		item.setSalesOrg(user.getSalesOrg());
	    		orderStudItemMapper.insertSdutOrderItem(item);
	    	}
	    }
	    
	    List<TMstOrderStudLoss> list30 = mstOrderStud.getList30();//损耗
	    if(CollectionUtils.isNotEmpty(list30)){
	    	for(TMstOrderStudLoss item : list30){
	    		if(StringUtils.isBlank(item.getMatnr())){
	    			continue;
	    		}
	    		if(item.getQty() == null || item.getQty() < 0){
	    			item.setQty(0);
	    		}
	    		item.setOrderId(orderId);
	    		item.setMid(UUID.randomUUID().toString().replace("-", ""));
	    		item.setCreateAt(date);
	    		item.setCreateBy(user.getLoginName());
	    		item.setCreateByTxt(user.getDisplayName());
	    		item.setLastModified(date);
	    		item.setLastModifiedBy(user.getLoginName());
	    		item.setLastModifiedByTxt(user.getDisplayName());
	    		item.setSalesOrg(user.getSalesOrg());
	    		orderStudLossMapper.insertOrderStudLoss(item);
	    	}
	    }
		return 1;
	}

	@Override
	public TMstOrderStud selectByOrderId(String orderId) {
		return mstOrderStudMapper.selectByOrderId(orderId);
	}

	@Override
	public int updateByOrder(TMstOrderStud mstOrderStud) {
		if(null == mstOrderStud || StringUtils.isBlank(mstOrderStud.getOrderId())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "订单号必传");
		}
		Date date = new Date();
		mstOrderStud.setLastModified(date);
		mstOrderStud.setLastModifiedBy(this.userSessionService.getCurrentUser().getLoginName());
		mstOrderStud.setLastModifiedByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		return mstOrderStudMapper.updateByOrder(mstOrderStud);
	}

	@Override
	public PageInfo<TMstOrderStud> findOrderPage(OrderStudQueryModel queryModel) {
		if(null == queryModel || StringUtils.isBlank(queryModel.getPageNum())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "当前页数必填");
		}
		if(StringUtils.isBlank(queryModel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "每页显示条数必填");
		}
		return mstOrderStudMapper.findOrderPage(queryModel);
	}

	@Override
	public Map<String, Object> findOrderInfoBySchoolCodeAndDate(TMstOrderStud mstOrderStud) {
		if(null == mstOrderStud || StringUtils.isBlank(mstOrderStud.getSchoolCode())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "学校代码必传，请选择学校");
		}
		if(StringUtils.isBlank(mstOrderStud.getOrderDateStr())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "订单日期必传，请选择订单日期");
		}
		if(StringUtils.isBlank(this.userSessionService.getCurrentUser().getSalesOrg())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "当前用户未归属销售组织");
		}
		TSysUser user = this.userSessionService.getCurrentUser();
		mstOrderStud.setSalesOrg(user.getSalesOrg());
		Map<String, Object> resultMap = new HashMap<>();
		TMstOrderStud orderStud = this.mstOrderStudMapper.selectOrderBySchoolCodeAndDateWithOrderStatus10(mstOrderStud);
		
		if(null == orderStud){
			//学校不存在该日期的订单，则初始化数据
			resultMap.put("orderStud", null);
			
			//学生奶
			List<TMstOrderStudItem> list10 = new ArrayList<TMstOrderStudItem>();
			List<TMdClass> classList = classMapper.findClassListBySalesOrg10(user.getSalesOrg());
			if(CollectionUtils.isNotEmpty(classList)){
				TMstOrderStudItem item10 = null;
				for(TMdClass mdClass : classList){
					item10 = new TMstOrderStudItem();
					item10.setClassCode(mdClass.getClassCode());
					item10.setClassName(mdClass.getClassName());
					item10.setQty(0);
					list10.add(item10);
				}
			}
			resultMap.put("list10", list10);
			
			//老师奶
			List<TMstOrderStudItem> list20 = new ArrayList<TMstOrderStudItem>();
			List<TMdMaraStud> maraList = maraStudMapper.findAllListBySalesOrg(user.getSalesOrg());
			if(CollectionUtils.isNotEmpty(maraList)){
				TMstOrderStudItem item20 = null;
				for(TMdMaraStud mara : maraList){
					item20 = new TMstOrderStudItem();
					item20.setQty(0);
					item20.setMatnr(mara.getMatnr());
					item20.setMatnrTxt(mara.getMatnrTxt());
					item20.setZbotCode(mara.getZbotCode());
					item20.setZbotCodeName(mara.getZbotCodeName());
					list20.add(item20);
				}
			}
			resultMap.put("list20", list20);
			
			//损耗
			List<TMstOrderStudLoss> list30 = new ArrayList<TMstOrderStudLoss>();
			if(CollectionUtils.isNotEmpty(maraList)){
				TMstOrderStudLoss item30 = null;
				for(TMdMaraStud mara : maraList){
					item30 = new TMstOrderStudLoss();
					item30.setQty(0);
					item30.setMatnr(mara.getMatnr());
					item30.setMatnrTxt(mara.getMatnrTxt());
					item30.setZbotCode(mara.getZbotCode());
					item30.setZbotCodeName(mara.getZbotCodeName());
					list30.add(item30);
				}
			}
			resultMap.put("list30", list30);
		}
		else{
			//存在订单，返回信息
			resultMap.put("orderStud", orderStud);
			
			//学生奶
			Map<String, Object> selectMap = new HashMap<>();
			selectMap.put("orderId", orderStud.getOrderId());
			selectMap.put("orderType", "10");
			selectMap.put("salesOrg", user.getSalesOrg());
			List<TMstOrderStudItem> list10 = orderStudItemMapper.findOrderItemByMap(selectMap);
			if(CollectionUtils.isEmpty(list10)){
				list10 = new ArrayList<TMstOrderStudItem>();
				List<TMdClass> classList = classMapper.findClassListBySalesOrg10(user.getSalesOrg());
				if(CollectionUtils.isNotEmpty(classList)){
					TMstOrderStudItem item10 = null;
					for(TMdClass mdClass : classList){
						item10 = new TMstOrderStudItem();
						item10.setClassCode(mdClass.getClassCode());
						item10.setClassName(mdClass.getClassName());
						item10.setQty(0);
						list10.add(item10);
					}
				}
			}
			else{
				orderStud.setMatnr(list10.get(0).getMatnr());
				List<String> notInList = new ArrayList<String>();
				for(TMstOrderStudItem item : list10){
					notInList.add(item.getClassCode());
				}
				Map<String, Object> selectClassMap = new HashMap<>();
				selectClassMap.put("salesOrg", user.getSalesOrg());
				selectClassMap.put("notInList", notInList);
				List<TMdClass> classList = classMapper.findClassListBySalesOrgNotIn(selectClassMap);
				if(CollectionUtils.isNotEmpty(classList)){
					TMstOrderStudItem item10 = null;
					for(TMdClass mdClass : classList){
						item10 = new TMstOrderStudItem();
						item10.setClassCode(mdClass.getClassCode());
						item10.setClassName(mdClass.getClassName());
						item10.setQty(0);
						list10.add(item10);
					}
				}
			}
			resultMap.put("list10", list10);
			
			//老师奶
			selectMap.put("orderType", "20");
			List<TMstOrderStudItem> list20 = orderStudItemMapper.findOrderItemByMap(selectMap);
			if(CollectionUtils.isEmpty(list20)){
				list20 = new ArrayList<TMstOrderStudItem>();
				List<TMdMaraStud> maraList = maraStudMapper.findAllListBySalesOrg(user.getSalesOrg());
				if(CollectionUtils.isNotEmpty(maraList)){
					TMstOrderStudItem item20 = null;
					for(TMdMaraStud mara : maraList){
						item20 = new TMstOrderStudItem();
						item20.setQty(0);
						item20.setMatnr(mara.getMatnr());
						item20.setMatnrTxt(mara.getMatnrTxt());
						item20.setZbotCode(mara.getZbotCode());
						item20.setZbotCodeName(mara.getZbotCodeName());
						list20.add(item20);
					}
				}
			}
			else{
				List<String> notInList = new ArrayList<String>();
				for(TMstOrderStudItem item : list20){
					notInList.add(item.getMatnr());
				}
				Map<String, Object> selectMaraMap = new HashMap<>();
				selectMaraMap.put("salesOrg", user.getSalesOrg());
				selectMaraMap.put("notInList", notInList);
				List<TMdMaraStud> maraList = maraStudMapper.findAllListBySalesOrgNotIn(selectMaraMap);
				if(CollectionUtils.isNotEmpty(maraList)){
					TMstOrderStudItem item20 = null;
					for(TMdMaraStud mara : maraList){
						item20 = new TMstOrderStudItem();
						item20.setQty(0);
						item20.setMatnr(mara.getMatnr());
						item20.setMatnrTxt(mara.getMatnrTxt());
						item20.setZbotCode(mara.getZbotCode());
						item20.setZbotCodeName(mara.getZbotCodeName());
						list20.add(item20);
					}
				}
			}
			resultMap.put("list20", list20);
			
			//损耗
			List<TMstOrderStudLoss> list30 = orderStudLossMapper.findLossByOrderId(selectMap);
			if(CollectionUtils.isEmpty(list30)){
				list30 = new ArrayList<TMstOrderStudLoss>();
				List<TMdMaraStud> maraList = maraStudMapper.findAllListBySalesOrg(user.getSalesOrg());
				if(CollectionUtils.isNotEmpty(maraList)){
					TMstOrderStudLoss item30 = null;
					for(TMdMaraStud mara : maraList){
						item30 = new TMstOrderStudLoss();
						item30.setQty(0);
						item30.setMatnr(mara.getMatnr());
						item30.setMatnrTxt(mara.getMatnrTxt());
						item30.setZbotCode(mara.getZbotCode());
						item30.setZbotCodeName(mara.getZbotCodeName());
						list30.add(item30);
					}
				}
			}
			else{
				List<String> notInList = new ArrayList<String>();
				for(TMstOrderStudLoss item : list30){
					notInList.add(item.getMatnr());
				}
				Map<String, Object> selectMaraMap = new HashMap<>();
				selectMaraMap.put("salesOrg", user.getSalesOrg());
				selectMaraMap.put("notInList", notInList);
				List<TMdMaraStud> maraList = maraStudMapper.findAllListBySalesOrgNotIn(selectMaraMap);
				if(CollectionUtils.isNotEmpty(maraList)){
					TMstOrderStudLoss item30 = null;
					for(TMdMaraStud mara : maraList){
						item30 = new TMstOrderStudLoss();
						item30.setQty(0);
						item30.setMatnr(mara.getMatnr());
						item30.setMatnrTxt(mara.getMatnrTxt());
						item30.setZbotCode(mara.getZbotCode());
						item30.setZbotCodeName(mara.getZbotCodeName());
						list30.add(item30);
					}
				}
			}
			resultMap.put("list30", list30);
		}
		return resultMap;
	}

	@Override
	public Map<String, Object> buildBatchInfo(OrderBatchBuildModel orderBatchBuildModel) throws Exception {
		TSysUser user = this.userSessionService.getCurrentUser();
		if(null == orderBatchBuildModel || StringUtils.isBlank(orderBatchBuildModel.getOrderStartDateStr())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "取数日期必传");
		}
		if(StringUtils.isBlank(orderBatchBuildModel.getOrderEndDateStr())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "目标日期必传");
		}
		if(StringUtils.isBlank(orderBatchBuildModel.getWeek())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "指定套餐必传");
		}
		if(orderBatchBuildModel.getOrderStartDateStr().compareTo(orderBatchBuildModel.getOrderEndDateStr()) > 0){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "取数日期不能大于目标日期");
		}
		if(StringUtils.isBlank(user.getSalesOrg())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "当前用户未归属销售组织");
		}
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		List<TMdSchool> baseSchoolList = this.schoolMapper.findSchoolList(new SchoolQueryModel(null, null, "10", user.getSalesOrg()));
		if(CollectionUtils.isEmpty(baseSchoolList)){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "未查询到学校");
		}
		
		List<TMdSchool> schoolList = new ArrayList<TMdSchool>();
		if(StringUtils.isNotBlank(orderBatchBuildModel.getUncloudSchoolCodes())){
			orderBatchBuildModel.setUncloudSchoolCodes(orderBatchBuildModel.getUncloudSchoolCodes().replace("，", "").replace(" ", "").trim());
			String[] schoolCodes = orderBatchBuildModel.getUncloudSchoolCodes().split(",");
			for(TMdSchool school : baseSchoolList){
				for(String schoolCode : schoolCodes){
					if(schoolCode.equals(school.getSchoolCode())){
						break;
					}
				}
				schoolList.add(school);
			}
		}
		else{
			schoolList = baseSchoolList;
		}
		
		if(CollectionUtils.isEmpty(schoolList)){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "过滤后没有学校");
		}
		
		/**
		 * 生产准备数据
		 */
		List<String> orderDateList = new ArrayList<String>();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String orderStartDateStr = orderBatchBuildModel.getOrderStartDateStr();
		String orderEndDateStr = orderBatchBuildModel.getOrderEndDateStr();
		Date orderStartDate = dateFormat.parse(orderStartDateStr);
		Date orderEndDate = dateFormat.parse(orderEndDateStr);
		String week = orderBatchBuildModel.getWeek();
		Calendar c = Calendar.getInstance();
		c.setTime(orderStartDate);
		int iweek = Integer.parseInt(week);
		while(c.getTime().getTime() <= orderEndDate.getTime()){
			if(iweek == c.get(Calendar.DAY_OF_WEEK)){
				orderDateList.add(dateFormat.format(c.getTime()));
			}
			c.add(Calendar.DAY_OF_YEAR, 1);
		}
		if(CollectionUtils.isEmpty(orderDateList)){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "指定日期期间不存在该套餐");
		}
		
		
		
		resultMap.put("orderDateList", orderDateList);
		resultMap.put("schoolList", schoolList);
		return resultMap;
	}

	@Override
	public int createOrderWithBatch(TMstOrderStud mstOrderStud) {
		// TODO Auto-generated method stub
		return 0;
	}

}
