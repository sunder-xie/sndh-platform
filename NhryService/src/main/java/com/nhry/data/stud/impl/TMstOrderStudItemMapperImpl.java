package com.nhry.data.stud.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.nhry.common.datasource.DynamicSqlSessionTemplate;
import com.nhry.data.stud.dao.TMstOrderStudItemMapper;
import com.nhry.data.stud.domain.TMstOrderStudItem;

/**
 * @author zhaoxijun
 * @date 2017年4月11日
 */
public class TMstOrderStudItemMapperImpl implements TMstOrderStudItemMapper {

	@Autowired
	private DynamicSqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public int insertSdutOrderItem(TMstOrderStudItem orderStudItem) {
		return sqlSessionTemplate.insert("insertSdutOrderItem", orderStudItem);
	}

	@Override
	public TMstOrderStudItem selectByMid(String mid) {
		return sqlSessionTemplate.selectOne("selectByMid", mid);
	}

	@Override
	public List<TMstOrderStudItem> findOrderItemByOrderId(String orderId) {
		return sqlSessionTemplate.selectList("findOrderItemByOrderId", orderId);
	}

	@Override
	public List<TMstOrderStudItem> findOrderItemByMap(Map<String, Object> parameterMap) {
		return sqlSessionTemplate.selectList("findOrderItemByMap", parameterMap);
	}

	@Override
	public List<TMstOrderStudItem> findOrderItemByMapWithBatch(Map<String, Object> parameterMap) {
		return sqlSessionTemplate.selectList("findOrderItemByMapWithBatch", parameterMap);
	}

	@Override
	public int deleteOrderAndItem(Map<String, Object> delMap) {
		return sqlSessionTemplate.update("deleteOrderAndItem", delMap);
	}

}
