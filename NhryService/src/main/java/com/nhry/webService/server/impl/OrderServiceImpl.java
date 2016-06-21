package com.nhry.webservice.server.impl;

import java.util.List;

import javax.jws.WebService;

import com.nhry.data.config.domain.NHSysCodeItem;
import com.nhry.service.config.dao.DictionaryService;
import com.nhry.webservice.server.dao.OrderService;

@WebService(endpointInterface = "com.nhry.webservice.server.dao.OrderService",portName="orderServicePort",targetNamespace="http://service.nhry.order.com/")
public class OrderServiceImpl implements OrderService {
	private DictionaryService dicService;
	
	@Override
	public List<NHSysCodeItem> getTreeCodeItemsByTypeCode(String typecode) {
		// TODO Auto-generated method stub
		return this.dicService.getTreeCodeItemsByTypeCode(typecode);
	}
	
	public void setDicService(DictionaryService dicService) {
		this.dicService = dicService;
	}
}
