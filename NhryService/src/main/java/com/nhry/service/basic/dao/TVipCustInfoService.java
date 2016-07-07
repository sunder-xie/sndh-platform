package com.nhry.service.basic.dao;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdAddress;
import com.nhry.data.basic.domain.TVipAcct;
import com.nhry.data.basic.domain.TVipCustInfo;
import com.nhry.model.basic.CustQueryModel;

public interface TVipCustInfoService {
	/**
	 * 添加订户
	 * @param record
	 * @return
	 */
    int addVipCust(TVipCustInfo record);
    
    /**
     * 根据订户编号查询订户信息
     * @param vipCustNo
     * @return
     */
    TVipCustInfo findVipCustByNo(String vipCustNo);
    
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
     * 根据订户编号查询订户信息
     * @param vipCustNo
     * @return
     */
    TVipCustInfo findVipCustByNoForUpt(String vipCustNo);
    
    /**
     * 停订、退订接口
     * @param vipCustNo
     * @param status 10-在订 20-暂停 30-停订 40-退订
     * @return
     */
    public int discontinue(String vipCustNo,String status);
    
    /**
     * 根据奶站编号、订户状态、时间等条件查询订户列表信息
     * @param cust
     * @return
     */
    public PageInfo findcustMixedTerms(CustQueryModel cust);
    
    /**
     * 为订户添加详细地址信息
     * @param vipCustNo
     * @return
     */
    String addAddressForCust(TMdAddress address);
    
    /**
     * 修改订户详细地址
     * @param record
     * @return
     */
    public int uptCustAddress(TMdAddress record);
    
    /**
	 * 添加订户订奶资金账户
	 * @param record
	 * @return
	 */
    int addVipAcct(TVipAcct record);
    
    /**
     * 根据会员编号查询资金账号
     * @param custNo
     * @return
     */
    TVipAcct findVipAcctByCustNo(String custNo);
    
    /**
     * 修改资金账号
     * @param record
     * @return
     */
    int uptVipAcct(TVipAcct record);
    
    /**
     * 根据id查询地址详细信息
     * @param addressId
     * @return
     */
    TMdAddress findAddressById(String addressId);
    
    /**
     * 根据状态和地址编号，更新地址信息
     * @param addressId
     * @return
     */
    int uptAddressById(String status,String addressId);
    
    /**
     * 根据地址id查询地址详细信息
     * @param id
     * @return
     */
    public TMdAddress findAddressDetailById(String id);
    
    /**
     * 根据订户编号，查询地址详细列表
     * @param custNo
     * @return
     */
    List<TMdAddress> findCnAddressByCustNo(String custNo);
}
