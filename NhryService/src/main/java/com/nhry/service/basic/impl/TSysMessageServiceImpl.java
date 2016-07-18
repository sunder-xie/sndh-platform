package com.nhry.service.basic.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.dao.TSysUserMapper;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.dao.TBranchNotsellListMapper;
import com.nhry.data.basic.dao.TSysMessageMapper;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.data.basic.domain.TSysMessage;
import com.nhry.data.order.dao.TPreOrderMapper;
import com.nhry.data.order.domain.TPreOrder;
import com.nhry.model.basic.OrderModel;
import com.nhry.service.BaseService;
import com.nhry.service.basic.dao.TSysMessageService;
import com.nhry.utils.PrimaryKeyUtils;
import com.nhry.utils.SysContant;
import com.nhry.utils.date.Date;

public class TSysMessageServiceImpl extends BaseService implements TSysMessageService {
    private TSysMessageMapper messageMapper;
    private TSysUserMapper userMapper;
    private TBranchNotsellListMapper notsellListMapper;
    private TPreOrderMapper tPreOrderMapper;
    
	@Override
	public int delTSysMessageByNo(String messageNo) {
		// TODO Auto-generated method stub
		return this.messageMapper.delTSysMessageByNo(messageNo);
	}

	@Override
	public int addTSysMessage(TSysMessage record) {
		// TODO Auto-generated method stub
		return this.messageMapper.addTSysMessage(record);
	}

	@Override
	public TSysMessage findTSysmessageByNo(String messageNo) {
		// TODO Auto-generated method stub
		return this.messageMapper.findTSysmessageByNo(messageNo);
	}

	@Override
	public int uptTSysMessage(TSysMessage record) {
		// TODO Auto-generated method stub
		return this.messageMapper.uptTSysMessage(record);
	}

	public void setMessageMapper(TSysMessageMapper messageMapper) {
		this.messageMapper = messageMapper;
	}

	public void setUserMapper(TSysUserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public boolean sendProductsMessages(String title,TMdMara mara) {
		// TODO Auto-generated method stub
		Map<String,String> attrs = new HashMap<String,String>();
		attrs.put("salesOrg", mara.getSalesOrg());
		attrs.put("matnr", mara.getMatnr());
		List<String> list = this.notsellListMapper.getNotSellDealerNosByMatnr(attrs);
		List<TSysUser> users = null;
		if(list != null && list.size() > 0){
			attrs.put("rid", SysContant.getSystemConst("branch_back_office"));
			if(list.contains("-1")){
				//自营奶站不可售卖
				attrs.put("branchGroup", SysContant.getSystemConst("dealer_Branch"));
			}
			StringBuffer sb = new StringBuffer();
			for(String dn : list){
				if("-1".equals(dn)){
					continue;
				}
				sb.append("'").append(dn).append("'").append(",");
			}
			if(sb.length() > 1){
				sb.deleteCharAt(sb.length()-1);
				attrs.put("dealerNo", sb.toString());
			}
			users = this.userMapper.getloginNamesByOrgsandRid(attrs);
		}else{
			//所有奶站都可以售卖
			users = this.userMapper.getloginNamesByOrgsandRid(attrs);
		}
		if(users != null && users.size() > 0){
			sendMessage(users, title+"！产品编号："+mara.getMatnr(), "产品编号："+mara.getMatnr()+"  产品名称："+mara.getMatnrTxt()+" 产品简称："+mara.getMaraEx().getShortTxt()+" 报货提前天数："
					+mara.getMaraEx().getPreDays(), "10");
		}
		return true;
	}
	
	private void sendMessage(List<TSysUser> users,String title,String content,String type){
		if(users != null && users.size() > 0){
			for(TSysUser user : users){
				TSysMessage mess = new TSysMessage();
				mess.setSalesOrg(user.getSalesOrg());
				mess.setDealerNo(user.getDealerId());
				mess.setBranchNo(user.getBranchNo());
				mess.setLoginName(user.getLoginName());
				mess.setType(type);    //产品消息
				mess.setDisplayName(user.getDisplayName());
				mess.setFinishFlag("N");
				mess.setMessageNo(PrimaryKeyUtils.generateUuidKey());
				mess.setCreateAt(new Date());
				mess.setCreateBy(this.userSessionService.getCurrentUser().getLoginName());
				mess.setCreateByTxt(this.userSessionService.getCurrentUser().getDisplayName());
				mess.setSource("10");
				mess.setTitle(title);
				mess.setContent(content);
				this.messageMapper.addTSysMessage(mess);
			}
		}
	}

	public void setNotsellListMapper(TBranchNotsellListMapper notsellListMapper) {
		this.notsellListMapper = notsellListMapper;
	}

	@Override
	public boolean sendOrderMemo(OrderModel om) {
		// TODO Auto-generated method stub
		if(StringUtils.isEmpty(om.getOrderNo()) || StringUtils.isEmpty(om.getMemoType()) || StringUtils.isEmpty(om.getMemoTitle()) || StringUtils.isEmpty(om.getMemoContent())){
			throw new ServiceException(MessageCode.LOGIC_ERROR, "订单编号(orderNo)、备注类别(memoType)、备注标题(memoTitle)、备注内容(memoContent)都必须输入!");
		}
		TPreOrder order = tPreOrderMapper.selectByPrimaryKey(om.getOrderNo());
		if(order == null){
			throw new ServiceException(MessageCode.LOGIC_ERROR,"该订单编号(orderNo)对应的订单不存在，请检查你的订单编号!");
		}
		List<TSysUser> users = null;
		//10-送部门内勤 20-送该订单奶站内勤 30-送部门内勤和奶站内勤
		Map<String,String> attrs = new HashMap<String,String>();
		attrs.put("salesOrg", order.getSalesOrg());
		if("10".equals(om.getMemoType())){
			//部门内勤
			attrs.put("rid", "'"+SysContant.getSystemConst("department_back_office")+"'");
			users = userMapper.getloginNamesByOrgsandRid2(attrs);
			this.sendMessage(users, om.getMemoTitle(), om.getMemoContent(), "20");
			if(!StringUtils.isEmpty(order.getDealerNo())){
				//经销商奶站订单
				attrs.put("dealerNo", order.getDealerNo());
				attrs.put("rid", "'"+SysContant.getSystemConst("dealer_back_office")+"'");
				users = userMapper.getloginNamesByOrgsandRid2(attrs);
				this.sendMessage(users, om.getMemoTitle(), om.getMemoContent(), "20");
			}
		}else if("20".equals(om.getMemoType())){
			//奶站内勤
			attrs.put("branchNo", order.getBranchNo());
			attrs.put("dealerNo", order.getDealerNo());
			attrs.put("rid", "'"+SysContant.getSystemConst("branch_back_office")+"'");
			users = userMapper.getloginNamesByOrgsandRid2(attrs);
			this.sendMessage(users, om.getMemoTitle(), om.getMemoContent(), "20");
		}else if("30".equals(om.getMemoType())){
			//部门内勤和奶站内勤
			om.setMemoType("10");
			this.sendOrderMemo(om);
			
			om.setMemoType("20");
			this.sendOrderMemo(om);
		}
		return true;
	}

	public void settPreOrderMapper(TPreOrderMapper tPreOrderMapper) {
		this.tPreOrderMapper = tPreOrderMapper;
	}
}
