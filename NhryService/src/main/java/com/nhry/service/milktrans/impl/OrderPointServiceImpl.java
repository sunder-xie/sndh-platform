package com.nhry.service.milktrans.impl;

import com.nhry.common.exception.ServiceException;
import com.nhry.data.milktrans.dao.TSsmSalOrderItemMapper;
import com.nhry.data.milktrans.dao.TSsmSalOrderMapper;
import com.nhry.model.milktrans.OrderPointModel;
import com.nhry.service.milktrans.dao.OrderPointService;
import org.apache.log4j.Logger;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cbz on 8/17/2016.
 */
public class OrderPointServiceImpl implements OrderPointService {
    private static final Logger logger = Logger.getLogger(OrderPointServiceImpl.class);

    private TSsmSalOrderMapper ssmSalOrderMapper;

    public void setSsmSalOrderMapper(TSsmSalOrderMapper ssmSalOrderMapper) {
        this.ssmSalOrderMapper = ssmSalOrderMapper;
    }

    private TSsmSalOrderItemMapper ssmSalOrderItemMapper;

    public void setSsmSalOrderItemMapper(TSsmSalOrderItemMapper ssmSalOrderItemMapper) {
        this.ssmSalOrderItemMapper = ssmSalOrderItemMapper;
    }

    @Override
    public int uptYfrechAndYGrowthByOrderNoAndItemNo(List<OrderPointModel> models) {
        logger.info("销售订单明细积分更新开始");
        try {
            Map<String, String> map = new HashMap<String, String>();
            for (OrderPointModel model : models) {
                map.put(model.getOrderNo(), model.getItemNo());
                ssmSalOrderItemMapper.uptYfrechAndYGrowthByOrderNoAndItemNo(model);
            }
            for (Map.Entry<String, String> entry : map.entrySet()) {
                OrderPointModel orderPoint = ssmSalOrderItemMapper.getSumYfrechAndYGrowByOrderNo(entry.getKey());
                ssmSalOrderMapper.uptYfrechAndYGrowthByOrderNo(orderPoint);
            }
        }catch (Exception e){
            logger.error("销售订单明细积分更新失败！"+e.getMessage());
            throw new ServiceException("销售订单明细积分更新失败！");
        }
        logger.info("销售订单明细积分更新结束");
        return 1;
    }
}
