package com.nhry.data.milk.impl;

import java.util.Date;
import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.milk.dao.TDispOrderMapper;
import com.nhry.data.milk.domain.TDispOrder;
import com.nhry.data.milk.domain.TDispOrderKey;
import com.nhry.model.milk.RouteOrderSearchModel;

public class TDispOrderMapperImpl implements TDispOrderMapper
{
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	public void setSqlSessionTemplate(DynamicSqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	private UserSessionService userSessionService;
	public void setUserSessionService(UserSessionService userSessionService) {
		this.userSessionService = userSessionService;
	}
	
	@Override
	public PageInfo selectMilkboxsByPage(RouteOrderSearchModel smodel)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectListByPages("searchRoutePlansByPage",smodel, Integer.parseInt(smodel.getPageNum()), Integer.parseInt(smodel.getPageSize()));
	}
	
	@Override
	public TDispOrder selectByPrimaryKey(TDispOrderKey key)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne("selectDispOrderByOrderNo", key);
	}
	
	@Override
	public int deleteByPrimaryKey(TDispOrderKey key)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int insert(TDispOrder record)
	{
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert("addNewDispOrder", record);
	}

	@Override
	public int updateByPrimaryKeySelective(TDispOrder record)
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int updateDispOrderStatus(String orderCode,String status)
	{
		TDispOrder record = new TDispOrder();
		record.setOrderNo(orderCode);
		record.setStatus(status);
		record.setLastModified(new Date());
		record.setLastModifiedBy(userSessionService.getCurrentUser().getLoginName());
		record.setLastModifiedByTxt(userSessionService.getCurrentUser().getDisplayName());
		return sqlSessionTemplate.update("updateDispOrder", record);
	}

}
