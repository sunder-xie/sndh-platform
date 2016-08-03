package com.nhry.service.basic.impl;

import com.github.pagehelper.PageInfo;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.dao.TMdAddressMapper;
import com.nhry.data.basic.dao.TMdBranchMapper;
import com.nhry.data.basic.dao.TVipAcctMapper;
import com.nhry.data.basic.dao.TVipCustInfoMapper;
import com.nhry.data.basic.domain.TMdAddress;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.basic.domain.TVipAcct;
import com.nhry.data.basic.domain.TVipCustInfo;
import com.nhry.model.basic.CustQueryModel;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.TVipCustInfoService;
import com.nhry.service.basic.pojo.Addresses;
import com.nhry.utils.PrimaryKeyUtils;
import com.nhry.utils.date.Date;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TVipCustInfoServiceImpl extends BaseService implements TVipCustInfoService {
	private TVipCustInfoMapper tmdVipcust;
	private TMdAddressMapper addressMapper;
	private TVipAcctMapper vipAcctMapper;
	private TMdBranchMapper branchMapper;

	public void setTmdVipcust(TVipCustInfoMapper tmdVipcust) {
		this.tmdVipcust = tmdVipcust;
	}

	@Override
	public String addVipCust(TVipCustInfo record) {
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
			addAddressForCust(address,null);
		}
		return record.getVipCustNo();
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
		if(custinfo.getFirstOrderTime()==null)cust.setFirstOrderTime(firstTime);
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
	   if(StringUtils.isEmpty(cust.getStationType()) && !StringUtils.isEmpty(this.userSessionService.getCurrentUser().getDealerId())){
		   //经销商内勤，只能看自己本经销商底下所有的奶站的订户
		   cust.setStationType("02");
		   cust.setDealerNo(this.userSessionService.getCurrentUser().getDealerId());
	   }
	   if(StringUtils.isEmpty(cust.getStation()) && !StringUtils.isEmpty(this.userSessionService.getCurrentUser().getBranchNo())){
		   cust.setStation(this.userSessionService.getCurrentUser().getBranchNo());
	   }
	  return this.tmdVipcust.findcustMixedTerms(cust);
	}

	@Override
	public String addAddressForCust(TMdAddress address,String branchNo) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(address.getRecvName()) || StringUtils.isEmpty(address.getAddressTxt()) || StringUtils.isEmpty(address.getProvince()) || StringUtils.isEmpty(address.getCity()) ){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "订户地址详细信息对应的收货人(recvName)、详细地址(addressTxt)、省份(province)、城市(city)不能为空!");
		}
		boolean tag = false;
		if(!StringUtils.isEmpty(branchNo) && StringUtils.isEmpty(address.getVipCustNo())){
			//来自电商，需要自动创建订户
			tag = true;
			Map<String,String> attrs = new HashMap<String,String>();
			attrs.put("salesOrg", this.userSessionService.getCurrentUser().getSalesOrg());
			attrs.put("branchNo",branchNo);
			attrs.put("phone", address.getMp());
			String custNo = this.tmdVipcust.getCustNoByPhone(attrs);
			if(StringUtils.isEmpty(custNo)){
				TVipCustInfo cust = new TVipCustInfo();
				cust.setVipCustNo(PrimaryKeyUtils.generateUuidKey());
				cust.setProvince(address.getProvince());
				cust.setCity(address.getCity());
				cust.setCounty(address.getCounty());
				cust.setAddressTxt(address.getAddressTxt());
				cust.setMp(address.getMp());
				cust.setVipName(address.getRecvName());
				cust.setBranchNo(branchNo);
				cust.setSubdist(address.getResidentialArea());
				cust.setCreateAt(new Date());
				cust.setCreateBy(this.userSessionService.getCurrentUser().getLoginName());
				cust.setCreateByTxt(this.userSessionService.getCurrentUser().getDisplayName());
				cust.setSalesOrg(this.userSessionService.getCurrentUser().getSalesOrg());
				this.tmdVipcust.addVipCust(cust);
				address.setVipCustNo(cust.getVipCustNo());
			}else{
				address.setVipCustNo(custNo);
				attrs.clear();
				attrs.put("province", address.getProvince());
				attrs.put("city", address.getCity());
				attrs.put("county", address.getCounty());
				attrs.put("residentialArea", address.getResidentialArea());
				attrs.put("addressTxt", address.getAddressTxt());
				List<TMdAddress> addresses = this.addressMapper.findAddressByMixedTerms(attrs);
				if(addresses != null && addresses.size() > 0){
					return custNo+","+addresses.get(0).getAddressId();
				}
			}
		}else if(StringUtils.isEmpty(address.getVipCustNo())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "订户地址详细信息对应的订户编号(vipCustNo)不能为空!");
		}
		if(StringUtils.isEmpty(branchNo)){
			TVipCustInfo cust = this.tmdVipcust.findVipCustOnlyByNo(address.getVipCustNo());
			if(cust == null){
				 throw new ServiceException(MessageCode.LOGIC_ERROR,"该订户地址详细信息中vipCustNo对应的订户信息不存在!");
			}
		}
		address.setAddressId(PrimaryKeyUtils.generateUuidKey());
		address.setCreateAt(new Date());
		address.setCreateBy(this.userSessionService.getCurrentUser().getLoginName());
		address.setCreateByTxt(this.userSessionService.getCurrentUser().getDisplayName());
		this.addressMapper.addAddressForCust(address);
		return address.getVipCustNo()+","+address.getAddressId();
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
					this.addAddressForCust(ad,null);
				}else{
					//修改
					this.addressMapper.uptCustAddress(ad);
				}
			}
		}
		return 1;
	}

	@Override
	public int deleteCustByCustNo(String custNo) {
		// TODO Auto-generated method stub
	  //删除订户的地址列表
	  addressMapper.deleteAddressByCustNo(custNo);
	  //删除订户
	  return tmdVipcust.deleteCustByCno(custNo);
	}

	@Override
	public String uptCustBranchNo(String custNo, String branchNo) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(custNo) || StringUtils.isEmpty(branchNo)){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"custNo、branchNo参数不能为空！");
		}
		TMdBranch branch = branchMapper.selectBranchByNo(branchNo);
		if(branch == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "该奶站编号对应的奶站不存在!");
		}
		TSysUser user = this.userSessionService.getCurrentUser();
		TVipCustInfo cust = new TVipCustInfo();
		cust.setSalesOrg(branch.getSalesOrg());
		cust.setDealerNo(branch.getDealerNo());
		cust.setBranchNo(branchNo);
		cust.setLastModified(new Date());
		cust.setLastModifiedBy(user.getLoginName());
		cust.setLastModifiedByTxt(user.getDisplayName());
		tmdVipcust.updateVipCustByNo(cust);
		return branch.getSalesOrg();
	}

	public void setBranchMapper(TMdBranchMapper branchMapper) {
		this.branchMapper = branchMapper;
	}
}
