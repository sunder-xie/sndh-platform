package com.nhry.data.basic.dao;

import com.nhry.data.basic.domain.TVipCrmInfo;

import java.util.Map;

public interface TVipCrmMapper {
	
	/**
	 * 添加会员
	 * @param record
	 * @return
	 */
    int addVipCust(TVipCrmInfo record);
    
    /**
     * 根据订户编号查询订户信息(包含地址信息)
     * @param vipCustNo
     * @return
     */
    TVipCrmInfo findVipCustByNo(String vipCustNo);
    
    /**
     * 根据订户编号查询订户信息(包含地址信息)
     * @param vipCustNo
     * @return
     */
    TVipCrmInfo findVipCustByNoForUpt(String vipCustNo);
    
    /**
     * 修改订户信息
     * @param record
     * @return
     */
    int updateVipCustByNo(TVipCrmInfo record);
    
//    /**
//     * 根据电话查找奶站订户信息
//     * @param attrs
//     * @return
//     */
//    List<TVipCrmInfo> findStaCustByPhone(Map<String, String> attrs);
//
//    /**
//     * 根据电话查找公司订户信息
//     * @param attrs
//     * @return
//     */
//    List<TVipCrmInfo> findCompanyCustByPhone(Map<String, String> attrs);
    
    /**
     * 查询订户信息(只查询订户信息本身)
     * @param vipCustNo
     * @return
     */
    TVipCrmInfo findVipCustOnlyByNo(String vipCustNo);
    
    /**
     * 修改订户状态
     * @param record
     * @return
     */
    int uptCustStatus(TVipCrmInfo record);
    
//    /**
//     * 根据混合条件查询订户列表信息
//     * @param cust
//     * @return
//     */
//    public PageInfo findcustMixedTerms(CustQueryModel cust);
    
//    /**
//     * 根据电话号码获取在同一奶站下订户的个数
//     * @param attrs
//     * @return
//     */
//    public int getCustCountByPhone(Map<String, String> attrs);
    
    /**
     * 根据电话号码获取在订户编号
     * @param attrs
     * @return
     */
    public String getCustNoByPhone(Map<String, String> attrs);

}