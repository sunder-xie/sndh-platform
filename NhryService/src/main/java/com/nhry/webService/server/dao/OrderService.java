package com.nhry.webservice.server.dao;

import java.util.List;

import javax.jws.WebService;

import com.nhry.data.config.domain.NHSysCodeItem;

@WebService
public interface OrderService {
	public List<NHSysCodeItem> getTreeCodeItemsByTypeCode(String typecode);
}
