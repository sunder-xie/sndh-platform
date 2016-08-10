package com.nhry.service.milktrans.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.model.milktrans.ReturnboxSerarch;
import com.nhry.model.milktrans.UpdateReturnBoxModel;
import com.nhry.model.stock.StockModel;

/**
 * Created by gongjk on 2016/6/27.
 */
public interface ReturnBoxService {

    int uptBoxRetrun(UpdateReturnBoxModel boxModel);

    public  int createDayRetBox(String dispOrderNo);

    public PageInfo searchRetBoxPage(ReturnboxSerarch rSearch);
    
    String getLastDayRets(String code);

    public int craeteRetBotByStock(StockModel sModel);

}
