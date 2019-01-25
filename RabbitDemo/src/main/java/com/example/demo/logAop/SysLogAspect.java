package com.example.demo.logAop;

import com.google.gson.Gson;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.awt.*;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * @version 1.0
 * @类名: SysLogAspect.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-1-24
 */
@Aspect
@Component
public class SysLogAspect {
    @Autowired
    private SysLogService sysLogService;

    /**
     * 定义一个切入点
     * 时间:   2019-1-24 15:34
     * 作者:   CM
     * @param
     * @return void
     */
    @Pointcut("@annotation(com.example.demo.logAop.SysLog)")
    public void logPointCut() {
    }

    /**
     * 用于环绕通知，使用proceed()方法来执行目标方法
     * 时间:   2019-1-24 15:55
     * 作者:   CM
     * @param point
     * @return java.lang.Object
     */
    @Around("logPointCut()")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = point.proceed();
        long time = System.currentTimeMillis() - beginTime;
        saveLog(point, time);
        return result;
    }

    /**
     * 保存日志
     * 时间:   2019-1-24 14:57
     * 作者:   CM
     *
     * @param joinPoint
     * @param time
     * @return void
     */
    private void saveLog(ProceedingJoinPoint joinPoint, long time) throws InterruptedException {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        SysLogBo sysLogBo = new SysLogBo();
        sysLogBo.setExeuTime(time);
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        sysLogBo.setCreateData(dateFormat.format(new Date()));
        SysLog sysLog = method.getAnnotation(SysLog.class);
        if (sysLog != null) {
            //注解上的描述
            sysLogBo.setRemark(sysLog.value());
        }
        //请求的类名、方法名
        String className = joinPoint.getTarget().getClass().getName();
        String methodName = signature.getName();
        sysLogBo.setClassName(className);
        sysLogBo.setMethodName(methodName);
        //请求的参数
        Object[] args = joinPoint.getArgs();
        ArrayList<String> list = new ArrayList<String>();
        for (Object o : args) {
            list.add(new Gson().toJson(o));
        }
        sysLogBo.setParams(list.toString());
        sysLogService.save(sysLogBo);

    }
}
