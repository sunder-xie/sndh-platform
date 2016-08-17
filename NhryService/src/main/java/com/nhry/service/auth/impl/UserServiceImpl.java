package com.nhry.service.auth.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.dao.TSysRoleMapper;
import com.nhry.data.auth.dao.TSysUserMapper;
import com.nhry.data.auth.domain.TSysAccesskey;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.domain.TMdBranchEmp;
import com.nhry.data.config.dao.NHSysCodeItemMapper;
import com.nhry.data.config.domain.NHSysCodeItem;
import com.nhry.model.auth.UserQueryModel;
import com.nhry.model.auth.UserQueryModel2;
import com.nhry.model.auth.UserQueryModel3;
import com.nhry.service.BaseService;
import com.nhry.service.auth.dao.ResourceService;
import com.nhry.service.auth.dao.TSysAccesskeyService;
import com.nhry.service.auth.dao.UserService;
import com.nhry.service.basic.dao.BranchEmpService;
import com.nhry.utils.Base64Util;
import com.nhry.utils.SysContant;
import com.nhry.utils.date.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.List;

public class UserServiceImpl extends BaseService implements UserService {
	private TSysUserMapper userMapper;
	private TSysRoleMapper roleMapper;
	private ResourceService resService;
	private NHSysCodeItemMapper codeItemMapper;
	private RedisTemplate objectRedisTemplate;
	private BranchEmpService branchEmpService;
	private TSysAccesskeyService accessKeyService;

	@Override
	public PageInfo findUser(UserQueryModel um){
		// TODO Auto-generated method stub
		return userMapper.findUser(um);
	}

	public int addUser(TSysUser user) {
		if(StringUtils.isEmpty(user.getLoginName()) || StringUtils.isEmpty(user.getDisplayName())){
			 throw new ServiceException(MessageCode.LOGIC_ERROR, "loginName、displayName属性值不能为空!");
		}
		if(!StringUtils.isEmpty(user.getCustomizedHrregion())){
			NHSysCodeItem item = new NHSysCodeItem();
			item.setParent(user.getCustomizedHrregion());
			item.setTypeCode(SysContant.getSystemConst("sales_org"));
			List<NHSysCodeItem> items = codeItemMapper.findItemsByParentCode(item);
			if(items != null && items.size() > 0 && !StringUtils.isEmpty(items.get(0).getItemCode())){
				user.setSalesOrg(items.get(0).getItemCode());
			}
		}
		TSysUser u = this.userMapper.findUserByLoginName(user.getLoginName());
		if(u == null){
			user.setCreateOn(new Date());
			user.setLastModified(new Date());
			return userMapper.addUser(user);
		}else{
			user.setLastModified(new Date());
			return this.userMapper.updateUser(user);
		}
	}

	@Override
	public TSysUser login(TSysUser user) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(user.getLoginName())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"用户名不能为空!");
		}
		TSysUser _user = userMapper.login(user);
		if(_user == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"系统不存在该用户,请检查你的用户名!");
		}
		return _user;
	}

	public void setUserMapper(TSysUserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public TSysUser findUserByLoginName(String loginName) {
		// TODO Auto-generated method stub
		return userMapper.findUserByLoginName(loginName);
	}

	@Override
	public int updateUser(TSysUser record) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(record.getLoginName())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "用户名登录名不能为空！");
		}
		record.setLastModified(new Date());
		if("-1".equals(record.getDealerId())){
			record.setDealerId(null);
		}
		userMapper.updateUser(record);
		
		if(!StringUtils.isEmpty(record.getBranchNo())){
			//奶站员工
			TSysUser user = new TSysUser();
			user.setLoginName(record.getLoginName());
			TSysUser sysuser = userMapper.login(user);
			if(sysuser == null){
				throw new ServiceException(MessageCode.LOGIC_ERROR, "用户名对应的用户不存在！");
			}
			TMdBranchEmp emp = new TMdBranchEmp();
			emp.setEmpNo(sysuser.getLoginName());
			emp.setHrEmpNo(sysuser.getLoginName());
			emp.setBranchNo(sysuser.getBranchNo());
			emp.setSalesOrg(sysuser.getSalesOrg());
			emp.setEmpName(sysuser.getDisplayName());
			emp.setMp(sysuser.getMobile());
			emp.setIdNo(sysuser.getSmartIdcardnumber());
			emp.setJoinDate(sysuser.getCustomizedJoininworkdate());
			branchEmpService.addBranchEmp(emp);
		}
		return 1;
	}

	@Override
	public int deleteUserByLoginName(String uname) {
		// TODO Auto-generated method stub
		TSysUser user = this.findUserByLoginName(uname);
		if(user == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该用户名对应的用户信息不存在！");
		}
		user.setLastModified(new Date());
		return this.userMapper.deleteUserByLoginName(user);
	}

	@Override
	public List<TSysUser> findUserByRoleId(UserQueryModel2 um) {
//		if(StringUtils.isEmpty(um.getRoleId())){
//			throw new ServiceException(MessageCode.LOGIC_ERROR,"roleId不能为空！");
//		}
		return userMapper.findUserByRoleId(um);
	}

	@Override
	public PageInfo findUserPageByRoleId(UserQueryModel um) {
		if(StringUtils.isEmpty(um.getPageNum()) || StringUtils.isEmpty(um.getPageSize()) || StringUtils.isEmpty(um.getRoleId())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"roleId,pageNum和pageSize不能为空！");
		}
		return userMapper.findUserPageByRoleId(um);
	}

	@Override
	public List<TSysUser> findNotRoleUser(UserQueryModel3 model) {
		return userMapper.findNotRoleUser(model);
	}

	@Override
	public PageInfo<TSysUser> findNotRoleUserPage(UserQueryModel3 model) {
		if(StringUtils.isEmpty(model.getPageNum()) || StringUtils.isEmpty(model.getPageSize())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"pageNum和pageSize不能为空！");
		}
		return userMapper.findNotRoleUserPage(model);
	}

	public void setRoleMapper(TSysRoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	public void setResService(ResourceService resService) {
		this.resService = resService;
	}

	public void setCodeItemMapper(NHSysCodeItemMapper codeItemMapper) {
		this.codeItemMapper = codeItemMapper;
	}

	@Override
	public TSysUser getCurrentLoginUser() {
		// TODO Auto-generated method stub
		return this.userSessionService.getCurrentUser();
	}

	public void setObjectRedisTemplate(RedisTemplate objectRedisTemplate) {
		this.objectRedisTemplate = objectRedisTemplate;
	}

	public void setBranchEmpService(BranchEmpService branchEmpService) {
		this.branchEmpService = branchEmpService;
	}

	@Override
	public boolean logout(String token) {
		// TODO Auto-generated method stub
		TSysAccesskey key = new TSysAccesskey();
		key.setAccesskey(Base64Util.decodeStr(token));
		key.setType("10");
		TSysAccesskey ak = accessKeyService.findAccesskeyByKey(key);
		if(ak == null){
			return false;
		}else{
			accessKeyService.deleteAccesskeyByAk(key);
		}
		return true;
	}

	public void setAccessKeyService(TSysAccesskeyService accessKeyService) {
		this.accessKeyService = accessKeyService;
	}
}
