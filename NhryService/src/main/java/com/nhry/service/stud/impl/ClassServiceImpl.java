package com.nhry.service.stud.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;

import com.github.pagehelper.PageInfo;
import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.stud.dao.TMdClassMapper;
import com.nhry.data.stud.domain.TMdClass;
import com.nhry.model.stud.ClassQueryModel;
import com.nhry.service.stud.dao.ClassService;

/**
 * @author zhaoxijun
 * @data 2017年4月10日
 */
public class ClassServiceImpl implements ClassService {

	@Autowired
	private UserSessionService userSessionService;
	
	@Autowired
	private TMdClassMapper classMapper;
	
	@Override
	public TMdClass findClassByClassCode(String classCode) {
		return classMapper.selectByClassCode(classCode);
	}

	@Override
	public List<TMdClass> findClassListBySalesOrg(String salesOrg) {
		if(StringUtils.isBlank(salesOrg)){
			salesOrg = this.userSessionService.getCurrentUser().getSalesOrg();
		}
		if(StringUtils.isBlank(salesOrg)){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "销售组织编码必填");
		}
		return classMapper.findClassListBySalesOrg(salesOrg);
	}

	@Override
	public PageInfo<TMdClass> findClassPage(ClassQueryModel queryModel) {
		if(null == queryModel || StringUtils.isBlank(queryModel.getPageNum())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "当前页数必填");
		}
		if(StringUtils.isBlank(queryModel.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "每页显示条数必填");
		}
		return classMapper.findClassPage(queryModel);
	}

	@Override
	public int addClass(TMdClass mdClass) {
		if(null == mdClass || StringUtils.isBlank(mdClass.getClassCode())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "班级代码必填");
		}
		if(StringUtils.isBlank(mdClass.getClassName())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "班级名称必填");
		}
		if(StringUtils.isBlank(this.userSessionService.getCurrentUser().getSalesOrg())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "当前用户未归属销售组织");
		}
		Date date = new Date();
		mdClass.setCreateAt(date);
		mdClass.setCreateBy(this.userSessionService.getCurrentUser().getLoginName());
		mdClass.setCreateByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		mdClass.setLastModified(date);
		mdClass.setLastModifiedBy(this.userSessionService.getCurrentUser().getLoginName());
		mdClass.setLastModifiedByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		mdClass.setSalesOrg(this.userSessionService.getCurrentUser().getSalesOrg());
		mdClass.setSort(0);
		mdClass.setVisiable("10");
		try {
			return classMapper.insertClass(mdClass);
		} catch (DuplicateKeyException e) {
			throw new ServiceException(MessageCode.LOGIC_ERROR, "班级代码系统已存在，请重新输入");
		}
	}

	@Override
	public int updClass(TMdClass mdClass) {
		
		if(null == mdClass || StringUtils.isBlank(mdClass.getClassCode())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "班级代码必传");
		}
		if(StringUtils.isBlank(mdClass.getClassName())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "班级名称必填");
		}
		if(StringUtils.isBlank(this.userSessionService.getCurrentUser().getSalesOrg())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "当前用户未归属销售组织");
		}
		
		TMdClass findMdClass = this.findClassByClassCode(mdClass.getClassCode());
		if(null == findMdClass){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "系统不存在班级代码"+mdClass.getClassCode());
		}
		
		Date date = new Date();
		mdClass.setLastModified(date);
		mdClass.setLastModifiedBy(this.userSessionService.getCurrentUser().getLoginName());
		mdClass.setLastModifiedByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		mdClass.setSalesOrg(this.userSessionService.getCurrentUser().getSalesOrg());
		mdClass.setVisiable("10");
		return classMapper.updateTMdClass(mdClass);
	}

	@Override
	public int delClass(String classCode) {
		if(StringUtils.isBlank(classCode)){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "班级代码必传");
		}
		TMdClass findMdClass = this.findClassByClassCode(classCode);
		if(null == findMdClass){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "系统不存在班级代码:"+classCode);
		}
		
		return classMapper.deleteByClassCode(classCode);
	}

}
