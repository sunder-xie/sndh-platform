package com.nhry.service.milktrans.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.data.milk.domain.TDispOrderItem;
import com.nhry.model.milk.RouteDetailUpdateModel;
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

    //更新录单时  重新录入回瓶记录 （update）
    public int  uptBoxReturnByDispOrder(RouteDetailUpdateModel newItem , TDispOrderItem orgItem);
}
