package com.nhry.service.pi.dao;

import org.apache.poi.ss.formula.functions.T;

import java.util.Date;
import java.util.List;

/**
 * Created by cbz on 7/4/2016.
 */
public interface PIRequireOrderService {
    String generateRequireOrder(String branch, Date roDate, List<T> rqItem);
}
