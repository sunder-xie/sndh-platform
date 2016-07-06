package com.nhry.data.basic.dao;

import com.nhry.data.basic.domain.TBranchNotsellList;

public interface TBranchNotsellListMapper {
	/**
	 * 根据清单号删除不可销售的清单
	 * @param listNo
	 * @return
	 */
    int delBranchNotsellByNo(String listNo);
    
    /**
     * 添加销售清单
     * @param record
     * @return
     */
    int addBranchNotSell(TBranchNotsellList record);
    
    /**
     * 根据产品编号删除相应的不可销售的清单
     * @param matnr
     * @return
     */
    int delBranchNotsellByMatnr(String matnr);
}