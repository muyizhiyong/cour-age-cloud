package com.muyi.courage.common.aop;

import com.muyi.courage.common.config.DBContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author 杨志勇
 * @date 2021-04-16 10:10
 */
@Aspect
@Component
@Slf4j
public class DataSourceAop {
    @Pointcut("!@annotation(com.muyi.courage.common.annotation.DBMaster) " +
            "&& (execution(* com.muyi.courage..service..qry*(..)) " +
            "|| execution(* com.muyi.courage..service..get*(..)) )" )
    public void readPointcut() {

    }

    @Pointcut("@annotation(com.muyi.courage.common.annotation.DBMaster) " +
            "|| execution(* com.muyi.courage..service..insert*(..)) " +
            "|| execution(* com.muyi.courage..service..add*(..)) " +
            "|| execution(* com.muyi.courage..service..update*(..)) " +
            "|| execution(* com.muyi.courage..service..delete*(..)) " +
            "|| execution(* com.muyi.courage..weblog..*(..))" )
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        log.info("readPointcut...");
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        log.info("writePointcut...");
        DBContextHolder.master();
    }
}
