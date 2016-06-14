package com.nhry.service.config.dao;

import java.util.List;

import com.nhry.data.config.domain.NHSysCodeItem;
import com.nhry.data.config.domain.NHSysCodeType;

public interface DictionaryService {
	
	/**
	 * 根据字典类型查找字典代码
	 * @param typecode
	 * @return
	 */
   public List<NHSysCodeItem> getCodeItemsByTypeCode(String typecode);
   
   /**
	 * 删除字典代码类型
	 * @param typeCode 类型编码
	 * @return
	 */
   int delSysCodeTypeByCode(String typeCode);
   
   /**
    * 添加字典代码类型
    * @param record
    * @return
    */
   int addSysCodeType(NHSysCodeType record);
   
   /**
    * 根据code查找字典代码类型
    * @param typeCode
    * @return
    */
   NHSysCodeType findCodeTypeByCode(String typeCode);
   
   /**
    * 修改字典代码类型
    * @param record
    * @return
    */
   int updateSysCodeType(NHSysCodeType record);
   
   /**
    * 根据typecode查询TypeItem数量
    * @param typecode
    * @return
    */
   public int findTypeItemCountByTypeCode(String typecode);
   
   /**
	 * 根据code删除codeItem
	 * @param key
	 * @return
	 */
   int deleteCodeItemByCode(NHSysCodeItem codeitem);
   
   /**
    * 添加CodeItem
    * @param record
    * @return
    */
   int addCodeItem(NHSysCodeItem record);
   
   /**
    * 根据code查询CodeItem
    * @param key
    * @return
    */
   NHSysCodeItem findCodeItenByCode(NHSysCodeItem codeitem);
   
   /**
    * 修改codeItem
    * @param record
    * @return
    */
   int updateCodeItemByCode(NHSysCodeItem record);
}
