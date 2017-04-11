package com.nhry.service.stud.impl;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.stud.dao.TMstOrderStudMapper;
import com.nhry.data.stud.domain.TMstOrderStud;
import com.nhry.model.stud.OrderStudQueryModel;
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
	
	@Override
	public int createOrder(TMstOrderStud mstOrderStud) {
		if(null == mstOrderStud){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "参数必传");
		}
		Date date = new Date();
		mstOrderStud.setCreateAt(date);
		mstOrderStud.setCreateBy(this.userSessionService.getCurrentUser().getLoginName());
		mstOrderStud.setCreateByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		mstOrderStud.setLastModified(date);
		mstOrderStud.setLastModifiedBy(this.userSessionService.getCurrentUser().getLoginName());
		mstOrderStud.setLastModifiedByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		mstOrderStud.setSalesOrg(this.userSessionService.getCurrentUser().getSalesOrg());
		return mstOrderStudMapper.insertOrder(mstOrderStud);
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

}
