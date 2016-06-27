package com.nhry.data.basic.dao;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.domain.TMdAddress;
import com.nhry.data.basic.domain.TVipCustInfo;
import com.nhry.model.basic.CustQueryModel;

public interface TVipCustInfoMapper {
	
	/**
	 * 添加订户
	 * @param record
	 * @return
	 */
    int addVipCust(TVipCustInfo record);
    
    /**
     * 根据订户编号查询订户信息(包含地址信息)
     * @param vipCustNo
     * @return
     */
    TVipCustInfo findVipCustByNo(String vipCustNo);
    
    /**
     * 根据订户编号查询订户信息(包含地址信息)
     * @param vipCustNo
     * @return
     */
    TVipCustInfo findVipCustByNoForUpt(String vipCustNo);
    
    /**
     * 修改订户信息
     * @param record
     * @return
     */
    int updateVipCustByNo(TVipCustInfo record);
    
    /**
     * 根据电话查找奶站订户信息
     * @param attrs
     * @return
     */
    TVipCustInfo findStaCustByPhone(Map<String,String> attrs);
    
    /**
     * 根据电话查找公司订户信息
     * @param attrs
     * @return
     */
    List<TVipCustInfo> findCompanyCustByPhone(Map<String,String> attrs);
    
    /**
     * 查询订户信息(只查询订户信息本身)
     * @param vipCustNo
     * @return
     */
    TVipCustInfo findVipCustOnlyByNo(String vipCustNo);
    
    /**
     * 修改订户状态
     * @param record
     * @return
     */
    int uptCustStatus(TVipCustInfo record);
    
    /**
     * 根据混合条件查询订户列表信息
     * @param cust
     * @return
     */
    public PageInfo findcustMixedTerms(CustQueryModel cust);
}