package com.muyi.courage.common.config;

import com.muyi.courage.common.util.DBTypeEnum;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author 杨志勇
 * @date 2021-04-16 10:08
 */
@Slf4j
public class DBContextHolder {
    private static final ThreadLocal<DBTypeEnum> contextHolder = new ThreadLocal<>();

    private static final AtomicInteger counter = new AtomicInteger(-1);

    public static void set(DBTypeEnum dbType) {
        contextHolder.set(dbType);
    }

    public static DBTypeEnum get() {
        return contextHolder.get();
    }

    public static void master() {
        set(DBTypeEnum.MASTER);
        log.info("[DBContextHolder] change to master DB ");
    }

    public static void slave() {
        //  轮询
        int index = counter.getAndIncrement() % 2;
        if (counter.get() > 9999) {
            counter.set(-1);
        }
        if (index == 0) {
            set(DBTypeEnum.SLAVE1);
            log.info("[DBContextHolder] change to slave[1] DB ");
        }else {
            set(DBTypeEnum.SLAVE2);
            log.info("[DBContextHolder] change to slave[2] DB ");
        }
    }
}
