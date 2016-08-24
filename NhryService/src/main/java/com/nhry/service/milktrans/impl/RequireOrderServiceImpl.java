package com.nhry.service.milktrans.impl;

import com.nhry.common.auth.UserSessionService;
import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.dao.TMdBranchMapper;
import com.nhry.data.basic.dao.TMdMaraMapper;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.basic.domain.TMdMara;
import com.nhry.data.milk.dao.TDispOrderMapper;
import com.nhry.data.milk.domain.TDispOrder;
import com.nhry.data.milktrans.dao.TSsmReqGoodsOrderItemMapper;
import com.nhry.data.milktrans.dao.TSsmReqGoodsOrderMapper;
import com.nhry.data.milktrans.dao.TSsmSalOrderItemMapper;
import com.nhry.data.milktrans.dao.TSsmSalOrderMapper;
import com.nhry.data.milktrans.domain.*;
import com.nhry.data.order.dao.TOrderDaliyPlanItemMapper;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.model.milktrans.*;
import com.nhry.service.milktrans.dao.RequireOrderService;
import com.nhry.service.pi.dao.PIRequireOrderService;
import com.nhry.utils.DateUtil;
import com.nhry.webService.client.PISuccessMessage;
import org.apache.commons.lang3.StringUtils;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by gongjk on 2016/6/24.
 */
public class RequireOrderServiceImpl implements RequireOrderService {
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
    private static final int  randomNum  = (int)((Math.random()*90)+10);
    private TSsmReqGoodsOrderItemMapper tSsmReqGoodsOrderItemMapper;
    private TSsmReqGoodsOrderMapper  tSsmReqGoodsOrderMapper ;
    private TOrderDaliyPlanItemMapper tOrderDaliyPlanItemMapper;
    private TDispOrderMapper    tDispOrderMapper;
    private UserSessionService userSessionService;
    private PIRequireOrderService piRequireOrderService;
    private TMdBranchMapper branchMapper;
    private TMdMaraMapper tMdMaraMapper;
    private TSsmSalOrderMapper tSsmSalOrderMapper;
    private TSsmSalOrderItemMapper tSsmSalOrderItemMapper;

    public void settDispOrderMapper(TDispOrderMapper tDispOrderMapper) {
        this.tDispOrderMapper = tDispOrderMapper;
    }

    public void settSsmSalOrderMapper(TSsmSalOrderMapper tSsmSalOrderMapper) {
        this.tSsmSalOrderMapper = tSsmSalOrderMapper;
    }
    public void settSsmSalOrderItemMapper(TSsmSalOrderItemMapper tSsmSalOrderItemMapper) {
        this.tSsmSalOrderItemMapper = tSsmSalOrderItemMapper;
    }

    public void setBranchMapper(TMdBranchMapper branchMapper) {
        this.branchMapper = branchMapper;
    }
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
        Date requireDate = null;
        TSysUser user = userSessionService.getCurrentUser();
        //如果是华西或者天音 则requiredDate日期为今天  否则requiredDate为明天
        if("4181".equals(user.getSalesOrg()) || "4390".equals(user.getSalesOrg())){
            requireDate = today;
        }else{
            requireDate = DateUtil.getTomorrow(new Date());
        }
        rModel.setBranchNo(user.getBranchNo());
        rModel.setOrderDate(today);
        rModel.setRequiredDate(requireDate);
        rModel.setSalesOrg(user.getSalesOrg());
        TSsmReqGoodsOrder order = null;
        //首先查看今天的要货计划是否已存在
       order =  this.tSsmReqGoodsOrderMapper.searchRequireOrder(rModel);
        if(order !=null){
            if(StringUtils.isNoneBlank(order.getVoucherNo())){
                //如果已生成，并且已经发送过ERP
                throw new ServiceException(MessageCode.LOGIC_ERROR,"当天要货计划已生成，并且已经发送过ERP，不能再次生成，请查阅!!!");
            }else{
                tSsmReqGoodsOrderItemMapper.delRequireOrderItemsByOrderNo(order.getOrderNo());
                tSsmReqGoodsOrderMapper.deleRequireGoodsOrderbyNo(order.getOrderNo());
            }


        }
        //查看明天和后天的订单
        rModel.setFirstDay(DateUtil.getTomorrow(today));
        rModel.setSecondDay(DateUtil.getDayAfterTomorrow(today));
        List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectDaliyPlansByBranchAndDay(rModel);
        //将i天后的日订单中符合的产品加入到 生成的要货计划
        if(items!=null && items.size()>0){
            order = new TSsmReqGoodsOrder();
            String orderNo = this.generateSal30Id();
            order.setRequiredDate(requireDate);
            order.setStatus("20");
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
            for(int j=0 ;j<items.size();j++ ){
                TOrderDaliyPlanItem entry = items.get(j);
                TSsmReqGoodsOrderItem item = new TSsmReqGoodsOrderItem();
                item.setFlag("1");
                item.setUnit(entry.getUnit());
                item.setOrderDate(today);
                item.setItemNo((j+1) * 10);
                item.setOrderNo(order.getOrderNo());
                item.setMatnr(entry.getMatnr());
                item.setMatnrTxt(entry.getMatnrTxt());
                item.setQty(entry.getQty());
                item.setIncreQty(0);
                this.tSsmReqGoodsOrderItemMapper.insertRequireOrderItem(item);
            }
        }else{
            throw new ServiceException(MessageCode.LOGIC_ERROR,"今天该奶站没有可以生成要货计划的行项目");
        }
        //查询出今天的要货计划
        return this.searchRequireOrder(today);

    }

    /**
     * 根据 日期 获取当前登录人所在奶站的 要货计划
     * @param orderDate
     * @return
     */
    @Override
    public RequireOrderModel searchRequireOrder(Date  orderDate) {
        if(orderDate == null){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"查询要货计划的日期不能为空");
        }
        TSysUser user = userSessionService.getCurrentUser();
        RequireOrderModel orderModel = new RequireOrderModel();
        RequireOrderSearch rModel = new RequireOrderSearch();

        String salesOrg = user.getSalesOrg();
        rModel.setBranchNo(user.getBranchNo());
        rModel.setOrderDate(orderDate);
        TSsmReqGoodsOrder order  = this.tSsmReqGoodsOrderMapper.searchRequireOrder(rModel);
        if(order!= null){
            orderModel.setStatus(order.getStatus());
            orderModel.setRequiredDate(order.getOrderDate());
            orderModel.setBranchNo(order.getBranchNo());
            orderModel.setOrderNo(order.getOrderNo());
            orderModel.setOrderDate(order.getOrderDate());
            if(StringUtils.isNotBlank(order.getVoucherNo())){
                orderModel.setVoucherNo(order.getVoucherNo());
            }
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
                entry.setFlag(item.getFlag());
                entry.setIncreQty(item.getIncreQty());
                entries.add(entry);
            }
            orderModel.setEntries(entries);
        }else{
            throw new ServiceException(MessageCode.LOGIC_ERROR,"当天的要货计划还未生成");
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

        if(StringUtils.isBlank(item.getUnit())){
            Map<String,String> map = new HashMap<String,String>();
            map.put("salesOrg",user.getSalesOrg());
            map.put("matnr",item.getMatnr());
            TMdMara mara = tMdMaraMapper.selectProductByCode(map);
            item.setUnit(mara.getBaseUnit());
        }
        item.setFlag("2");
        item.setItemNo(tSsmReqGoodsOrderItemMapper.getMaxItemNoByOrderNo(orderNo)+10);
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
    public String sendRequireOrderToERP() {
        String result = "";
        Date today = new Date();
        TSysUser user = userSessionService.getCurrentUser();
        String salesOrg =user.getSalesOrg();
        String branchNo = user.getBranchNo();
        RequireOrderSearch search = new RequireOrderSearch();
        search.setOrderDate(today);
        search.setBranchNo(branchNo);
        TSsmReqGoodsOrder order = tSsmReqGoodsOrderMapper.searchRequireOrder(search);
        String errorMessage = "";
        if(order == null){
            errorMessage = "今天的要货计划还未创建";
            throw  new ServiceException(MessageCode.LOGIC_ERROR,errorMessage);
        }else{
            if( "30".equals(order.getStatus())){
                errorMessage = "今天的要货计划已发送至ERP";
                throw  new ServiceException(MessageCode.LOGIC_ERROR,errorMessage);
            }
            TMdBranch branch = branchMapper.selectBranchByNo(branchNo);

            //自营奶站
            if("01".equals(branch.getBranchGroup())){
                PISuccessMessage message =  piRequireOrderService.generateRequireOrder(order);
                order.setVoucherNo(message.getData());
                if(message.isSuccess()){

                    if(!uptRequireOrderAndDayOrderStatus(order,user)){
                        errorMessage ="修改要货计划或日订单状态失败" ;
                        throw  new ServiceException(MessageCode.LOGIC_ERROR,errorMessage);
                    }
                    result = message.getData();
                }else{
                    throw  new ServiceException(MessageCode.LOGIC_ERROR,"自营奶站发送要货计划失败，请重新发送");
                }
            }else{
                //经销商奶站 生成销售订单
                this.creatNoPromoSalOrderOfDealerBranch(today);
                this.creatPromoSalOrderOfDealerBranch(today);
                order.setLastModified(new Date());
                order.setLastModifiedBy(user.getLoginName());
                order.setLastModifiedByTxt(user.getDisplayName());
                order.setStatus("30");
                tSsmReqGoodsOrderMapper.uptRequireGoodsModifyInfo(order);
            }


       /*    PISuccessMessage message =  piRequireOrderService.generateRequireOrder(order);
           *//* PISuccessMessage message = new PISuccessMessage();
            message.setSuccess(Boolean.TRUE);
            message.setData(PrimaryKeyUtils.generateUuidKey());
            message.setMessage("成功");*//*
            //如果成功
            if(message.isSuccess()){
                order.setVoucherNo(message.getData());
                //直营奶站
                if("01".equals(branch.getBranchGroup())){
                     //修改要货计划状态为已确认状态 并 同步修改订户日订单状态为已确认
                    if(!uptRequireOrderAndDayOrderStatus(order,user)){
                        errorMessage ="修改要货计划或日订单状态失败" ;
                        throw  new ServiceException(MessageCode.LOGIC_ERROR,errorMessage);
                    }
                }else{
                    //要货计划单状态为“确认”，将不能修改
                    order.setLastModified(new Date());
                    order.setLastModifiedBy(user.getLoginName());
                    order.setLastModifiedByTxt(user.getDisplayName());
                    order.setStatus("30");
                    tSsmReqGoodsOrderMapper.uptRequireGoodsModifyInfo(order);
                    *//*上订户系统的大商/小商奶站  自动生成以奶站为单位的销售订单(产品要重新统计，根据产品参加N个促销活动  生成N+1 个销售单 )
                    * 其中 产品增量部分 为不参加促销活动的产品 即 放至 在 1 那个单子中
                    * *//*
                    this.creatNoPromoSalOrderOfDealerBranch(today);
                    this.creatPromoSalOrderOfDealerBranch(today);
                }

            }else{
                throw  new ServiceException(MessageCode.REQUEST_NOT_FOUND,message.getMessage());
            }*/
        }
        return result;
    }

    /**
     * 根据日订单  生成经销商奶站的  参加促销的销售订单
     *
     * @return
     */

    @Override
    public int creatPromoSalOrderOfDealerBranch(Date today) {
        TSysUser user = userSessionService.getCurrentUser();
        RequireOrderSearch rModel = new RequireOrderSearch();
        rModel.setFirstDay(DateUtil.getTomorrow(today));
        rModel.setSecondDay(DateUtil.getDayAfterTomorrow(today));
        rModel.setBranchNo(user.getBranchNo());
        rModel.setSalesOrg(user.getSalesOrg());

        List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectProDayPlanOfDealerBranch(rModel);
        if(items !=null && items.size()>0){
            TSsmSalOrder order = createSaleOrder(user,today,"dealer",null);
            for(int i = 1; i <=items.size();i++){
                TOrderDaliyPlanItem item = items.get(i-1);
                createSaleOrderItem(item,i,order.getOrderNo(),today,"dealer");
            }
            //调用 接口
            PISuccessMessage  message  = piRequireOrderService.generateSalesOrder(order,order.getDealerNo(),order.getBranchNo(),order.getSalesOrg(),null);
            if(message.isSuccess()){
                this.uptVouCherNoByOrderNo(order.getOrderNo(),message.getData());
            }
        }


        /*List<String> promotionNolist = tOrderDaliyPlanItemMapper.getDailOrderPromOfDealerBranch(rModel);
        if(promotionNolist !=null && promotionNolist.size()>0){
            for(String promotion : promotionNolist){
                //创建一份 销售订单
                TSsmSalOrder order = createSaleOrder(user,today,"dealer",null);
                rModel.setPromotion(promotion);
                List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectProDayPlanOfDealerBranch(rModel);
                if(items !=null && items.size()>0){
                    for(int i = 1; i <=items.size();i++){
                        TOrderDaliyPlanItem item = items.get(i-1);
                        createSaleOrderItem(item,i,order.getOrderNo(),today,"dealer");
                    }
                }
                //调用 接口

              PISuccessMessage  message  = piRequireOrderService.generateSalesOrder(order,order.getDealerNo(),order.getBranchNo(),order.getSalesOrg(),promotion);
                if(message.isSuccess()){
                    this.uptVouCherNoByOrderNo(order.getOrderNo(),message.getData());
                }

            }
        }*/

        return 1;
    }

    /**
     *  经销商奶站  生成 requiredDate 日期的  不参加促销的销售订单
     * @param requiredDate
     * @return
     */
    @Override
    public int creatNoPromoSalOrderOfDealerBranch(Date requiredDate) {
        TSysUser user = userSessionService.getCurrentUser();
        RequireOrderSearch rModel = new RequireOrderSearch();
        rModel.setFirstDay(DateUtil.getTomorrow(requiredDate));
        rModel.setSecondDay(DateUtil.getDayAfterTomorrow(requiredDate));
        rModel.setBranchNo(user.getBranchNo());
        rModel.setSalesOrg(user.getSalesOrg());
        List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectNoProDayPlanOfDealerBranch(rModel);
        if(items!=null && items.size()>0){
            //生成 促销订单
            TSsmSalOrder order  = createSaleOrder(user,requiredDate,"dealer","");
            for(int i=0 ;i<items.size();i++){
                TOrderDaliyPlanItem item = items.get(i);
                //生成 促销订单行项目
                createSaleOrderItem(item,i+1,order.getOrderNo(),requiredDate,"dealer");
            }
            //调用 接口
            PISuccessMessage  message  = piRequireOrderService.generateSalesOrder(order,order.getDealerNo(),order.getBranchNo(),order.getSalesOrg(),"");
            if(message.isSuccess()){
                this.uptVouCherNoByOrderNo(order.getOrderNo(),message.getData());
            }else{
                throw new ServiceException(MessageCode.LOGIC_ERROR,"发送失败");
            }
        }
        return 1;
    }




    /**
     * 自营奶站   创建 不参加促销的销售订单
     * @param requiredDate
     * @return
     */

    @Override
    public int creatNoPromoSalOrderOfSelftBranch(Date requiredDate) {
        TSysUser user = userSessionService.getCurrentUser();
        RequireOrderSearch rModel = new RequireOrderSearch();
        rModel.setRequiredDate(requiredDate);
        rModel.setBranchNo(user.getBranchNo());
        rModel.setSalesOrg(user.getSalesOrg());
        List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectNoProDayPlanOfSelfBranch(rModel);
        if(items!=null && items.size()>0){
            //生成 促销订单
            TSsmSalOrder order = createSaleOrder(user,requiredDate,"branch","");

            for(int i=0 ;i<items.size();i++){
                TOrderDaliyPlanItem item = items.get(i);
                //生成 促销订单行项目
                createSaleOrderItem(item,i+1,order.getOrderNo(),requiredDate,"branch");
            }
            //调用 接口
            PISuccessMessage  message  = piRequireOrderService.generateSalesOrder(order,order.getDealerNo(),order.getBranchNo(),order.getSalesOrg(),"");
            if(message.isSuccess()){
                this.uptVouCherNoByOrderNo(order.getOrderNo(),message.getData());

            }else{
                throw new ServiceException(MessageCode.LOGIC_ERROR,"创建销售订单失败,请联系开发");
            }
            return 1;
        }else{
            return 0;
        }

    }


    /**
     * 自营奶站   创建 参加促销的销售订单
     * @param requiredDate
     * @return
     */
    @Override
    public int creatPromoSalOrderOfSelftBranch(Date requiredDate) {
        TSysUser user = userSessionService.getCurrentUser();
        RequireOrderSearch rModel = new RequireOrderSearch();
        rModel.setRequiredDate(requiredDate);
        rModel.setBranchNo(user.getBranchNo());
        rModel.setSalesOrg(user.getSalesOrg());

                List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectProDayPlanOfSelfBranch(rModel);
                if(items!=null && items.size()>0){
                    TSsmSalOrder order = createSaleOrder(user,requiredDate,"branch","free");
                    if(items !=null && items.size()>0){
                        for(int i = 1; i <=items.size();i++){
                            TOrderDaliyPlanItem item = items.get(i-1);
                            createSaleOrderItem(item,i,order.getOrderNo(),requiredDate,"branch");
                        }
                    }
                    //调用 接口
                    PISuccessMessage  message  = piRequireOrderService.generateSalesOrder(order,order.getDealerNo(),order.getBranchNo(),order.getSalesOrg(),"");
                    if(message.isSuccess()){
                        this.uptVouCherNoByOrderNo(order.getOrderNo(),message.getData());
                    }else{
                        throw new ServiceException(MessageCode.LOGIC_ERROR,"创建销售订单失败,请联系开发");
                    }
                    return 1;
                }else{
                    return 0;
                }

       /* List<String> promotionNolist = tOrderDaliyPlanItemMapper.getDailOrderPromOfSelfBranch(rModel);
        if(promotionNolist !=null && promotionNolist.size()>0){
            for(String promotion : promotionNolist){
                //创建一份 销售订单
                TSsmSalOrder order = createSaleOrder(user,requiredDate,"branch",promotion);
                rModel.setPromotion(promotion);
                List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectProDayPlanOfSelfBranch(rModel);
                if(items !=null && items.size()>0){
                    for(int i = 1; i <=items.size();i++){
                        TOrderDaliyPlanItem item = items.get(i-1);
                        createSaleOrderItem(item,i,order.getOrderNo(),requiredDate,"branch");

                    }
                }
                //调用 接口
                PISuccessMessage  message  = piRequireOrderService.generateSalesOrder(order,order.getDealerNo(),order.getBranchNo(),order.getSalesOrg(),promotion);
                if(message.isSuccess()){
                    this.uptVouCherNoByOrderNo(order.getOrderNo(),message.getData());
                }
            }

        }*/

    }


    /**
     * 自营奶站创建  销售订单
     * 根据已确认的路单生成
     * @return
     */
 /* @Override
    public List<TSsmSalOrder> creaSalOrderOfSelftBranch() {
        TSysUser user = userSessionService.getCurrentUser();
        TMdBranch branch  = branchMapper.selectBranchByNo(user.getBranchNo());
        SalOrderModel sMode = new SalOrderModel();
        sMode.setOrderDate(new Date());
        List<TSsmSalOrder> result = this.getSaleOrderByQueryDate(sMode);
        if(result!=null && result.size()>0){
           *//* String orderNo = result.get(0).getOrderNo();
            tSsmSalOrderMapper.delSalOrderByOrderNo(orderNo);
            tSsmSalOrderItemMapper.delSalOrderItemsByOrderNo(orderNo);*//*
            throw new ServiceException(MessageCode.LOGIC_ERROR,"该奶站今天已经创建过销售订单,请直接查询");
        }

         Date today = new Date();
         if("01".equals(branch.getBranchGroup())){
             int noprom = this.creatNoPromoSalOrderOfSelftBranch(today);
             int prom = this.creatPromoSalOrderOfSelftBranch(today);
             if(noprom + prom == 0){
                 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                 throw new ServiceException(MessageCode.LOGIC_ERROR,"该奶站没有  "+sdf.format(today)+"  确认的路单");
             }
             return this.getSaleOrderByQueryDate(sMode);
          }else{
              throw new ServiceException(MessageCode.LOGIC_ERROR,"该奶站不是自营奶站");
          }

    }*/

    /**
     * 经销商奶站创建 销售订单
     * 根据已生成的日订单 生成
     * @return
     */
    @Override
    public List<TSsmSalOrder> creaSalOrderOfDealerBranch() {
        SalOrderModel sMode = new SalOrderModel();
        sMode.setOrderDate(new Date());
        List<TSsmSalOrder> result = this.getSaleOrderByQueryDate(sMode);
        if(result!=null && result.size()>0){
            String orderNo = result.get(0).getOrderNo();
            tSsmSalOrderMapper.delSalOrderByOrderNo(orderNo);
            tSsmSalOrderItemMapper.delSalOrderItemsByOrderNo(orderNo);
           // throw new ServiceException(MessageCode.LOGIC_ERROR,"该奶站已经创建过销售订单");
        }
        TSysUser user = userSessionService.getCurrentUser();
        TMdBranch branch  = branchMapper.selectBranchByNo(user.getBranchNo());
        Date today = new Date();
        if("02".equals(branch.getBranchGroup())){
            int noProm = this.creatNoPromoSalOrderOfDealerBranch(today);
            int prom = this.creatPromoSalOrderOfDealerBranch(today);
            return this.getSaleOrderByQueryDate(sMode);
        }else{
            throw new ServiceException(MessageCode.LOGIC_ERROR,"该奶站不是经销商奶站");
        }
    }

    /**
     * 获取指定日期下的 销售订单
     * @param sModel
     * @return
     */
    @Override
    public List<TSsmSalOrder> getSaleOrderByQueryDate(SalOrderModel sModel) {
        TSysUser user = userSessionService.getCurrentUser();
        String branchNo = user.getBranchNo();
        if(branchNo == null){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"该用户不存在奶站");
        }
            sModel.setBranchNo(user.getBranchNo());
            sModel.setDealerNo(user.getDealerId());
        List<TSsmSalOrder> result = tSsmSalOrderMapper.selectSalOrderByDateAndBranchOrDealerNo(sModel);
        if(result == null ){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"今天的销售订单还没生成");
        }
        return result;
    }

    @Override
    public List<TSsmSalOrderItems> getSaleOrderDetailByOrderNo(String orderNo) {
        Map<String,String> map = new HashMap<String,String>();
        map.put("orderNo",orderNo);
        map.put("salesOrg",userSessionService.getCurrentUser().getSalesOrg());
        return tSsmSalOrderItemMapper.selectItemsBySalOrderNo(map);
    }

    /**
     * 生成指定日期的要货计划
     * @param eSearch
     * @return
     */
    @Override
    public RequireOrderModel creatRequireOrderByDate(ReqGoodsOrderSearch eSearch) {
        Date orderDate = eSearch.getRequiredDate();
        RequireOrderSearch rModel = new RequireOrderSearch();
        Date requireDate = null;
        TSysUser user = userSessionService.getCurrentUser();
        //如果是华西或者天音 则requiredDate日期为今天  否则requiredDate为明天
        if("4181".equals(user.getSalesOrg()) || "4390".equals(user.getSalesOrg())){
            requireDate = orderDate;
        }else{
            requireDate = DateUtil.getTomorrow(new Date());
        }
        rModel.setBranchNo(user.getBranchNo());
        rModel.setOrderDate(orderDate);
        rModel.setRequiredDate(requireDate);
        rModel.setSalesOrg(user.getSalesOrg());
        TSsmReqGoodsOrder order = null;
        //首先查看今天的要货计划是否已存在
        order =  this.tSsmReqGoodsOrderMapper.searchRequireOrder(rModel);
        if(order !=null){
            tSsmReqGoodsOrderItemMapper.delRequireOrderItemsByOrderNo(order.getOrderNo());
            tSsmReqGoodsOrderMapper.deleRequireGoodsOrderbyNo(order.getOrderNo());
            // throw new ServiceException(MessageCode.LOGIC_ERROR,"当天要货计划已存在");
        }
        //查看明天和后天的订单
        rModel.setFirstDay(DateUtil.getTomorrow(orderDate));
        rModel.setSecondDay(DateUtil.getDayAfterTomorrow(orderDate));
        List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectDaliyPlansByBranchAndDay(rModel);
        //将i天后的日订单中符合的产品加入到 生成的要货计划
        if(items!=null && items.size()>0){
            order = new TSsmReqGoodsOrder();
            String orderNo = this.generateSal30Id();
            order.setRequiredDate(requireDate);
            order.setStatus("10");
            order.setOrderNo(orderNo);
            order.setOrderDate(orderDate);
            order.setBranchNo(user.getBranchNo());
            order.setCreateAt(orderDate);
            order.setCreateBy(user.getLoginName());
            order.setCreateByTxt(user.getDisplayName());
            order.setLastModified(orderDate);
            order.setLastModifiedByTxt(user.getDisplayName());
            order.setLastModifiedBy(user.getLoginName());
            tSsmReqGoodsOrderMapper.insertRequireOrder(order);
            for(int j=0 ;j<items.size();j++ ){
                TOrderDaliyPlanItem entry = items.get(j);
                TSsmReqGoodsOrderItem item = new TSsmReqGoodsOrderItem();
                item.setFlag("1");
                item.setUnit(entry.getUnit());
                item.setOrderDate(orderDate);
                item.setItemNo((j+1) * 10);
                item.setOrderNo(order.getOrderNo());
                item.setMatnr(entry.getMatnr());
                item.setMatnrTxt(entry.getMatnrTxt());
                item.setQty(entry.getQty());
                item.setIncreQty(0);
                this.tSsmReqGoodsOrderItemMapper.insertRequireOrderItem(item);
            }
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            throw new ServiceException(MessageCode.LOGIC_ERROR,"该奶站没有"+sdf.format(orderDate)+" 可以生成要货计划的行项目");
        }
        //查询出今天的要货计划
        return this.searchRequireOrder(orderDate);
    }

    /**
     * 自营奶站 根据已确认路单  和  内部销售订单 生成销售订单
     * 创建前 判断是否已经生成 -> 判断今天的路单是否已经生成 -> 判断今天的路单是否已经全部确认(否则给出提示)
     * @param search
     * @return
     */
    @Override
    public List<TSsmSalOrder> creaSalOrderOfSelftBranchByDate(SalOrderDaySearch search) {
        TSysUser user = userSessionService.getCurrentUser();
        TMdBranch branch  = branchMapper.selectBranchByNo(user.getBranchNo());
        SalOrderModel sMode = new SalOrderModel();
        sMode.setOrderDate(search.getOrderDate());

        List<TSsmSalOrder> result = this.getSaleOrderByQueryDate(sMode);
        if(result!=null && result.size()>0){
           /* String orderNo = result.get(0).getOrderNo();
            tSsmSalOrderMapper.delSalOrderByOrderNo(orderNo);
            tSsmSalOrderItemMapper.delSalOrderItemsByOrderNo(orderNo);*/
            throw new ServiceException(MessageCode.LOGIC_ERROR,"该奶站今天已经创建过销售订单,请直接查询");
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        //判断今天的路单是否已经生成
        List<TDispOrder>  dispOrders = tDispOrderMapper.selectDispOrderByBranchNoAndDay(user.getBranchNo(),search.getOrderDate());
        if(dispOrders == null || dispOrders.size() == 0){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"该奶站今天的路单还没生成，请先生成路单并全部确认后再生成");
        }else{
            //判断今天的路单是否已经全部确认
            List<TDispOrder>  confirmDispOrders = tDispOrderMapper.selectConfirmDispOrderByBranchNoAndDay(user.getBranchNo(),search.getOrderDate());
            if(confirmDispOrders!=null){
                if(confirmDispOrders.size() < dispOrders.size()){
                    throw new ServiceException(MessageCode.LOGIC_ERROR,"该奶站"+sdf.format(search.getOrderDate())+"的路单还有"+(dispOrders.size() - confirmDispOrders.size())+"个路单没确认，请全部确认后再生成");
                }
            }else{
                throw new ServiceException(MessageCode.LOGIC_ERROR,"该奶站"+sdf.format(search.getOrderDate())+"的路单所有的路单都没确认，请全部确认后再生成");
            }
        }
        if("01".equals(branch.getBranchGroup())){
            int noprom = this.creatNoPromoSalOrderOfSelftBranch(search.getOrderDate());
            int prom = this.creatPromoSalOrderOfSelftBranch(search.getOrderDate());
            //TODO  这段应该走不到了
            if(noprom + prom == 0){
                throw new ServiceException(MessageCode.LOGIC_ERROR,"该奶站没有  "+sdf.format(search.getOrderDate())+"确认的路单");
            }
            return this.getSaleOrderByQueryDate(sMode);
        }else{
            throw new ServiceException(MessageCode.LOGIC_ERROR,"该奶站不是自营奶站");
        }
    }


    /**
     *添加  销售订单行项目
     * @param item          产品code，产品数量
     * @param i             用来生成itemNo
     * @param orderNo       销售单号
     * @param requiredDate  日期
     * @param type          如果type=dealer 则为经销商订单行项目，否则为自营奶站订单行项目
     */
    private void createSaleOrderItem(TOrderDaliyPlanItem item, int i, String orderNo,Date requiredDate,String type) {
        TSsmSalOrderItems salOrderItems = new TSsmSalOrderItems();
        salOrderItems.setOrderNo(orderNo);
        salOrderItems.setOrderDate(requiredDate);
        salOrderItems.setQty(item.getQty());
        if(StringUtils.isNotBlank(item.getPlanItemNo())){
            salOrderItems.setPromNo(item.getPlanItemNo());
        }
        if("dealer".equals(type)){
            salOrderItems.setMatnr(item.getMatnr());
            salOrderItems.setType("10");
        }else{
            salOrderItems.setMatnr(item.getConfirmMatnr());
            salOrderItems.setRefMatnr(item.getMatnr());
            salOrderItems.setType("20");
        }

        salOrderItems.setItemNo(i*10);
        tSsmSalOrderItemMapper.addSalOrderItem(salOrderItems);

    }

    /**
     *  创建一个销售订单
     * @param user              当前用户
     * @param requiredDate      日期
     * @param type              类型（dealer为经销商销售订单  branch 为自营奶站销售订单）
     * @param promotion         如果促销号不为空，则该销售订单为一个参加促销的销售订单，否则为正品促销订单
     * @return
     */
    private TSsmSalOrder  createSaleOrder(TSysUser user, Date requiredDate, String type, String promotion) {
        TSsmSalOrder order = new TSsmSalOrder();

        String orderNo = this.generateSal35Id();
        order.setOrderNo(orderNo);
        order.setSalesOrg(user.getSalesOrg());
        order.setRequiredDate(requiredDate);
        order.setBranchNo(user.getBranchNo());
        if("dealer".equals(type)){
            TMdBranch branch = branchMapper.selectBranchByNo(user.getBranchNo());
            order.setDealerNo(branch.getDealerNo());
            order.setBranchGroup("10");
        }else{
            order.setDealerNo(user.getBranchNo());
            order.setBranchGroup("20");
        }
        if("free".equals(promotion)){
            order.setFreeFlag("Y");
        }else{
            order.setFreeFlag("N");
        }
        order.setOrderDate(requiredDate);
        order.setCreateAt(requiredDate);
        order.setCreateByTxt(user.getDisplayName());
        order.setCreateBy(user.getLoginName());
        if(StringUtils.isNoneBlank(promotion)){
            order.setPromNo(promotion);
        }
        tSsmSalOrderMapper.addsalOrder(order);
        return order;
    }

    private boolean  uptRequireOrderAndDayOrderStatus(TSsmReqGoodsOrder order,TSysUser user) {
            //更新要货计划状态为确认
        order.setLastModified(new Date());
        order.setLastModifiedBy(user.getLoginName());
        order.setLastModifiedByTxt(user.getDisplayName());
        order.setStatus("30");
        tSsmReqGoodsOrderMapper.uptRequireGoodsModifyInfo(order);
        String branchNo = order.getBranchNo();
        String salesOrg = user.getSalesOrg();
        Date requiredDate = order.getRequiredDate();
        //更新后两天配送的订户日订单
        for(int i=1;i<=2;i++){
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(requiredDate);
            calendar.add(calendar.DATE,i);//把日期往后增加i天.整数往后推 这个时间就是日期往后推一天的结果
            Date orderDate = calendar.getTime();
            //更近orderDate时的产品
            RequireOrderSearch search = new RequireOrderSearch();
            search.setBranchNo(branchNo);
            search.setPreDays(i);
            search.setSalesOrg(salesOrg);
            search.setRequiredDate(orderDate);
            List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectDaliyPlansByBranchAndDay(search);
            if(items!=null && items.size()>0){
                for(TOrderDaliyPlanItem item : items){
                    item.setStatus("20");
                    tOrderDaliyPlanItemMapper.updateDaliyPlanItemStatus(item);
                }
            }

        }


        return true;
    }


    public void uptVouCherNoByOrderNo(String orderNo,String voucherNo){
        Map<String,String> map = new HashMap<String,String>();
        map.put("orderNo",orderNo);
        map.put("voucherNo",voucherNo);
        tSsmSalOrderMapper.uptVouCherNoByOrderNo(map);
    }

    /**
     * 销售订单编号 35位
     * @return
     */
    private String generateSal35Id(){
        TMdBranch branch = branchMapper.getBranchByNo(userSessionService.getCurrentUser().getBranchNo());
        StringBuilder uuid = new StringBuilder();
        uuid.append("DH001");
        uuid.append("A");
        uuid.append(branch.getCompanyCode());
        String branchNo = branch.getBranchNo();
        uuid.append(branchNo.substring(1));
        uuid.append(format.format(new Date()));
        uuid.append(randomNum);
        return uuid.toString();
    }
    /**
     * 要货单编号  30位
     * @return
     */
    private String generateSal30Id(){
        TMdBranch branch = branchMapper.getBranchByNo(userSessionService.getCurrentUser().getBranchNo());
        StringBuilder uuid = new StringBuilder();
        uuid.append("DH001");
        uuid.append("B");
        uuid.append(branch.getCompanyCode());
        if(org.apache.commons.lang.StringUtils.isEmpty(branch.getLgort())){
            uuid.append(branch.getSalesOrg());
        }else {
            uuid.append(branch.getLgort());
        }
        uuid.append(format.format(new Date()));
        uuid.append(randomNum);
        return uuid.toString();
    }

}
