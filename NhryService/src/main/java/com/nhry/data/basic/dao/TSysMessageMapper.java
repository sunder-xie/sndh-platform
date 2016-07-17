package com.nhry.data.basic.dao;

import com.nhry.data.basic.domain.TSysMessage;

public interface TSysMessageMapper {
	
	/**
	 * 根据消息编号删除消息
	 * @param messageNo
	 * @return
	 */
    int delTSysMessageByNo(String messageNo);
    
    /**
     * 添加消息
     * @param record
     * @return
     */
    int addTSysMessage(TSysMessage record);
    
    /**
     * 根据消息编号，查找消息
     * @param messageNo
     * @return
     */
    TSysMessage findTSysmessageByNo(String messageNo);
    
    /**
     * 修改消息
     * @param record
     * @return
     */
    int uptTSysMessage(TSysMessage record);
}