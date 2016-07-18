package com.nhry.service.basic.dao;

import com.nhry.data.basic.domain.TMdMara;
import com.nhry.data.basic.domain.TSysMessage;

public interface TSysMessageService {
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
    
    /**
     * 产品发布通知(给奶站内勤发送通知)
     * @param mara
     * @return
     */
    public boolean sendProductsMessages(TMdMara mara);
}
