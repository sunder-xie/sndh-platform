package com.nhry.service.milktrans.impl;

import com.nhry.common.exception.MessageCode;
import com.nhry.common.exception.ServiceException;
import com.nhry.data.milktrans.dao.TRequireOrderMapper;
import com.nhry.data.milktrans.domain.TMstRequireOrder;
import com.nhry.data.order.dao.TOrderDaliyPlanItemMapper;
import com.nhry.data.order.domain.TOrderDaliyPlanItem;
import com.nhry.model.milktrans.OrderRequireItem;
import com.nhry.model.milktrans.RequireOrderModel;
import com.nhry.model.milktrans.RequireOrderSearch;
import com.nhry.service.milktrans.dao.RequireOrderService;
import com.nhry.service.order.impl.OrderServiceImpl;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Created by gongjk on 2016/6/24.
 */
public class RequireOrderServiceImpl implements RequireOrderService {

    private TRequireOrderMapper tRequireOrderMapper;
    private TOrderDaliyPlanItemMapper tOrderDaliyPlanItemMapper;
    public void settRequireOrderMapper(TRequireOrderMapper tRequireOrderMapper) {
        this.tRequireOrderMapper = tRequireOrderMapper;
    }

    public void settOrderDaliyPlanItemMapper(TOrderDaliyPlanItemMapper tOrderDaliyPlanItemMapper) {
        this.tOrderDaliyPlanItemMapper = tOrderDaliyPlanItemMapper;
    }

    @Override
    public RequireOrderModel creatRequireOrder(RequireOrderModel rModel) {

        RequireOrderSearch sModel = new RequireOrderSearch();
        sModel.setRequireDate(rModel.getRequireDate());
        sModel.setBranchNo(rModel.getBranchNo());

        List<TMstRequireOrder> orderlist = this.tRequireOrderMapper.searchRequireOrder(sModel);
        if(orderlist!=null && orderlist.size()>0){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"当天要货计划已存在");
        }
        Date today = new Date();
        Date requireDate = rModel.getRequireDate();
        rModel.setPreDays(OrderServiceImpl.daysOfTwo(today,requireDate));
        List<TOrderDaliyPlanItem> items = tOrderDaliyPlanItemMapper.selectDaliyPlansByBranchAndDay(rModel);
        //将日预定单生成的要货计划存放数据库
        for(TOrderDaliyPlanItem entry :items ){
            TMstRequireOrder torder = new TMstRequireOrder();
            torder.setRequireDate(requireDate);
            torder.setBranchNo(rModel.getBranchNo());
            torder.setMatnr(entry.getMatnr());
            torder.setCreateAt(today);
            torder.setQty(entry.getQty());
            torder.setIncreQty(0);
            torder.setStatus("10");
            this.tRequireOrderMapper.insertRequireOrder(torder);
        }
        return this.searchRequireOrder(sModel);

    }



    @Override
    public int insertRequireOrder(TMstRequireOrder order) {
        return tRequireOrderMapper.insertRequireOrder(order);
    }

    /**
     * 根据 奶站编号 和 日期 获取当前日期的要货计划
     * @param rModel
     * @return
     */
    @Override
    public RequireOrderModel searchRequireOrder(RequireOrderSearch rModel) {
        List<TMstRequireOrder> orderlist = this.tRequireOrderMapper.searchRequireOrder(rModel);
        RequireOrderModel orderModel = new RequireOrderModel();
        if(orderlist!= null && orderlist.size()>0){
            TMstRequireOrder rorder = orderlist.get(0);
            orderModel.setBranchNo(rorder.getBranchNo());
            orderModel.setRequireDate(rorder.getRequireDate());
            List<OrderRequireItem> entry = new ArrayList<OrderRequireItem>();
            for(TMstRequireOrder order :orderlist){
                if(StringUtils.isBlank(orderModel.getStatus())){
                    orderModel.setStatus(order.getStatus());
                }
                OrderRequireItem item = new OrderRequireItem();
                item.setMatnr(order.getMatnr());
                item.setMatnrTxt(order.getMatnrTxt());
                item.setQty(order.getQty());
                item.setIncreQty(order.getIncreQty());
                entry.add(item);
            }
            orderModel.setEntries(entry);
        }
        return orderModel;
    }

    /**
     * 修改要货计划
     * @param rModel
     * @return
     */
    @Override
    public int uptRequireOrder(RequireOrderModel rModel) {
        //获取数据库中存好的要货计划
        RequireOrderSearch search = new RequireOrderSearch();
        search.setBranchNo(rModel.getBranchNo());
        search.setRequireDate(rModel.getRequireDate());
        RequireOrderModel orderModel = this.searchRequireOrder(search);

        if(orderModel.getStatus() == "30"){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"要货订单已确定，不能改变");
        }
        if(orderModel == rModel){
            throw new ServiceException(MessageCode.LOGIC_ERROR,"要货计划未改变");
        }

        int add = 0;
        int upt = 0;
        int del = 0;
        boolean success = true;
        try{
            //前台传来的要货计划entry
            List<OrderRequireItem> items = rModel.getEntries();
            //数据库中存放的要货计划enry
            List<OrderRequireItem> oldItems = orderModel.getEntries();
            //存放数据库中取出来的产品，key存放产品code
            Set<String> oldCodeSet = new HashSet<String>();
            Map<String,OrderRequireItem> oldMaps =new HashMap<String,OrderRequireItem>();
            for(OrderRequireItem item : oldItems) {
                oldCodeSet.add(item.getMatnr());
                oldMaps.put(item.getMatnr(),item);

            }
            for(OrderRequireItem item : items){
                String matnr = item.getMatnr();
                TMstRequireOrder tMstRequireOrder = new TMstRequireOrder();
                tMstRequireOrder.setBranchNo(rModel.getBranchNo());
                tMstRequireOrder.setRequireDate(rModel.getRequireDate());
                tMstRequireOrder.setMatnr(matnr);
                tMstRequireOrder.setQty(item.getQty());
                tMstRequireOrder.setIncreQty(item.getIncreQty());
                //该产品存在，则该产品有两种可能：1数量修改，2 没有变化
                if(oldCodeSet.contains(matnr)){
                    oldCodeSet.remove(matnr);
                    OrderRequireItem oldItem = oldMaps.get(matnr);
                    //数量发生变化  则说明做了修改 ， 更新数据
                    if(oldItem.getQty() != item.getQty() || oldItem.getIncreQty() != item.getIncreQty()){//
                        tMstRequireOrder.setStatus("20");
                        upt = tRequireOrderMapper.uptRequireOrder(tMstRequireOrder);
                    }
                    oldMaps.remove(matnr);
                }else {
                    //该产品不存在：新添加的
                    tMstRequireOrder.setStatus("10");
                    add = tRequireOrderMapper.insertRequireOrder(tMstRequireOrder);

                }
            }
            //如果oldCodeSet.size()>0 则说明这里面的产品为删除的产品
            if(oldCodeSet.size()>0){
                for(String matnr : oldCodeSet){
                    OrderRequireItem oldItem = oldMaps.get(matnr);
                    TMstRequireOrder tMstRequireOrder = new TMstRequireOrder();
                    tMstRequireOrder.setBranchNo(rModel.getBranchNo());
                    tMstRequireOrder.setRequireDate(rModel.getRequireDate());
                    tMstRequireOrder.setMatnr(matnr);
                    del = tRequireOrderMapper.delRequireOrder(tMstRequireOrder);
                }
            }

        }catch (Exception e){
            success = false;
            throw new ServiceException(MessageCode.LOGIC_ERROR,"更新要货订单失败");
        }
        return add + upt +del;
    }


}
