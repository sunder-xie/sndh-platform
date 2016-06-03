package com.nhry.service.config.dao;

import java.util.List;

import com.nhry.data.config.domain.NHSysCodeItem;

public interface DictionaryService {
   public List<NHSysCodeItem> getCodeItemsByTypeCode(String typecode);
}
