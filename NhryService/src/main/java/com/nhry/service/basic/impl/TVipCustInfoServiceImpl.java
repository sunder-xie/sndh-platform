package com.nhry.service.basic.impl;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.basic.dao.TMdAddressMapper;
import com.nhry.data.basic.dao.TVipAcctMapper;
import com.nhry.data.basic.dao.TVipCustInfoMapper;
import com.nhry.data.basic.domain.TMdAddress;
import com.nhry.data.basic.domain.TVipAcct;
import com.nhry.data.basic.domain.TVipCustInfo;
import com.nhry.model.basic.CustQueryModel;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.TVipCustInfoService;
import com.nhry.service.basic.pojo.Addresses;
import com.nhry.utils.PrimaryKeyUtils;
import com.nhry.utils.date.Date;

public class TVipCustInfoServiceImpl extends BaseService implements TVipCustInfoService {
	private TVipCustInfoMapper tmdVipcust;
	private TMdAddressMapper addressMapper;
	private TVipAcctMapper vipAcctMapper;

	public void setTmdVipcust(TVipCustInfoMapper tmdVipcust) {
		this.tmdVipcust = tmdVipcust;
	}

	@Override
	public int addVipCust(TVipCustInfo record) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(record.getVipName()) || StringUtils.isEmpty(record.getMp()) || StringUtils.isEmpty(record.getBranchNo())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "会员姓名(vipName)、手机号码(mp)、订户奶站(branchNo) 必须填写!");
		}
		Map<String,String> attrs = new HashMap<String,String>();
		attrs.put("salesOrg", this.userSessionService.getCurrentUser().getSalesOrg());
		attrs.put("branchNo", record.getBranchNo());
		attrs.put("phone", record.getMp());
		int count = this.tmdVipcust.getCustCountByPhone(attrs);
		if(count > 0){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "改电话号码对应的订户信息已存在！");
		}
		record.setVipCustNo(PrimaryKeyUtils.generateUuidKey());
		record.setCreateAt(new Date());
		record.setCreateBy(this.userSessionService.getCurrentUser().getLoginName());
		record.setCreateByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		record.setSalesOrg(this.userSessionService.getCurrentUser().getSalesOrg());
		this.tmdVipcust.addVipCust(record);
		if(!StringUtils.isBlank(record.getAddressTxt()) && !StringUtils.isBlank(record.getProvince()) && !StringUtils.isBlank(record.getCity()) &&
				!StringUtils.isBlank(record.getCounty())){
			TMdAddress address = new TMdAddress();
			address.setAddressTxt(record.getAddressTxt());
			address.setProvince(record.getProvince());
			address.setCity(record.getCity());
			address.setCounty(record.getCounty());
			address.setMp(record.getMp());
			address.setRecvName(record.getVipName());
			address.setZip(record.getZip());
			address.setResidentialArea(record.getSubdist());
			address.setStreet(record.getStreet());
			address.setVipCustNo(record.getVipCustNo());
			address.setIsDafault("Y");
			address.setCreateAt(new Date());
			address.setCreateBy(this.userSessionService.getCurrentUser().getLoginName());
			address.setCreateByTxt(this.userSessionService.getCurrentUser().getDisplayName());
			addAddressForCust(address);
		}
		return 1;
	}

	@Override
	public TVipCustInfo findVipCustByNo(String vipCustNo) {
		// TODO Auto-generated method stub
		TVipCustInfo cust = this.tmdVipcust.findVipCustByNo(vipCustNo);
		if(cust != null){
			cust.setAddresses(addressMapper.findCnAddressByCustNo(vipCustNo));
		}
		return cust;
	}

	@Override
	public int updateVipCustByNo(TVipCustInfo record) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(record.getVipCustNo()) || StringUtils.isEmpty(record.getVipName()) || StringUtils.isEmpty(record.getMp()) || StringUtils.isEmpty(record.getBranchNo())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "订户编号(vipCustNo)、会员姓名(vipName)、手机号码(mp)、奶站编号(branchNo)不能为空!");
		}
		//判断同一电话号码，在同一奶站下是否存在相同的订户
		Map<String,String> attrs = new HashMap<String,String>();
		attrs.put("salesOrg", this.userSessionService.getCurrentUser().getSalesOrg());
		attrs.put("branchNo", record.getBranchNo());
		attrs.put("phone", record.getMp());
		attrs.put("custNo", record.getVipCustNo());
		int count = this.tmdVipcust.getCustCountByPhone(attrs);
		if(count > 0){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "改电话号码对应的订户信息已存在！");
		}
		record.setLastModified(new Date());
		record.setLastModifiedBy(this.userSessionService.getCurrentUser().getLoginName());
		record.setLastModifiedByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		this.tmdVipcust.updateVipCustByNo(record);
		//更新地址列表信息
		if(record.getAddresses() != null && record.getAddresses().size() > 0){
			Addresses ad = new Addresses();
			ad.setAddresses(record.getAddresses());
			this.batchUptCustAddress(ad);
		}
		return 1;
	}

	@Override
	public List<TVipCustInfo> findStaCustByPhone(Map<String, String> attrs) {
		// TODO Auto-generated method stub
		return this.tmdVipcust.findStaCustByPhone(attrs);
	}

	@Override
	public List<TVipCustInfo> findCompanyCustByPhone(Map<String, String> attrs) {
		// TODO Auto-generated method stub
		return this.tmdVipcust.findCompanyCustByPhone(attrs);
	}

	@Override
	public TVipCustInfo findVipCustByNoForUpt(String vipCustNo) {
		// TODO Auto-generated method stub
		TVipCustInfo cust = this.tmdVipcust.findVipCustByNoForUpt(vipCustNo);
		if(cust != null){
			cust.setAddresses(this.addressMapper.findOriginAddressByCustNo(vipCustNo));
		}
		return this.tmdVipcust.findVipCustByNoForUpt(vipCustNo);
	}

	@Override
	public int discontinue(String vipCustNo, String status,Date firstTime,Date lastestTime) {
		// TODO Auto-generated method stub
		TVipCustInfo custinfo = this.tmdVipcust.findVipCustOnlyByNo(vipCustNo);
		if(custinfo == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"该订户编号对应的订户信息不存在!");
		}
		TVipCustInfo cust = new TVipCustInfo();
		cust.setVipCustNo(vipCustNo);
		cust.setStatus(status);
		cust.setFirstOrderTime(firstTime);
		cust.setLastOrderTime(lastestTime);
		cust.setLastModified(new Date());
		cust.setLastModifiedBy(this.userSessionService.getCurrentUser().getLoginName());
		cust.setLastModifiedByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		return this.tmdVipcust.uptCustStatus(cust);
	}

	@Override
	public PageInfo findcustMixedTerms(CustQueryModel cust) {
		// TODO Auto-generated method stub
	   cust.setSalesOrg(userSessionService.getCurrentUser().getSalesOrg());
	  return this.tmdVipcust.findcustMixedTerms(cust);
	}

	@Override
	public String addAddressForCust(TMdAddress address) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(address.getVipCustNo()) || StringUtils.isEmpty(address.getAddressTxt()) || StringUtils.isEmpty(address.getProvince()) || StringUtils.isEmpty(address.getCity()) ){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "订户地址详细信息对应的订户编号(vipCustNo)、详细地址(addressTxt)、省份(province)、城市(city)不能为空!");
		}
		TVipCustInfo cust = this.tmdVipcust.findVipCustOnlyByNo(address.getVipCustNo());
		if(cust == null){
			 throw new ServiceException(MessageCode.LOGIC_ERROR,"该订户地址详细信息中vipCustNo对应的订户信息不存在!");
		}
		address.setAddressId(PrimaryKeyUtils.generateUuidKey());
		address.setCreateAt(new Date());
		address.setCreateBy(this.userSessionService.getCurrentUser().getLoginName());
		address.setCreateByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		this.addressMapper.addAddressForCust(address);
		return address.getAddressId();
	}

	public void setAddressMapper(TMdAddressMapper addressMapper) {
		this.addressMapper = addressMapper;
	}

	@Override
	public int uptCustAddress(TMdAddress address) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(address.getAddressId())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "订户地址详细信息对应的地址唯一编号(addressId)不能为空!");
		}
		TMdAddress _address = this.addressMapper.findAddressById(address.getAddressId());
		if(_address == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该地址唯一编号(addressId)对应的地址详细信息不存在!");
		}
		address.setLastModified(new Date());
		address.setLastModifiedBy(this.userSessionService.getCurrentUser().getLoginName());
		address.setLastModifiedByTxt(this.userSessionService.getCurrentUser().getDisplayName());
	  return this.addressMapper.uptCustAddress(address);
	}

	@Override
	public int addVipAcct(TVipAcct record) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(record.getVipCustNo()) || (record.getAcctAmt() == null)){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "订户编号(vipCustNo)、个帐余额(acctAmt)不能为空!");
		}
		TVipCustInfo cust = this.tmdVipcust.findVipCustOnlyByNo(record.getVipCustNo());
		if(cust == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该订户编号对应的订户信息不存在!");
		}
		TVipAcct acct = findVipAcctByCustNo(record.getVipCustNo());
		if(acct == null){
			//添加
			record.setCreateAt(new Date());
			record.setCreateBy(this.userSessionService.getCurrentUser().getLoginName());
			record.setCreateByTxt(this.userSessionService.getCurrentUser().getDisplayName());
			return this.vipAcctMapper.addVipAcct(record);
		}else{
			//修改  
			acct.setLastModified(new Date());
			acct.setLastModifiedBy(this.userSessionService.getCurrentUser().getLoginName());
			acct.setLastModifiedByTxt(this.userSessionService.getCurrentUser().getDisplayName());
			acct.setAcctAmt(acct.getAcctAmt().add(record.getAcctAmt()));
			return this.uptVipAcct(acct);
	   }
	}

	@Override
	public TVipAcct findVipAcctByCustNo(String custNo) {
		// TODO Auto-generated method stub
		return this.vipAcctMapper.findVipAcctByCustNo(custNo);
	}

	@Override
	public int uptVipAcct(TVipAcct record) {
		// TODO Auto-generated method stub
		return this.vipAcctMapper.uptVipAcct(record);
	}

	public void setVipAcctMapper(TVipAcctMapper vipAcctMapper) {
		this.vipAcctMapper = vipAcctMapper;
	}

	@Override
	public TMdAddress findAddressById(String addressId) {
		// TODO Auto-generated method stub
		return this.addressMapper.findAddressById(addressId);
	}

	@Override
	public TMdAddress findAddressDetailById(String id) {
		// TODO Auto-generated method stub
		return this.addressMapper.findAddressDetailById(id);
	}

	@Override
	public List<TMdAddress> findCnAddressByCustNo(String custNo) {
		// TODO Auto-generated method stub
		return this.addressMapper.findCnAddressByCustNo(custNo);
	}

	@Override
	public int uptAddressById(String status, String addressId) {
		// TODO Auto-generated method stub
		TMdAddress address = this.addressMapper.findAddressById(addressId);
		if(address != null){
			//10 : 删除  20 ： 改成默认状态
			if("10".equals(status)){
				 address.setIsDelete("Y");
				 address.setLastModified(new Date());
				 address.setLastModifiedBy(this.userSessionService.getCurrentUser().getSalesOrg());
				 address.setLastModifiedByTxt(this.userSessionService.getCurrentUser().getDisplayName());
				 return this.addressMapper.uptCustAddress(address);
			}else if("20".equals(status)){
				 address.setIsDafault("Y");
				 address.setLastModified(new Date());
				 address.setLastModifiedBy(this.userSessionService.getCurrentUser().getSalesOrg());
				 address.setLastModifiedByTxt(this.userSessionService.getCurrentUser().getDisplayName());
				 this.addressMapper.uptCustAddress(address);
				 //将该订户下的其他详细地址设置为非默认状态
				return this.addressMapper.uptCustAddressUnDefault(address);
			}else{
				throw new ServiceException(MessageCode.LOGIC_ERROR, "状态标示不符合约定条件!");
			}
		}else{
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该地址编号对应的地址信息不存在!");
		}
	}

	@Override
	public int batchUptCustAddress(Addresses record) {
		// TODO Auto-generated method stub
		if(record != null){
			for(TMdAddress ad : record.getAddresses()){
				if(StringUtils.isBlank(ad.getAddressId())){
					//新增
					this.addAddressForCust(ad);
				}else{
					//修改
					this.addressMapper.uptCustAddress(ad);
				}
			}
		}
		return 1;
	}
}
