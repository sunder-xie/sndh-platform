package com.nhry.service.milktrans.impl;

import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.dao.TMdMaraMapper;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.data.milktrans.dao.TSsmReqGoodsOrderItemMapper;
import com.nhry.data.milktrans.dao.TSsmReqGoodsOrderMapper;
import com.nhry.data.milktrans.domain.TSsmReqGoodsOrder;
import com.nhry.data.milktrans.domain.TSsmReqGoodsOrderItem;
import com.nhry.data.milktrans.domain.TSsmReqGoodsOrderItemUpt;
import com.nhry.data.order.dao.TOrderDaliyPlanItemMapper;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.model.milktrans.*;
import com.nhry.service.milktrans.dao.RequireOrderService;
import com.nhry.service.pi.dao.PIRequireOrderService;
import com.nhry.utils.PrimaryKeyUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by gongjk on 2016/6/24.
 */
public class RequireOrderServiceImpl implements RequireOrderService {

    private TSsmReqGoodsOrderItemMapper tSsmReqGoodsOrderItemMapper;
    private TSsmReqGoodsOrderMapper  tSsmReqGoodsOrderMapper ;
    private TOrderDaliyPlanItemMapper tOrderDaliyPlanItemMapper;
    private UserSessionService userSessionService;
    private PIRequireOrderService piRequireOrderService;
    private TMdMaraMapper tMdMaraMapper;
    public void setPiRequireOrderService(PIRequireOrderService piRequireOrderService) {
        this.piRequireOrderService = piRequireOrderService;
    }
    public void settSsmReqGoodsOrderItemMapper(TSsmReqGoodsOrderItemMapper tSsmReqGoodsOrderItemMapper) {
        this.tSsmReqGoodsOrderItemMapper = tSsmReqGoodsOrderItemMapper;
    }
    public void setUserSessionService(UserSessionService userSessionService) {
        this.userSessionService = userSessionService;
    }
    public void settSsmReqGoodsOrderMapper(TSsmReqGoodsOrderMapper tSsmReqGoodsOrderMapper) {
        this.tSsmReqGoodsOrderMapper = tSsmReqGoodsOrderMapper;
    }
    public void settOrderDaliyPlanItemMapper(TOrderDaliyPlanItemMapper tOrderDaliyPlanItemMapper) {
        this.tOrderDaliyPlanItemMapper = tOrderDaliyPlanItemMapper;
    }
    public void settMdMaraMapper(TMdMaraMapper tMdMaraMapper) {
        this.tMdMaraMapper = tMdMaraMapper;
    }

    @Override
    public RequireOrderModel creatRequireOrder() {
        RequireOrderSearch rModel = new RequireOrderSearch();
        Date today = new Date();
        TSysUser user = userSessionService.getCurrentUser();
        rModel.setBranchNo(user.getBranchNo());
        rModel.setRequiredDate(today);
        rModel.setSalesOrg(user.getSalesOrg());
        TSsmReqGoodsOrder order = null;
        //首先查看今天的要货计划是否已存在
        order = this.tSsmReqGoodsOrderMapper.searchRequireOrder(rModel);

        if(order !=null){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"当天要货计划已存在");
        }else{
            order = new TSsmReqGoodsOrder();
            String orderNo = PrimaryKeyUtils.generateUuidKey();
            order.setRequiredDate(today);
            order.setStatus("10");
            order.setOrderNo(orderNo);
            order.setOrderDate(today);
            order.setBranchNo(user.getBranchNo());
            order.setCreateAt(today);
            order.setCreateBy(user.getLoginName());
            order.setCreateByTxt(user.getDisplayName());
            order.setLastModified(today);
            order.setLastModifiedByTxt(user.getDisplayName());
            order.setLastModifiedBy(user.getLoginName());
            tSsmReqGoodsOrderMapper.insertRequireOrder(order);
        }
        //查看明天和后天的订单
        int k = 0;
        for(int i = 1;i<=2;i++){
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(today);
            calendar.add(calendar.DATE,i);//把日期往后增加i天.整数往后推 这个时间就是日期往后推一天的结果
            Date requiredDate = calendar.getTime();
            rModel.setRequiredDate(requiredDate);
            rModel.setPreDays(i);
            List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectDaliyPlansByBranchAndDay(rModel);
            //将i天后的日订单中符合的产品加入到 生成的要货计划
            if(items!=null && items.size()>0){
                for(int j=0 ;j<items.size();j++ ){
                    k++;
                    TOrderDaliyPlanItem entry = items.get(j);
                    TSsmReqGoodsOrderItem item = new TSsmReqGoodsOrderItem();
                    item.setFlag("1");
                    item.setItemNo(k);
                    item.setUnit(entry.getUnit());
                    item.setOrderDate(today);
                    item.setOrderNo(order.getOrderNo());
                    item.setMatnr(entry.getMatnr());
                    item.setMatnrTxt(entry.getMatnrTxt());
                    item.setQty(entry.getQty());
                    item.setIncreQty(0);
                    this.tSsmReqGoodsOrderItemMapper.insertRequireOrderItem(item);
                }
            }

        }
        //查询出今天的要货计划
        return this.searchRequireOrder(today);

    }

    /**
     * 根据 日期 获取当前登录人所在奶站的 要货计划
     * @param requiredDate
     * @return
     */
    @Override
    public RequireOrderModel searchRequireOrder(Date  requiredDate) {
        if(requiredDate == null){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"查询要货计划的日期不能为空");
        }
        TSysUser user = userSessionService.getCurrentUser();
        RequireOrderModel orderModel = new RequireOrderModel();
        RequireOrderSearch rModel = new RequireOrderSearch();

        String salesOrg = user.getSalesOrg();
        rModel.setBranchNo(user.getBranchNo());
        rModel.setRequiredDate(requiredDate);
        TSsmReqGoodsOrder order  = this.tSsmReqGoodsOrderMapper.searchRequireOrder(rModel);

        if(order!= null){
            orderModel.setStatus(order.getStatus());
            orderModel.setRequiredDate(order.getOrderDate());
            orderModel.setBranchNo(order.getBranchNo());
            orderModel.setOrderNo(order.getOrderNo());
            List<TSsmReqGoodsOrderItem> items = this.tSsmReqGoodsOrderItemMapper.getReqGoodsItemsByOrderNo(order.getOrderNo());
            List<OrderRequireItem> entries = new ArrayList<OrderRequireItem>();
            for(TSsmReqGoodsOrderItem item :items){
                OrderRequireItem entry = new OrderRequireItem();
                Map<String,String> map = new HashMap<String,String>();
                map.put("salesOrg",salesOrg);
                map.put("matnr",item.getMatnr());

                TMdMara mara = tMdMaraMapper.selectProductByCode(map);
                entry.setMatnr(item.getMatnr());
                entry.setMatnrTxt(mara.getMatnrTxt());
                entry.setQty(item.getQty());
                entry.setIncreQty(item.getIncreQty());
                entries.add(entry);
            }
            orderModel.setEntries(entries);
        }
            return orderModel;
    }
    /**
     * 修改新添加的要货计划行
     * @param rModel
     * @return
     */
    @Override
    public int uptNewRequireOrderItem(UpdateNewRequiredModel rModel) {

       try{
           TSysUser user = userSessionService.getCurrentUser();
           String orderNo = rModel.getOrderNo();
           TSsmReqGoodsOrder orderModel = this.tSsmReqGoodsOrderMapper.getRequireOrderByNo(orderNo);
           //获取数据库中存好的要货计划 如果状态已经确定，则不能修改
           if(orderModel.getStatus() == "30"){
               throw new ServiceException(MessageCode.LOGIC_ERROR,"要货订单已确定，不能改变");
           }

           Map<String,String> map = new HashMap<String,String>();
           map.put("salesOrg",user.getSalesOrg());
           map.put("matnr",rModel.getMatnr());

           TMdMara mara = tMdMaraMapper.selectProductByCode(map);
           TSsmReqGoodsOrderItemUpt itemUpt = new TSsmReqGoodsOrderItemUpt();
           itemUpt.setMatnr(rModel.getMatnr());
           itemUpt.setOrderNo(orderNo);
           itemUpt.setMatnrTxt(mara.getMatnrTxt());
           itemUpt.setIncreQty(rModel.getIncreQty());
           itemUpt.setQty(rModel.getQty());
           itemUpt.setUnit(mara.getBaseUnit());
           itemUpt.setOldMatnr(rModel.getOldMatnr());
           this.tSsmReqGoodsOrderItemMapper.uptNewReqGoodsItem(itemUpt);
           return 1;
       }catch (Exception e){
           throw new ServiceException(MessageCode.LOGIC_ERROR,"修改失败");
       }

    }

    @Override
    public int addRequireOrderItem(TSsmReqGoodsOrderItem item) {
        String message = "";
        String orderNo = item.getOrderNo();
        TSsmReqGoodsOrderItem oldItem = tSsmReqGoodsOrderItemMapper.getReqGoodsItemsByMatnrAndOrderNo(orderNo,item.getMatnr());
        if(oldItem!=null){
            message = "该产品已存在，请重新选择";
            throw new ServiceException(MessageCode.LOGIC_ERROR,message);
        }


        TSysUser user = userSessionService.getCurrentUser();
        TSsmReqGoodsOrder orderModel = this.tSsmReqGoodsOrderMapper.getRequireOrderByNo(orderNo);

        if("30".equals(orderModel.getStatus())){
            message = "今天的要货计划已确定，不可再添加或修改";
            throw new ServiceException(MessageCode.LOGIC_ERROR,message);
        }
        orderModel.setLastModified(new Date());
        orderModel.setLastModifiedBy(user.getLoginName());
        orderModel.setLastModifiedByTxt(user.getDisplayName());
        tSsmReqGoodsOrderMapper.uptRequireGoodsModifyInfo(orderModel);

        List<TSsmReqGoodsOrderItem> items = this.tSsmReqGoodsOrderItemMapper.getReqGoodsItemsByOrderNo(orderNo);
        if(StringUtils.isBlank(item.getUnit())){
            Map<String,String> map = new HashMap<String,String>();
            map.put("salesOrg",user.getSalesOrg());
            map.put("matnr",item.getMatnr());
            TMdMara mara = tMdMaraMapper.selectProductByCode(map);
            item.setUnit(mara.getBaseUnit());
        }
        item.setItemNo(items.size()+1);
        item.setFlag("2");
        item.setOrderDate(orderModel.getOrderDate());
        return tSsmReqGoodsOrderItemMapper.insertRequireOrderItem(item);
    }

    @Override
    public int delRequireOrderItem(ReqGoodsOrderItemSearch item) {
        TSsmReqGoodsOrderItem oldItem = tSsmReqGoodsOrderItemMapper.getReqGoodsItemsByMatnrAndOrderNo(item.getOrderNo(),item.getMatnr());
        if("1".equals(oldItem.getFlag())){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"生成的要货计划行不能删除");
        }
        return tSsmReqGoodsOrderItemMapper.delRequireOrderItem(item);
    }

    @Override
    public int uptRequireOrder(UpdateRequiredModel uModel) {
        String message = "修改失败";
        try{
            String orderNo = uModel.getOrderNo();
            TSsmReqGoodsOrder orderModel = this.tSsmReqGoodsOrderMapper.getRequireOrderByNo(orderNo);
            //获取数据库中存好的要货计划 如果状态已经确定，则不能修改
            if(orderModel.getStatus() == "30"){
                message = "要货订单已确定，不能改变";
                throw new ServiceException(MessageCode.LOGIC_ERROR,message);
            }


            this.tSsmReqGoodsOrderItemMapper.uptReqGoodsItem(uModel);
            return 1;
        }catch (Exception e){
            throw new ServiceException(MessageCode.LOGIC_ERROR,message);
        }
    }

    @Override
    public int sendRequireOrderToERP() {
        Date today = new Date();
        TSysUser user = userSessionService.getCurrentUser();
        String salesOrg =user.getGroupId();
        RequireOrderSearch search = new RequireOrderSearch();
        search.setRequiredDate(today);
        TSsmReqGoodsOrder order = tSsmReqGoodsOrderMapper.searchRequireOrder(search);
      //  piRequireOrderService.generateRequireOrder(order);
        return 0;
    }


}
