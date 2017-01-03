package com.nhry.service.pi.dao;

/**
 * Created by cbz on 12/26/2016.
 */
public interface PIRedateByTradeService {
    /**
     * 定时发送折扣金额到ＣＲＭ或ＥＲＰ
     * @return
     */
    String sendRedateByTradeToCRM();
}
