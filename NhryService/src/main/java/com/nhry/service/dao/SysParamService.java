package com.nhry.service.dao;

import java.util.List;

import com.github.pagehelper.PageInfo;
import com.nhry.domain.NHSysParameter;
import com.nhry.pojo.query.SysParamQueryModel;

public interface SysParamService  {
	/**
	 * 根据参数编码删除系统参数
	 * @param paramCode 参数编码
	 * @return
	 */
	int deleteSysParamByCode(String paramCode);
	
	/**
	 * 增加系统参数
	 * @param record 系统参数对象
	 * @return
	 */
    int addSysParam(NHSysParameter record);
    
    /**
     * 根据参数编码查询系统参数对象
     * @param paramCode 系统参数对象
     * @return
     */
    NHSysParameter selectSysParamByCode(String paramCode);
    
    /**
     * 修改系统参数对象
     * @param record
     * @return
     */
    int uptSysParamByCode(NHSysParameter record);
    
    /**
     * 根据参数编码、参数名称、参数值，查询系统参数(带分页功能)
     * @param param
     * @return
     */
    public PageInfo findSysParam(SysParamQueryModel param);
}
