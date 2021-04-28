package com.muyi.courage.common.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.lang.Nullable;


/**
 * @author 杨志勇
 * @date 2021-04-16 04
 */
public class MyRoutingDataSource extends AbstractRoutingDataSource {
    @Override
    @Nullable
    public Object determineCurrentLookupKey(){
        return DBContextHolder.get();
    }

}
