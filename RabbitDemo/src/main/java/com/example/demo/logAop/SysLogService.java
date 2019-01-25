package com.example.demo.logAop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @类名: SysLogService.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-1-24
 */
@Slf4j
@Service
public class SysLogService {
    @Async
    public void save(SysLogBo sysLogBo) throws InterruptedException {
        Thread.sleep(3000);
        log.info(sysLogBo.getParams());
        //return true;
    }
}
