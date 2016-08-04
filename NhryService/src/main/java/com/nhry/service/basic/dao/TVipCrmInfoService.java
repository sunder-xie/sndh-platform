package com.nhry.service.basic.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdAddress;
import com.nhry.data.basic.domain.TVipAcct;
import com.nhry.data.basic.domain.TVipCrmAddress;
import com.nhry.data.basic.domain.TVipCrmInfo;
import com.nhry.model.basic.CustQueryModel;
import com.nhry.service.basic.pojo.Addresses;
import com.nhry.utils.date.Date;

import java.util.List;
import java.util.Map;

public interface TVipCrmInfoService {
	/**
	 * 添加订户
	 * @param record
	 * @return
	 */
	void addVipCrm(TVipCrmInfo record);
    
    /**
     * 根据订户编号查询订户信息
     * @param vipCrmNo
     * @return
     */
    TVipCrmInfo findVipCrmByNo(String vipCrmNo);
    
    /**
     * 修改订户信息
     * @param record
     * @return
     */
    int updateVipCrmByNo(TVipCrmInfo record);

	String getCrmNoByPhone(Map<String, String> attrs);
	
	/**
	 * 修改crm会员地址
	 * @param address
	 * @return
	 */
	int updateVipCrmAddress(TVipCrmAddress address);
}
