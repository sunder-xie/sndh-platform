package com.nhry.service.basic.impl;

import com.nhry.data.auth.dao.TSysUserMapper;
import com.nhry.data.basic.dao.TBranchNotsellListMapper;
import com.nhry.data.basic.dao.TSysMessageMapper;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.data.basic.domain.TSysMessage;
import com.nhry.service.basic.dao.TSysMessageService;

public class TSysMessageServiceImpl implements TSysMessageService {
    private TSysMessageMapper messageMapper;
    private TSysUserMapper userMapper;
    private TBranchNotsellListMapper notsellListMapper;
    
	@Override
	public int delTSysMessageByNo(String messageNo) {
		// TODO Auto-generated method stub
		return this.messageMapper.delTSysMessageByNo(messageNo);
	}

	@Override
	public int addTSysMessage(TSysMessage record) {
		// TODO Auto-generated method stub
		return this.messageMapper.addTSysMessage(record);
	}

	@Override
	public TSysMessage findTSysmessageByNo(String messageNo) {
		// TODO Auto-generated method stub
		return this.messageMapper.findTSysmessageByNo(messageNo);
	}

	@Override
	public int uptTSysMessage(TSysMessage record) {
		// TODO Auto-generated method stub
		return this.messageMapper.uptTSysMessage(record);
	}

	public void setMessageMapper(TSysMessageMapper messageMapper) {
		this.messageMapper = messageMapper;
	}

	public void setUserMapper(TSysUserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public boolean sendProductsMessages(TMdMara mara) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setNotsellListMapper(TBranchNotsellListMapper notsellListMapper) {
		this.notsellListMapper = notsellListMapper;
	}
}
