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
import com.nhry.data.milk.dao.TMstRefuseResendItemMapper;
import com.nhry.data.milk.dao.TMstRefuseResendMapper;
import com.nhry.data.milk.domain.TDispOrder;
import com.nhry.data.milktrans.dao.TSsmReqGoodsOrderItemMapper;
import com.nhry.data.milktrans.dao.TSsmReqGoodsOrderMapper;
import com.nhry.data.milktrans.dao.TSsmSalOrderItemMapper;
import com.nhry.data.milktrans.dao.TSsmSalOrderMapper;
import com.nhry.data.milktrans.domain.*;
import com.nhry.data.order.dao.TOrderDaliyPlanItemMapper;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.data.stock.dao.TSsmGiOrderItemMapper;
import com.nhry.data.stock.dao.TSsmGiOrderMapper;
import com.nhry.data.stock.domain.TSsmGiOrder;
import com.nhry.model.milktrans.*;
import com.nhry.service.milktrans.dao.RequireOrderService;
import com.nhry.service.pi.dao.PIRequireOrderService;
import com.nhry.service.stock.dao.TSsmStockService;
import com.nhry.utils.DateUtil;
import com.nhry.webService.client.PISuccessMessage;
import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by gongjk on 2016/6/24.
 */
public class RequireOrderServiceImpl implements RequireOrderService {
    private TSsmReqGoodsOrderItemMapper tSsmReqGoodsOrderItemMapper;
    private TSsmReqGoodsOrderMapper tSsmReqGoodsOrderMapper;
    private TOrderDaliyPlanItemMapper tOrderDaliyPlanItemMapper;
    private TDispOrderMapper tDispOrderMapper;
    private UserSessionService userSessionService;
    private PIRequireOrderService piRequireOrderService;
    private TMdBranchMapper branchMapper;
    private TMdMaraMapper tMdMaraMapper;
    private TSsmSalOrderMapper tSsmSalOrderMapper;
    private TSsmSalOrderItemMapper tSsmSalOrderItemMapper;
    private TSsmGiOrderMapper tSsmGiOrderMapper;
    private TSsmGiOrderItemMapper tSsmGiOrderItemMapper;
    private TMstRefuseResendMapper resendMapper;
    private TMstRefuseResendItemMapper resendItemMapper;
    private TSsmStockService stockService;

    public void setStockService(TSsmStockService stockService) {
        this.stockService = stockService;
    }

    public void setResendItemMapper(TMstRefuseResendItemMapper resendItemMapper) {
        this.resendItemMapper = resendItemMapper;
    }

    public void setResendMapper(TMstRefuseResendMapper resendMapper) {
        this.resendMapper = resendMapper;
    }

    public void settSsmGiOrderItemMapper(TSsmGiOrderItemMapper tSsmGiOrderItemMapper) {
        this.tSsmGiOrderItemMapper = tSsmGiOrderItemMapper;
    }

    public void settSsmGiOrderMapper(TSsmGiOrderMapper tSsmGiOrderMapper) {
        this.tSsmGiOrderMapper = tSsmGiOrderMapper;
    }

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

  /*  @Override
    public RequireOrderModel creatRequireOrder() {
        RequireOrderSearch rModel = new RequireOrderSearch();
        Date today = new Date();
        Date requireDate = null;
        TSysUser user = userSessionService.getCurrentUser();
        //如果是华西或者天音 则requiredDate日期为今天  否则requiredDate为明天
        if ("4181".equals(user.getSalesOrg()) || "4390".equals(user.getSalesOrg())) {
            requireDate = today;
        } else {
            requireDate = DateUtil.getTomorrow(today);
        }
        rModel.setBranchNo(user.getBranchNo());
        rModel.setOrderDate(today);
        rModel.setRequiredDate(requireDate);
        rModel.setSalesOrg(user.getSalesOrg());
        TSsmReqGoodsOrder order = null;
        //首先查看今天的要货计划是否已存在
        order = this.tSsmReqGoodsOrderMapper.searchRequireOrder(rModel);
        if (order != null) {
            if ("30".equals(order.getStatus())) {
                //如果已生成，并且已经发送过ERP
                throw new ServiceException(MessageCode.LOGIC_ERROR, "当天要货计划已生成，并且已经发送过ERP，不能再次生成，请查阅!!!");
            } else {
                tSsmReqGoodsOrderItemMapper.delRequireOrderItemsByOrderNo(order.getOrderNo());
                tSsmReqGoodsOrderMapper.deleRequireGoodsOrderbyNo(order.getOrderNo());
            }


        }
        //查看明天和后天的订单
        rModel.setFirstDay(DateUtil.getTomorrow(today));
        rModel.setSecondDay(DateUtil.getDayAfterTomorrow(today));
        List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectDaliyPlansByBranchAndDay(rModel);
        //将i天后的日订单中符合的产品加入到 生成的要货计划
        if (items != null && items.size() > 0) {
            order = new TSsmReqGoodsOrder();
            String orderNo = this.generateSal30Id();
            order.setRequiredDate(requireDate);
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
            for (int j = 0; j < items.size(); j++) {
                TOrderDaliyPlanItem entry = items.get(j);
                TSsmReqGoodsOrderItem item = new TSsmReqGoodsOrderItem();
                item.setFlag("1");
                item.setUnit(entry.getUnit());
                item.setOrderDate(today);
                item.setItemNo((j + 1) * 10);
                item.setOrderNo(order.getOrderNo());
                item.setMatnr(entry.getMatnr());
                item.setMatnrTxt(entry.getMatnrTxt());
                item.setQty(entry.getQty());
                item.setIncreQty(0);
                this.tSsmReqGoodsOrderItemMapper.insertRequireOrderItem(item);
            }
        } else {
            throw new ServiceException(MessageCode.LOGIC_ERROR, "今天该奶站没有可以生成要货计划的行项目");
        }
        //查询出今天的要货计划
        return this.searchRequireOrder(today);

    }*/

    /**
     * 根据 日期 获取当前登录人所在奶站的 要货计划
     *
     * @param orderDate
     * @return
     */
    @Override
    public RequireOrderModel searchRequireOrder(Date orderDate) {
        if (orderDate == null) {
            throw new ServiceException(MessageCode.LOGIC_ERROR, "查询要货计划的日期不能为空");
        }
        TSysUser user = userSessionService.getCurrentUser();
        RequireOrderModel orderModel = new RequireOrderModel();
        RequireOrderSearch rModel = new RequireOrderSearch();

        String salesOrg = user.getSalesOrg();
        rModel.setBranchNo(user.getBranchNo());
        rModel.setOrderDate(orderDate);
        TSsmReqGoodsOrder order = this.tSsmReqGoodsOrderMapper.searchRequireOrder(rModel);
        if (order != null) {

            List<TMstRefuseResend>  resendList = resendMapper.findNoUsedAndUsedRefuseResend(user.getBranchNo(),order.getOrderNo());
            Set<String> matnrMap = new HashSet<String>();
            if(resendList!=null && resendList.size()>0){
                resendList.stream().forEach(resend->{
                    matnrMap.add(resend.getMatnr());
                });
            }


            orderModel.setStatus(order.getStatus());
            orderModel.setRequiredDate(order.getOrderDate());
            orderModel.setBranchNo(order.getBranchNo());
            orderModel.setOrderNo(order.getOrderNo());
            orderModel.setOrderDate(order.getOrderDate());
            if (StringUtils.isNotBlank(order.getVoucherNo())) {
                orderModel.setVoucherNo(order.getVoucherNo());
            }
            List<TSsmReqGoodsOrderItem> items = this.tSsmReqGoodsOrderItemMapper.getReqGoodsItemsByOrderNo(order.getOrderNo());
            List<OrderRequireItem> entries = new ArrayList<OrderRequireItem>();
            for (TSsmReqGoodsOrderItem item : items) {
                OrderRequireItem entry = new OrderRequireItem();
                Map<String, String> map = new HashMap<String, String>();
                map.put("salesOrg", salesOrg);
                map.put("matnr", item.getMatnr());
                TMdMara mara = tMdMaraMapper.selectProductByCode(map);
                if(matnrMap.contains(item.getMatnr())){
                    entry.setHasTmp(true);
                }else{
                    entry.setHasTmp(false);
                }
                entry.setResendQty(item.getResendQty());
                entry.setMatnr(item.getMatnr());
                entry.setMatnrTxt(mara.getMatnrTxt());
                entry.setQty(item.getQty());
                entry.setFlag(item.getFlag());
                entry.setIncreQty(item.getIncreQty());
                entries.add(entry);
            }
            orderModel.setEntries(entries);
        } else {
            throw new ServiceException(MessageCode.LOGIC_ERROR, "当天的要货计划还未生成");
        }
        return orderModel;
    }

    /**
     * 修改新添加的要货计划行
     *
     * @param rModel
     * @return
     */
    @Override
    public int uptNewRequireOrderItem(UpdateNewRequiredModel rModel) {
        try {
            TSysUser user = userSessionService.getCurrentUser();
            String orderNo = rModel.getOrderNo();
            TSsmReqGoodsOrder orderModel = this.tSsmReqGoodsOrderMapper.getRequireOrderByNo(orderNo);
            //获取数据库中存好的要货计划 如果状态已经确定，则不能修改
            if (orderModel.getStatus() == "30") {
                throw new ServiceException(MessageCode.LOGIC_ERROR, "要货订单已确定，不能改变");
            }

            Map<String, String> map = new HashMap<String, String>();
            map.put("salesOrg", user.getSalesOrg());
            map.put("matnr", rModel.getMatnr());

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
        } catch (Exception e) {
            throw new ServiceException(MessageCode.LOGIC_ERROR, "修改失败");
        }

    }

    @Override
    public int addRequireOrderItem(TSsmReqGoodsOrderItem item) {
        String message = "";
        String orderNo = item.getOrderNo();
        TSsmReqGoodsOrderItem oldItem = tSsmReqGoodsOrderItemMapper.getReqGoodsItemsByMatnrAndOrderNo(orderNo, item.getMatnr());
        if (oldItem != null) {
            message = "该产品已存在，请重新选择";
            throw new ServiceException(MessageCode.LOGIC_ERROR, message);
        }


        TSysUser user = userSessionService.getCurrentUser();
        TSsmReqGoodsOrder orderModel = this.tSsmReqGoodsOrderMapper.getRequireOrderByNo(orderNo);

        if ("30".equals(orderModel.getStatus())) {
            message = "这天的要货计划已确定，不可再添加或修改";
            throw new ServiceException(MessageCode.LOGIC_ERROR, message);
        }
        orderModel.setLastModified(new Date());
        orderModel.setLastModifiedBy(user.getLoginName());
        orderModel.setLastModifiedByTxt(user.getDisplayName());
        tSsmReqGoodsOrderMapper.uptRequireGoodsModifyInfo(orderModel);

        if (StringUtils.isBlank(item.getUnit())) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("salesOrg", user.getSalesOrg());
            map.put("matnr", item.getMatnr());
            TMdMara mara = tMdMaraMapper.selectProductByCode(map);
            item.setUnit(mara.getBaseUnit());
        }
        item.setFlag("2");
        item.setItemNo(tSsmReqGoodsOrderItemMapper.getMaxItemNoByOrderNo(orderNo) + 10);
        item.setOrderDate(orderModel.getOrderDate());
        return tSsmReqGoodsOrderItemMapper.insertRequireOrderItem(item);
    }

    @Override
    public int delRequireOrderItem(ReqGoodsOrderItemSearch item) {
        TSsmReqGoodsOrderItem oldItem = tSsmReqGoodsOrderItemMapper.getReqGoodsItemsByMatnrAndOrderNo(item.getOrderNo(), item.getMatnr());
        if ("1".equals(oldItem.getFlag())) {
            throw new ServiceException(MessageCode.LOGIC_ERROR, "生成的要货计划行不能删除");
        }
        return tSsmReqGoodsOrderItemMapper.delRequireOrderItem(item);
    }


    @Override
    public int uptRequireOrder(UpdateRequiredModel uModel) {
        String message = "修改失败";
        try {
            String orderNo = uModel.getOrderNo();
            TSsmReqGoodsOrder orderModel = this.tSsmReqGoodsOrderMapper.getRequireOrderByNo(orderNo);
            //获取数据库中存好的要货计划 如果状态已经确定，则不能修改
            if (orderModel.getStatus() == "30") {
                message = "要货订单已确定，不能改变";
                throw new ServiceException(MessageCode.LOGIC_ERROR, message);
            }


            this.tSsmReqGoodsOrderItemMapper.uptReqGoodsItem(uModel);
            return 1;
        } catch (Exception e) {
            throw new ServiceException(MessageCode.LOGIC_ERROR, message);
        }
    }

    /**
     * 根据日订单  生成经销商奶站的  orderDate 日期的  参加促销的销售订单
     * 此时还没有发送
     * @param orderDate
     * @return
     */
    @Override
    public TSsmSalOrder creatPromoSalOrderOfDealerBranch(Date orderDate) {
        TSysUser user = userSessionService.getCurrentUser();
        RequireOrderSearch rModel = new RequireOrderSearch();
        rModel.setFirstDay(DateUtil.getTomorrow(orderDate));
        rModel.setSecondDay(DateUtil.getDayAfterTomorrow(orderDate));
        rModel.setBranchNo(user.getBranchNo());
        rModel.setSalesOrg(user.getSalesOrg());

        List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectProDayPlanOfDealerBranch(rModel);
        if (items != null && items.size() > 0) {
            TSsmSalOrder order = createSaleOrder(user, orderDate, "dealer", "free",2);
            for (int i = 1; i <= items.size(); i++) {
                TOrderDaliyPlanItem item = items.get(i - 1);
                createSaleOrderItem(item, i, order.getOrderNo(), orderDate, "dealer");
            }
            return order;
        }
        return null;
    }

    /**
     * 根据日订单 生成经销商奶站的  orderDate 日期的  不参加促销的销售订单
     * 此时还没有发送
     * @param orderDate
     * @return
     */
    @Override
    public TSsmSalOrder creatNoPromoSalOrderOfDealerBranch(Date orderDate) {
        TSysUser user = userSessionService.getCurrentUser();
        RequireOrderSearch rModel = new RequireOrderSearch();
        rModel.setFirstDay(DateUtil.getTomorrow(orderDate));
        rModel.setSecondDay(DateUtil.getDayAfterTomorrow(orderDate));
        rModel.setBranchNo(user.getBranchNo());
        rModel.setSalesOrg(user.getSalesOrg());
        rModel.setOrderDate(orderDate);
        List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectNoProDayPlanOfDealerBranch(rModel);
        if (items != null && items.size() > 0) {
            //生成 促销订单
            TSsmSalOrder order = createSaleOrder(user, orderDate, "dealer", "",1);
            for (int i = 0; i < items.size(); i++) {
                TOrderDaliyPlanItem item = items.get(i);
                //生成 促销订单行项目
                createSaleOrderItem(item, i + 1, order.getOrderNo(), orderDate, "dealer");
            }
            return order;
        }
        return null;
    }


    /**
     * 自营奶站   创建 不参加促销的销售订单
     * 此时还没有发送
     * @param orderDate
     * @return
     */

    @Override
    public TSsmSalOrder creatNoPromoSalOrderOfSelftBranch(Date orderDate) {
        TSysUser user = userSessionService.getCurrentUser();
        RequireOrderSearch rModel = new RequireOrderSearch();
        rModel.setOrderDate(orderDate);
        rModel.setBranchNo(user.getBranchNo());
        rModel.setSalesOrg(user.getSalesOrg());
        List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectNoProDayPlanOfSelfBranch(rModel);
        if (items != null && items.size() > 0) {
            //生成 促销订单
            TSsmSalOrder order = createSaleOrder(user, orderDate, "branch", "",1);

            for (int i = 0; i < items.size(); i++) {
                TOrderDaliyPlanItem item = items.get(i);
                //生成 促销订单行项目
                createSaleOrderItem(item, i + 1, order.getOrderNo(), orderDate, "branch");
            }
            return order;
        } else {
            return null;
        }

    }


    /**
     * 自营奶站   创建 参加促销的销售订单
     * 此时还没有发送
     * @param orderDate
     * @return
     */
    @Override
    public TSsmSalOrder creatPromoSalOrderOfSelftBranch(Date orderDate) {
        TSysUser user = userSessionService.getCurrentUser();
        RequireOrderSearch rModel = new RequireOrderSearch();
        rModel.setOrderDate(orderDate);
        rModel.setBranchNo(user.getBranchNo());
        rModel.setSalesOrg(user.getSalesOrg());
        List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectProDayPlanOfSelfBranch(rModel);
        if (items != null && items.size() > 0) {
            TSsmSalOrder order = createSaleOrder(user, orderDate, "branch", "free",2);
            if (items != null && items.size() > 0) {
                for (int i = 1; i <= items.size(); i++) {
                    TOrderDaliyPlanItem item = items.get(i - 1);
                    createSaleOrderItem(item, i, order.getOrderNo(), orderDate, "branch");
                }
            }
            return order;
        } else {
            return null;
        }
    }


    /**
     * 获取指定日期下的 销售订单
     *
     * @param sModel
     * @return
     */
    @Override
    public List<TSsmSalOrder> getSaleOrderByQueryDate(SalOrderModel sModel) {
        TSysUser user = userSessionService.getCurrentUser();
        String branchNo = user.getBranchNo();
        if (branchNo == null) {
            throw new ServiceException(MessageCode.LOGIC_ERROR, "该用户不存在奶站");
        }
        if(sModel.getBranchNo() == null){
            sModel.setBranchNo(user.getBranchNo());
        }
        List<TSsmSalOrder> result = tSsmSalOrderMapper.selectSalOrderByDateAndBranchNo(sModel);
        return result;
    }

    @Override
    public List<TSsmSalOrderItems> getSaleOrderDetailByOrderNo(String orderNo) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("orderNo", orderNo);
        map.put("salesOrg", userSessionService.getCurrentUser().getSalesOrg());
        return tSsmSalOrderItemMapper.selectItemsBySalOrderNo(map);
    }

    /**
     * 生成指定日期的要货计划
     *
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
        if ("4181".equals(user.getSalesOrg()) || "4390".equals(user.getSalesOrg())) {
            requireDate = orderDate;
        } else {
            requireDate = DateUtil.getTomorrow(orderDate);
        }
        rModel.setBranchNo(user.getBranchNo());
        rModel.setOrderDate(orderDate);
        rModel.setRequiredDate(requireDate);
        rModel.setSalesOrg(user.getSalesOrg());
        TSsmReqGoodsOrder order = null;
        //首先查看今天的要货计划是否已存在
        order = this.tSsmReqGoodsOrderMapper.searchRequireOrder(rModel);
        if (order != null) {
            if ("30".equals(order.getStatus())) {
                //如果已生成，并且已经发送过ERP
                throw new ServiceException(MessageCode.LOGIC_ERROR, "当天要货计划已生成，并且已经发送过ERP，不能再次生成，请查阅!!!");
            } else {
                //将 拒收复送  复原
                List<TMstRefuseResendItem> entries = resendItemMapper.selectItemByRequireOrder(order.getOrderNo());
                if(entries!=null && entries.size()>0){
                    //key  拒收复送单号，value 数量
                    Map<String, BigDecimal> matnrList = new HashMap<String,BigDecimal>();
                    entries.stream().filter(e->(e.getQty().intValue()>0)).forEach(e->{
                           matnrList.put(e.getResendOrderNo(),e.getQty());
                    });
                    //有拒收复送产品,更新拒收复送数量，删除子项
                    if(matnrList.size()>0){
                        for(String resendOrderNo : matnrList.keySet()){
                            BigDecimal uptQty = matnrList.get(resendOrderNo);
                            TMstRefuseResend resend = resendMapper.selectRefuseResendByNo(resendOrderNo);
                            resend.setConfirmQty(resend.getConfirmQty().subtract(uptQty));
                            resend.setRemainQty(resend.getRemainQty().add(uptQty));
                           resendMapper.uptRefuseResend(resend);
                        }
                        resendItemMapper.delResendItemByRequireOrderNoAndType(order.getOrderNo(),"10");
                    }
                }
                tSsmReqGoodsOrderItemMapper.delRequireOrderItemsByOrderNo(order.getOrderNo());
                tSsmReqGoodsOrderMapper.deleRequireGoodsOrderbyNo(order.getOrderNo());
            }
        }


        //查看明天和后天的订单
        rModel.setFirstDay(DateUtil.getTomorrow(orderDate));
        rModel.setSecondDay(DateUtil.getDayAfterTomorrow(orderDate));
        List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectDaliyPlansByBranchAndDay(rModel);
        //将i天后的日订单中符合的产品加入到 生成的要货计划
        if (items != null && items.size() > 0) {
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
            for (int j = 0; j < items.size(); j++) {

                TOrderDaliyPlanItem entry = items.get(j);

                TSsmReqGoodsOrderItem item = new TSsmReqGoodsOrderItem();

                item.setFlag("1");
                item.setUnit(entry.getUnit());
                item.setOrderDate(orderDate);
                item.setItemNo((j + 1) * 10);
                item.setOrderNo(order.getOrderNo());
                item.setMatnr(entry.getMatnr());
                item.setMatnrTxt(entry.getMatnrTxt());
                item.setQty(entry.getQty());
                item.setIncreQty(0);
                this.tSsmReqGoodsOrderItemMapper.insertRequireOrderItem(item);
            }
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            throw new ServiceException(MessageCode.LOGIC_ERROR, "该奶站没有" + sdf.format(orderDate) + " 可以生成要货计划的行项目");
        }
        //查询出今天的要货计划
        return this.searchRequireOrder(orderDate);
    }

    @Override
    public String sendRequireOrderToERPByDate(ReqGoodsOrderSearch eSearch) {
        String result = "";
        Date orderDate = eSearch.getRequiredDate();
        TSysUser user = userSessionService.getCurrentUser();
        String salesOrg = user.getSalesOrg();
        String branchNo = user.getBranchNo();
        RequireOrderSearch search = new RequireOrderSearch();
        search.setOrderDate(orderDate);
        search.setBranchNo(branchNo);
        TSsmReqGoodsOrder order = tSsmReqGoodsOrderMapper.searchRequireOrder(search);
        String errorMessage = "";
        if (order == null) {
            errorMessage = "今天的要货计划还未创建";
            throw new ServiceException(MessageCode.LOGIC_ERROR, errorMessage);
        } else {
            if ("30".equals(order.getStatus())) {
                errorMessage = "今天的要货计划已发送至ERP";
                throw new ServiceException(MessageCode.LOGIC_ERROR, errorMessage);
            }
            TMdBranch branch = branchMapper.selectBranchByNo(branchNo);
            //自营奶站
            if ("01".equals(branch.getBranchGroup())) {
                PISuccessMessage message = piRequireOrderService.generateRequireOrder(order);
                if (message.isSuccess()) {
                    order.setVoucherNo(message.getData());
                    if (!uptRequireOrderAndDayOrderStatus(order, user)) {
                        errorMessage = "修改要货计划或日订单状态失败";
                        throw new ServiceException(MessageCode.LOGIC_ERROR, errorMessage);
                    }
                    result = order.getVoucherNo();
                } else {
                    throw new ServiceException(MessageCode.LOGIC_ERROR, message.getMessage());
                }
            } else {
                //经销商奶站 生成销售订单 并发送
                this.creaSalOrderOfDealerBranchByDate(orderDate);
                order.setLastModified(new Date());
                order.setLastModifiedBy(user.getLoginName());
                order.setLastModifiedByTxt(user.getDisplayName());
                order.setStatus("30");
                tSsmReqGoodsOrderMapper.uptRequireGoodsModifyInfo(order);
            }
        }
        return result;
    }

    @Override
    public TSsmSalOrder creatNoPromoSalOrderAndSendOfSelftBranch(Date requiredDate) {
        TSsmSalOrder entry =  this.creatNoPromoSalOrderOfSelftBranch(requiredDate);
        generateSalesOrderAnduptVouCher(entry);
        return entry;
    }

    @Override
    public TSsmSalOrder creatPromoSalOrderAndSendOfSelftBranch(Date requiredDate) {
        TSsmSalOrder entry =  this.creatPromoSalOrderOfSelftBranch(requiredDate);
        generateSalesOrderAnduptVouCher(entry);
        return entry;
    }

    @Override
    public List<TSsmSalOrder> creaSalOrderOfSelftBranchByDate2(SalOrderDaySearch search) {
        TSysUser user = userSessionService.getCurrentUser();
        TMdBranch branch = branchMapper.selectBranchByNo(user.getBranchNo());
        Date orderDate = search.getOrderDate();
        RequireOrderSearch rModel = new RequireOrderSearch();
        rModel.setBranchNo(user.getBranchNo());
        rModel.setOrderDate(DateUtil.getYestoday(orderDate));
        rModel.setSalesOrg(user.getSalesOrg());
        //获取要的货是今天的要货计划
        TSsmReqGoodsOrder reqGoodsOrder = this.tSsmReqGoodsOrderMapper.searchRequireOrder(rModel);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        if(reqGoodsOrder == null || StringUtils.isBlank(reqGoodsOrder.getVoucherNo())){
            throw  new ServiceException(MessageCode.LOGIC_ERROR,"今天还没要货");
        }else{
            //判断要货计划对应的 交货单 是否生成 若没生成 提示还没生成
            List<TSsmGiOrder>  giOrders = tSsmGiOrderMapper.findGiOrderByReqOrderNo(reqGoodsOrder.getVoucherNo());
            if(giOrders == null || giOrders.size()<=0){
                throw  new ServiceException(MessageCode.LOGIC_ERROR,"该奶站" + sdf.format(search.getOrderDate()) +"的交货单还没有生成，请先获取交货单");
            }else{
                //判断所有的交货计划是否都已确认过
                if(giOrders.stream().anyMatch(
                        (e)->(!"30".equals(e.getStatus()))
                        )
                        ){
                    throw new ServiceException(MessageCode.LOGIC_ERROR,"含有未确认的交货单，请确认!");
                }


                SalOrderModel sMode = new SalOrderModel();
                sMode.setOrderDate(orderDate);
                sMode.setBranchNo(branch.getBranchNo());
                //查看今天销售订单
                List<TSsmSalOrder> result = tSsmSalOrderMapper.selectSalOrderByDateAndNo(sMode);
                if (result != null && result.size() > 0) {
                    Boolean flag = true;
                    for (TSsmSalOrder entry : result) {
                        if (StringUtils.isNotBlank(entry.getVoucherNo())){
                            continue;
                        }else{
                            flag = false;
                            generateSalesOrderAnduptVouCher(entry);
                        }
                    }
                    if(flag){
                        throw new ServiceException(MessageCode.LOGIC_ERROR, "该奶站今天已经创建所有销售订单,请直接查询");
                    }
                    return this.getSaleOrderByQueryDate(sMode);
                }else{

                    //判断今天的路单是否已经生成
                    List<TDispOrder> dispOrders = tDispOrderMapper.selectDispOrderByBranchNoAndDay(user.getBranchNo(), search.getOrderDate());
                    if (dispOrders == null || dispOrders.size() == 0) {
                        throw new ServiceException(MessageCode.LOGIC_ERROR, "该奶站" + sdf.format(search.getOrderDate()) +"的路单还没生成，请先生成路单并全部确认后再生成");
                    }else{
                        //判断今天的路单是否已经全部确认
                        List<TDispOrder> confirmDispOrders = tDispOrderMapper.selectConfirmDispOrderByBranchNoAndDay(user.getBranchNo(), search.getOrderDate());
                        if (confirmDispOrders != null) {
                            if (confirmDispOrders.size() < dispOrders.size()) {
                                throw new ServiceException(MessageCode.LOGIC_ERROR, "该奶站" + sdf.format(search.getOrderDate()) + "的路单还有" + (dispOrders.size() - confirmDispOrders.size()) + "个路单没确认，请全部确认后再生成");
                            }
                        } else {
                            throw new ServiceException(MessageCode.LOGIC_ERROR, "该奶站" + sdf.format(search.getOrderDate()) + "的路单所有的路单都没确认，请全部确认后再生成");
                        }
                    }

                    if ("01".equals(branch.getBranchGroup())) {
                        RequireOrderSearch   rsModel = new RequireOrderSearch();
                        rsModel.setBranchNo(user.getBranchNo());
                        rsModel.setSalesOrg(user.getSalesOrg());
                        rsModel.setOrderDate(orderDate);

                        //获取确认后的路单中的参加促销的产品
                        List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectProDayPlanOfSelfBranch(rsModel);
                        //获取交货单中的产品
                        rModel.setReqOrderNo(reqGoodsOrder.getVoucherNo());
                        List<TOrderDaliyPlanItem>  planItems = tSsmGiOrderItemMapper.selectNoProDayPlanOfSelfBranch(rModel);
                        Map<String,Integer>  entries =  new HashMap<String,Integer>();
                        if(planItems!=null && planItems.size()>0){
                            for(TOrderDaliyPlanItem planItem : planItems){
                                entries.put(planItem.getConfirmMatnr(),planItem.getQty());
                            }
                        }
                        TSsmSalOrder noprom = null;
                        TSsmSalOrder prom = null;
                        if (items!=null && items.size()>0) {
                                boolean  hasCreateOrder = false;
                                for (int i = 1; i <= items.size(); i++) {
                                    TOrderDaliyPlanItem item = items.get(i - 1);
                                    //如果交货单 产品 包含 促销产品 则做促销销售订单
                                    // 再判断 数量 如果促销数量 >=  交货单数量  则以交货单数量为准 并将交货单中产品去除
                                    //如果 促销数量 < 交货单数量  则以促销数量为准，  并将交货单中产品减去促销数量
                                    if(entries.containsKey(item.getConfirmMatnr())){
                                        //此时说明有促销产品要生成销售定单  （再判断是否已生成 如果还没则 先生成 并将标记为置为已生成过，保证只生成一个)
                                        if(!hasCreateOrder){
                                            prom = createSaleOrder(user, search.getOrderDate(), "branch", "free",2);
                                            hasCreateOrder = true;
                                        }
                                        if(item.getQty() >= entries.get(item.getConfirmMatnr())){
                                            item.setQty(entries.get(item.getConfirmMatnr()));
                                            entries.remove(item.getConfirmMatnr());
                                        }else{
                                            entries.replace(item.getConfirmMatnr(),entries.get(item.getConfirmMatnr()) - item.getQty());
                                        }
                                        createSaleOrderItem(item, i, prom.getOrderNo(), search.getOrderDate(), "branch");
                                    }else{
                                        continue;
                                    }

                                }

                              //生成 不参加促销
                               if(entries!=null && entries.size()>0){
                                   noprom = createSalOrderByGiOrderMap(entries,user,orderDate);
                               }
                        }else{
                            //生成 不参加促销
                            if(entries!=null && entries.size()>0){
                                noprom = createSalOrderByGiOrderMap(entries,user,orderDate);
                            }
                        }

                        if(noprom!=null){
                            generateSalesOrderAnduptVouCher(noprom);
                        }
                        if(prom!=null){
                            generateSalesOrderAnduptVouCher(prom);
                        }
                        return this.getSaleOrderByQueryDate(sMode);
                    } else {
                        throw new ServiceException(MessageCode.LOGIC_ERROR, "该奶站不是自营奶站");
                    }
                }
            }
        }
        //如果销售订单已存在，判断是否存在发送成功的 如果有 重新发送，如果没有 则提示已经创建所有的销售订单，请直接查询
    }

    @Override
    public List<TMstRefuseResend>  queryRefuseResendByMatnr(String matnr,String reqOrderNo) {
        TSysUser user = userSessionService.getCurrentUser();
        return resendMapper.queryRefuseResendByMatnr(matnr,user.getBranchNo(),reqOrderNo);
    }

    @Override
    public int uptRequireOrderByResendItem(UptReqOrderByResendItemMode umodel) {
        List<TMstRefuseResend> resendlist = umodel.getEntries();
        if(resendlist !=null &&  resendlist.size()>0){
            TSsmReqGoodsOrderItem item = tSsmReqGoodsOrderItemMapper.getReqGoodsItemsByMatnrAndOrderNo(umodel.getReqOrderNo(),umodel.getMatnr());
            TSysUser user = userSessionService.getCurrentUser();

            String reqOrderNo = item.getOrderNo();
            int  total = resendlist.stream().mapToInt(r->r.getUseQty().intValue()).sum();
            if(item.getQty() + item.getIncreQty() < total){
                throw new ServiceException(MessageCode.LOGIC_ERROR,"选择的拒收复送总数不应该大于要货数量");
            }
            //要货减少量
            int uptReqQty = 0;
            for(TMstRefuseResend resend : resendlist){
                String resendOrderNo = resend.getResendOrderNo();
                //单条拒收复送应用增加量
                BigDecimal addQty = BigDecimal.ZERO;
                //生成使用拒收复送详情
                TMstRefuseResend oldResend = resendMapper.selectRefuseResendByNo(resend.getResendOrderNo());
                TMstRefuseResendItem oldResendItem = resendItemMapper.selectItemByReqorderAndNo(umodel.getReqOrderNo(),resend.getResendOrderNo());
                if(oldResendItem != null){
                    //如果等于0 说明不再应用
                    if(resend.getUseQty().compareTo(BigDecimal.ZERO) == 0){
                        uptReqQty = uptReqQty +oldResendItem.getQty().intValue();
                        addQty = addQty.subtract(oldResendItem.getQty());
                        resendItemMapper.delResendItemByReOrderNoAndResendOrderNo(umodel.getReqOrderNo(),oldResend.getResendOrderNo());
                    }else{
                        uptReqQty = uptReqQty +oldResendItem.getQty().subtract(resend.getUseQty()).intValue();
                        addQty =addQty.add(resend.getUseQty().subtract(oldResendItem.getQty()));
                        //如果大于零  说明改变应用
                        oldResendItem.setQty(resend.getUseQty());
                        resendItemMapper.uptResendItem(oldResendItem);
                    }
                    //库存
                    stockService.updateTmpStock(oldResend.getBranchNo(),oldResend.getMatnr(),resend.getUseQty().subtract(oldResendItem.getQty()),user.getSalesOrg());
                }else{
                    if(resend.getUseQty().compareTo(BigDecimal.ZERO) == 0){
                        continue;
                    }else{
                        addQty = addQty.add(resend.getUseQty());
                        uptReqQty = uptReqQty + resend.getUseQty().multiply(new BigDecimal(-1)).intValue();
                        TMstRefuseResendItem resendItem = new TMstRefuseResendItem();
                        resendItem.setResendOrderNo(resendOrderNo);
                        resendItem.setOrderNo(reqOrderNo);
                        resendItem.setType("10");
                        resendItem.setCreateAt(new Date());
                        resendItem.setCreateBy(user.getLoginName());
                        resendItem.setQty(resend.getUseQty());
                        resendItemMapper.addResendItem(resendItem);
                        //库存
                        stockService.updateTmpStock(oldResend.getBranchNo(),oldResend.getMatnr(),resend.getUseQty(),user.getSalesOrg());
                    }
                }
                if(resend.getUseQty().compareTo(BigDecimal.ZERO)==0 && oldResendItem==null){
                    continue;
                }else{
                    //更新拒收复送 信息
                    oldResend.setRemainQty(oldResend.getRemainQty().subtract(addQty));
                    oldResend.setConfirmQty(oldResend.getConfirmQty().add(addQty));
                    if(oldResend.getRemainQty().compareTo(BigDecimal.ZERO) != -1){
                        oldResend.setStatus("20");
                    }else{
                        oldResend.setStatus("30");
                    }
                    resendMapper.uptRefuseResend(oldResend);
                }
            }
            //更新 要货计划行信息
            TSsmReqGoodsOrderItemUpt uptItem = new TSsmReqGoodsOrderItemUpt();
            uptItem.setResendQty(item.getResendQty()-uptReqQty);
            uptItem.setQty(item.getQty()+uptReqQty);
            uptItem.setOrderNo(item.getOrderNo());
            uptItem.setOldMatnr(item.getMatnr());
            tSsmReqGoodsOrderItemMapper.uptNewReqGoodsItem(uptItem);
            return 1;
        }else{
            throw  new ServiceException(MessageCode.LOGIC_ERROR,"拒收复送参数不能为空");
        }

    }


    /**
     * 根据 交货单 产品数量 生成  不参加促销的销售订单
     */

    public TSsmSalOrder createSalOrderByGiOrderMap(Map<String,Integer> giOrderMap,TSysUser user ,Date orderDate){
        TSsmSalOrder order = createSaleOrder(user, orderDate, "branch", "",1);
        int i = 0;
        for(String key : giOrderMap.keySet()){
            i = i + 1;
            TOrderDaliyPlanItem item = new TOrderDaliyPlanItem();
            item.setQty(giOrderMap.get(key));
            item.setConfirmMatnr(key);
            createSaleOrderItem(item, i + 1, order.getOrderNo(), orderDate, "branch");
        }
        return order;
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
        TMdBranch branch = branchMapper.selectBranchByNo(user.getBranchNo());
        SalOrderModel sMode = new SalOrderModel();
        sMode.setOrderDate(search.getOrderDate());
        sMode.setBranchNo(branch.getBranchNo());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<TSsmSalOrder> result = tSsmSalOrderMapper.selectSalOrderByDateAndNo(sMode);
        if (result != null && result.size() > 0) {
            Boolean flag = true;
            for (TSsmSalOrder entry : result) {
                if (StringUtils.isNotBlank(entry.getVoucherNo())){
                    continue;
                }else{
                    flag = false;
                    generateSalesOrderAnduptVouCher(entry);
                }
            }
            if(flag){
                throw new ServiceException(MessageCode.LOGIC_ERROR, "该奶站" + sdf.format(search.getOrderDate()) + "已经创建所有销售订单,请直接查询");
            }
            return this.getSaleOrderByQueryDate(sMode);
        }else{

            //判断今天的路单是否已经生成
            List<TDispOrder> dispOrders = tDispOrderMapper.selectDispOrderByBranchNoAndDay(user.getBranchNo(), search.getOrderDate());
            if (dispOrders == null || dispOrders.size() == 0) {
                throw new ServiceException(MessageCode.LOGIC_ERROR, "该奶站" + sdf.format(search.getOrderDate()) + " 这天的路单还没生成，请先生成路单并全部确认后再生成");
            }else{
                //判断今天的路单是否已经全部确认
                List<TDispOrder> confirmDispOrders = tDispOrderMapper.selectConfirmDispOrderByBranchNoAndDay(user.getBranchNo(), search.getOrderDate());
                if (confirmDispOrders != null) {
                    if (confirmDispOrders.size() < dispOrders.size()) {
                        throw new ServiceException(MessageCode.LOGIC_ERROR, "该奶站" + sdf.format(search.getOrderDate()) + "的路单还有" + (dispOrders.size() - confirmDispOrders.size()) + "个路单没确认，请全部确认后再生成");
                    }
                } else {
                    throw new ServiceException(MessageCode.LOGIC_ERROR, "该奶站" + sdf.format(search.getOrderDate()) + "的路单所有的路单都没确认，请全部确认后再生成");
                }
            }
            if ("01".equals(branch.getBranchGroup())) {
                //首先创建，创建完再发送
                TSsmSalOrder noprom = this.creatNoPromoSalOrderOfSelftBranch(search.getOrderDate());
                TSsmSalOrder prom = this.creatPromoSalOrderOfSelftBranch(search.getOrderDate());
                if(noprom!=null){
                    generateSalesOrderAnduptVouCher(noprom);
                }
                if(prom!=null){
                    generateSalesOrderAnduptVouCher(prom);
                }
                return this.getSaleOrderByQueryDate(sMode);
            } else {
                throw new ServiceException(MessageCode.LOGIC_ERROR, "该奶站不是自营奶站");
            }
        }

    }

    @Override
    public int creaSalOrderOfDealerBranchByDate(Date orderDate) {
        TSysUser user = userSessionService.getCurrentUser();
        SalOrderModel sMode = new SalOrderModel();
        sMode.setOrderDate(orderDate);
        sMode.setBranchNo(user.getBranchNo());
        List<TSsmSalOrder> results = tSsmSalOrderMapper.selectSalOrderByDateAndNo(sMode);
        if (results != null && results.size() > 0) {
            Boolean flag = true;
            for (TSsmSalOrder item : results) {
                if (StringUtils.isNotBlank(item.getVoucherNo())) {
                    continue;
                } else {
                    flag = false;
                    generateSalesOrderAnduptVouCher(item);
                }
            }
            if (flag) {
                throw new ServiceException(MessageCode.LOGIC_ERROR, "该奶站今天已经创建所有销售订单,请直接查询");
            }
            return 1;
        }else {
                TSsmSalOrder  noPromOrder  = this.creatNoPromoSalOrderOfDealerBranch(orderDate);
                TSsmSalOrder  promOrder = this.creatPromoSalOrderOfDealerBranch(orderDate);
                if(noPromOrder !=null){
                    generateSalesOrderAnduptVouCher(noPromOrder);
                }
                if(promOrder !=null){
                    generateSalesOrderAnduptVouCher(promOrder);
                }

        }
        return 0;
    }

    /**
     *添加  销售订单行项目
     * @param item          产品code，产品数量
     * @param i             用来生成itemNo
     * @param orderNo       销售单号
     * @param orderDate  日期
     * @param type          如果type=dealer 则为经销商订单行项目，否则为自营奶站订单行项目
     */
    private void createSaleOrderItem(TOrderDaliyPlanItem item, int i, String orderNo,Date orderDate,String type) {
        TSsmSalOrderItems salOrderItems = new TSsmSalOrderItems();
        salOrderItems.setOrderNo(orderNo);
        salOrderItems.setOrderDate(orderDate);
        salOrderItems.setQty(item.getQty());
        if(StringUtils.isNotBlank(item.getPromotionFlag())){
            salOrderItems.setPromNo(item.getPromotionFlag());
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
    private TSsmSalOrder  createSaleOrder(TSysUser user, Date requiredDate, String type, String promotion,int i) {
        TSsmSalOrder order = new TSsmSalOrder();
        String orderNo = this.generateSal35Id(i);
        order.setOrderNo(orderNo);
        order.setSalesOrg(user.getSalesOrg());
        TMdBranch branch = branchMapper.selectBranchByNo(user.getBranchNo());
        if ("4181".equals(user.getSalesOrg()) || "4390".equals(user.getSalesOrg())) {
            order.setRequiredDate(requiredDate);
        } else {
            if("01".equals(branch.getBranchGroup())){
                order.setRequiredDate(requiredDate);
            }else{
                order.setRequiredDate( DateUtil.getTomorrow(requiredDate));
            }
        }

        order.setBranchNo(user.getBranchNo());
        if("dealer".equals(type)){
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
    private String generateSal35Id(int  i){
        TMdBranch branch = branchMapper.getBranchByNo(userSessionService.getCurrentUser().getBranchNo());
        StringBuilder uuid = new StringBuilder();
        uuid.append("DH001");
        uuid.append("A");
        uuid.append(branch.getCompanyCode());
        String branchNo = branch.getBranchNo();
        uuid.append(branchNo.substring(1));
        uuid.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        uuid.append(new Random().nextInt(80)+10+i);
        System.out.println("------------------------"+ uuid.toString());
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
        uuid.append(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        uuid.append((int) ((Math.random() * 90) + 10));
        return uuid.toString();
    }

    public  void generateSalesOrderAnduptVouCher(TSsmSalOrder order){
        TMdBranch branch = branchMapper.selectBranchByNo(order.getBranchNo());
        PISuccessMessage  message = null;
        if("01".equals(branch.getBranchGroup())){
              message = piRequireOrderService.generateSalesOrder(order, order.getDealerNo(), order.getBranchNo(), order.getSalesOrg(), "");
        }else{
              message = piRequireOrderService.generateSalesOrder(order, order.getDealerNo(), order.getBranchNo(), order.getSalesOrg(), "");
        }
        if (message.isSuccess()) {
            this.uptVouCherNoByOrderNo(order.getOrderNo(), message.getData());
        }else{
            throw new ServiceException(MessageCode.LOGIC_ERROR, message.getMessage());
        }
    }

}
