package com.nhry.data.basic.dao;

import com.nhry.data.basic.domain.TMaraSalesKey;

public interface TMaraSalesMapper {
    int deleteMaraSales(TMaraSalesKey key);

    int insertMaraSales(TMaraSalesKey record);

    int isMaraSales(TMaraSalesKey record);
}