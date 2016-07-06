package com.nhry.data.basic.dao;

import java.util.List;

import com.nhry.data.basic.domain.TMdAddress;

public interface TMdAddressMapper {
	
    /**
     * 为订户添加详细地址信息
     * @param address
     * @return
     */
    public int addAddressForCust(TMdAddress address);
    
    /**
     * 根据id查找地址信息
     * @param addressId
     * @return
     */
    TMdAddress findAddressById(String addressId);
    
    /**
     * 修改地址
     * @param record
     * @return
     */
    int uptCustAddress(TMdAddress record);
    
    /**
     * 根据订户编号查询，订户对应的原始详细地址列表
     * @param custNp
     * @return
     */
    List<TMdAddress> findOriginAddressByCustNo(String custNo);
    
    /**
     * 根据订户编号查询，订户对应的翻译之后的详细地址列表
     * @param custNp
     * @return
     */
    List<TMdAddress> findCnAddressByCustNo(String custNo);
    
     /**
      * 根据地址id，查询地址详细信息
      * @param id
      * @return
      */
    TMdAddress findAddressDetailById(String id);
}