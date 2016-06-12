package com.nhry.service.auth.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.PageInfo;
import com.nhry.data.auth.dao.TSysResourceMapper;
import com.nhry.data.auth.dao.TSysRoleMapper;
import com.nhry.data.auth.dao.TSysUserRoleMapper;
import com.nhry.data.auth.domain.TSysRole;
import com.nhry.data.auth.domain.TSysRoleResource;
import com.nhry.data.auth.domain.TSysUserRole;
import com.nhry.exception.MessageCode;
import com.nhry.exception.ServiceException;
import com.nhry.model.auth.UserRoleModel;
import com.nhry.service.BaseService;
import com.nhry.service.auth.dao.RoleService;
import com.nhry.utils.Date;
import com.nhry.utils.PrimaryKeyUtils;

public class RoleServiceImpl extends BaseService implements RoleService {
	private TSysRoleMapper roleMapper;
	private TSysUserRoleMapper urMapper;
	private TSysResourceMapper resMapper;

	@Override
	public int deleteRoleByRid(String id) {
		// TODO Auto-generated method stub
		TSysRole role = findRoleByRid(id);
		if(role == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该角色id对应的角色不存在！");
		}
		List<TSysUserRole> list = this.urMapper.findUserRoleByRid(id);
		if(list != null && list.size() > 0){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"该角色目前存在"+list.size()+"个人员角色关系,暂不能删除!");
		}
		List<TSysRoleResource> rlist = this.resMapper.findRoleResByRid(id);
		if(rlist != null && rlist.size() > 0){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"该角色目前存在"+list.size()+"个角色资源关系,暂不能删除!");
		}
		return this.roleMapper.deleteRoleByRid(id);
	}

	@Override
	public int addRole(TSysRole record) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(record.getRoleName())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"角色名称不能为空!");
		}
		record.setId(PrimaryKeyUtils.generateUuidKey());
		record.setCreateAt(new Date());
		record.setCreateBy(userSessionService.getCurrentUser().getLoginName());
		record.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());
		return this.roleMapper.addRole(record);
	}

	@Override
	public TSysRole findRoleByRid(String id) {
		// TODO Auto-generated method stub
		return this.roleMapper.findRoleByRid(id);
	}

	@Override
	public int updateRoleByRid(TSysRole record) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(record.getId())){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"角色id不能为空!");
		}
		TSysRole role = findRoleByRid(record.getId());
		if(role == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该角色id对应的角色不存在！");
		}
		record.setCreateAt(new Date());
		record.setCreateBy(userSessionService.getCurrentUser().getLoginName());
		record.setCreateByTxt(userSessionService.getCurrentUser().getDisplayName());
		return this.roleMapper.updateRoleByRid(record);
	}

	public void setRoleMapper(TSysRoleMapper roleMapper) {
		this.roleMapper = roleMapper;
	}

	@Override
	public int assignRoleToUsers(UserRoleModel urmodel) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(urmodel.getRoleId()) || urmodel.getLoginNames() == null || urmodel.getLoginNames().size() ==0 ){
			 throw new ServiceException(MessageCode.LOGIC_ERROR, "角色和用户名不能为空！");
		}
		for(String uname : urmodel.getLoginNames()){
			TSysUserRole ur = new TSysUserRole();
			ur.setId(urmodel.getRoleId());
			ur.setLoginName(uname);
			if("Y".equals(urmodel.getIsDefault()) || "N".equals(urmodel.getIsDefault())){
				ur.setIsDefault(urmodel.getIsDefault());
			}else{
				ur.setIsDefault(null);
			}
			TSysUserRole userRole = urMapper.findUserRoleRelations(ur);
			if(userRole != null){
				//如果存在该用户关系，则更新用户角色关系
				if(!StringUtils.isEmpty(ur.getIsDefault()) && !ur.getIsDefault().equals(userRole.getIsDefault())){
					userRole.setIsDefault(urmodel.getIsDefault());
					this.urMapper.updateUserRoleByLoginName(userRole);
				}
			}else{
				this.urMapper.addUserRole(ur);
			}
		}
		return 1;
	}

	public void setUrMapper(TSysUserRoleMapper urMapper) {
		this.urMapper = urMapper;
	}

	@Override
	public int deleteUserRoles(UserRoleModel urmodel) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(urmodel.getLoginName()) || urmodel.getRoleIds()==null || urmodel.getRoleIds().size()==0){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "loginName、roleIds属性值不能为空!");
		}
		return this.urMapper.deleteUserRoles(urmodel);
	}

	public void setResMapper(TSysResourceMapper resMapper) {
		this.resMapper = resMapper;
	}
}
