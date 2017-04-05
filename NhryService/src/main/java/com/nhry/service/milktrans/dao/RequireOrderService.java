package com.nhry.service.milktrans.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.basic.domain.TMdBranch;
import com.nhry.data.milktrans.domain.*;
import com.nhry.model.milktrans.*;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by gongjk on 2016/6/24.
 */
public interface RequireOrderService {

    // RequireOrderModel creatRequireOrder();
    RequireOrderModel searchRequireOrder(Date orderDate);

    int uptNewRequireOrderItem(UpdateNewRequiredModel uModel);

    int addRequireOrderItem(TSsmReqGoodsOrderItem item);

    int delRequireOrderItem(ReqGoodsOrderItemSearch item);

    int uptRequireOrder(UpdateRequiredModel uModel);

    TSsmSalOrder creatPromoSalOrderOfDealerBranch(Date today, String branchNo, String salesOrg);

    TSsmSalOrder creatNoPromoSalOrderOfDealerBranch(Date requiredDate, String branchNo, String salesOrg);

    List<TSsmSalOrder> creatNoPromoSalOrderOfDealerBranch70(Date requiredDate, String branchNo, String salesOrg);

    TSsmSalOrder createPromDaliyDiscountAmtOfDearler(Date requireDate, String branchNo,String salesOrg);

    TSsmSalOrder createPromDaliyDiscountAmtOfBranch(Date requireDate, String branchNo,String salesOrg);

    TSsmSalOrder creatPromoSalOrderOfDealerBranch40(Date today, String branchNo, String salesOrg);

    TSsmSalOrder creatNoPromoSalOrderOfDealerBranch40(Date requiredDate, String branchNo, String salesOrg);

    TSsmSalOrder creatNoPromoSalOrderOfSelftBranch(Date requiredDate);

    TSsmSalOrder creatPromoSalOrderOfSelftBranch(Date requiredDate);

    List<TSsmSalOrder> creaSalOrderOfSelftBranchByDate(SalOrderDaySearch search);

    int creaSalOrderOfDealerBranchByDate(Date orderDate);

    List<TSsmSalOrder> getSaleOrderByQueryDate(SalOrderModel sModel);

    List<TSsmSalOrderItems> getSaleOrderDetailByOrderNo(String orderNo);

    RequireOrderModel creatRequireOrderByDate(ReqGoodsOrderSearch eSearch);

    String sendRequireOrderToERPByDate(ReqGoodsOrderSearch eSearch);

    TSsmSalOrder creatNoPromoSalOrderAndSendOfSelftBranch(Date orderDate);

    TSsmSalOrder creatPromoSalOrderAndSendOfSelftBranch(Date orderDate);

    List<TSsmSalOrder> creaSalOrderOfSelftBranchByDate2(SalOrderDaySearch search);

    List<TMstRefuseResend> queryRefuseResendByMatnr(String matnr,String reqOrderNo);

    int uptRequireOrderByResendItem(UptReqOrderByResendItemMode umodel);

    PageInfo searchRequireOrderByDealer(RequireOrderSearchPage rModel);

    RequireOrderModel getRequireOrderByOrderNo(String orderNo);

    PageInfo searchSalOrderByDealer(RequireOrderSearchPage sModel);

    String batchSendSalOrder(List orderNos);

    Map<String,Integer> findReqGoodResendByOrderNo(Date orderDate, String branchNo);

    /**
     * 按送奶工维度统计日销售数量，生成送奶工销售订单使用（经销商奶站）
     * @param requiredDate
     * @param branchNo
     * @param salesOrg
     * @return
     */
    List<TSsmSalOrder> creatNoPromoSalOrderOfDealerBranchAndEmpNo(Date requiredDate, String branchNo, String salesOrg);


    boolean isEmpSendMode();

    /**
     * 生成自营奶站送奶工销售订单
     * @param search
     * @return
     */
    List<TSsmSalOrder> creaSalOrderOfSelftBranchAndEmpNoByDate(SalOrderDaySearch search);

    /**
     * 按送奶工维度统计日销售数量，生成送奶工销售订单使用(自营奶站)
     * @param requiredDate
     * @param branchNo
     * @param salesOrg
     * @return
     */
    List<TSsmSalOrder> creatNoPromoSalOrderOfBranchAndEmpNo(Date requiredDate, String branchNo, String salesOrg);

    /**
     * 生成电商的促销销售订单（自营奶站）
     * @param today
     * @param branchNo
     * @param salesOrg
     * @return
     */
    TSsmSalOrder creatPromoSalOrderOfBranch40(Date today, String branchNo, String salesOrg);

    /**
     * 生成电商的销售订单（自营奶站）
     * @param today
     * @param branchNo
     * @param salesOrg
     * @return
     */
    TSsmSalOrder creatNoPromoSalOrderOfBranch40(Date today, String branchNo, String salesOrg);

    /**
     * 生成机构订奶销售订单（自营奶站）
     * @param requiredDate
     * @param branchNo
     * @param salesOrg
     * @return
     */
    List<TSsmSalOrder> creatNoPromoSalOrderOfBranchOrg(Date requiredDate, String branchNo, String salesOrg);

    /**
     * 生成年卡订奶销售订单（自营奶站）
     * @param requiredDate
     * @param branchNo
     * @param salesOrg
     * @return
     */
    TSsmSalOrder creatPromDaliyDiscountAmtOfBranch(Date requiredDate, String branchNo, String salesOrg);

    /**
     * 发送销售订单
     * @param search
     * @return
     */
    List<TSsmSalOrder> batchSendSalOrderByDate(SalOrderDaySearch search);

    List<TSsmSalOrderItems> updateSalOrderItems(List<TSsmSalOrderItems> itemss);

    Date getSalOrderDate(Date orderDate, TMdBranch branch);

    Date getRequireDate(Date orderDate);



}
