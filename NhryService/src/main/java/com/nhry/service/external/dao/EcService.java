package com.nhry.service.external.dao;

import com.nhry.data.basic.domain.TMdBranch;

public interface EcService {
    
	/**
	 * 奶站更新时，往电商推送
	 */
	public void pushBranch2EcForUpt(TMdBranch branch);
}
