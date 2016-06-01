package com.nhry.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.nhry.data.dao.NHSysCodeItemMapper;
import com.nhry.domain.NHSysCodeItem;
import com.nhry.exception.MessageCode;
import com.nhry.exception.ServiceException;
import com.nhry.service.BaseService;
import com.nhry.service.dao.DictionaryService;

public class DictionaryServiceImpl extends BaseService implements DictionaryService {
    private NHSysCodeItemMapper codeItemMapper;
	@Override
	public List<NHSysCodeItem> getCodeItemsByTypeCode(String typecode) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(typecode)){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "类型编码不能为空!");
		}
		return codeItemMapper.getCodeItemsByTypeCode(typecode);
	}
	public void setCodeItemMapper(NHSysCodeItemMapper codeItemMapper) {
		this.codeItemMapper = codeItemMapper;
	}

}
