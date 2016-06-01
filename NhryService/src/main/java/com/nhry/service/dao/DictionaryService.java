package com.nhry.service.dao;

import java.util.List;

import com.nhry.domain.NHSysCodeItem;

public interface DictionaryService {
   public List<NHSysCodeItem> getCodeItemsByTypeCode(String typecode);
}
