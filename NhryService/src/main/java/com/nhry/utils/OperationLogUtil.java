package com.nhry.utils;

import com.nhry.data.auth.domain.TSysUser;
import com.nhry.data.basic.dao.TMdOperationLogMapper;
import com.nhry.data.basic.domain.TMdOperationLog;

import java.util.Date;

/**
 * Created by gongjk on 2016/10/24.
 */
public class OperationLogUtil  {
    public static void saveHistoryOperation(String logNo, String type,String logName, String originalValue, String newValue,
                                            String matnr, Date dispDate, TSysUser user,TMdOperationLogMapper operationLogMapper){
        final TMdOperationLog operationLogModel = new TMdOperationLog();
        operationLogModel.setLogNo(logNo);
        operationLogModel.setLogType(type);
        operationLogModel.setLogDate(new Date());
        operationLogModel.setLogName(logName);
        operationLogModel.setOriginalValue(originalValue);
        operationLogModel.setNewValue(newValue);
        operationLogModel.setCreateBy(user.getLoginName());
        operationLogModel.setCreateByTxt(user.getDisplayName());
        operationLogModel.setCreateAt(new Date());
        operationLogModel.setDispDate(dispDate);
        operationLogModel.setLogMatnr(matnr);
        operationLogMapper.save(operationLogModel);
    }
}
