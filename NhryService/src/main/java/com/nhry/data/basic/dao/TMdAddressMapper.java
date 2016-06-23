package com.nhry.data.basic.dao;

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
}