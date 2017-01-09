package com.nhry.service.milktrans.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.milktrans.domain.*;
import com.nhry.model.milktrans.*;

import java.util.Date;
import java.util.List;

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

    String batchSendSalOrder(String [] orderNos);
}
