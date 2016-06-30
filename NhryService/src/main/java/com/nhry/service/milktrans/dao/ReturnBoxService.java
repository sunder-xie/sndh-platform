package com.nhry.service.milktrans.dao;

import com.github.pagehelper.PageInfo;
import com.nhry.model.milktrans.BoxSearch;
import com.nhry.model.milktrans.CreateEmpReturnboxModel;
import com.nhry.model.milktrans.CreateReturnBoxModel;

/**
 * Created by gongjk on 2016/6/27.
 */
public interface ReturnBoxService {
    public PageInfo searchBoxPage(BoxSearch bsearch);

    int uptBoxRetrun(CreateReturnBoxModel boxModel);

    public  CreateReturnBoxModel createDayRetBox(CreateEmpReturnboxModel cModel);
}
