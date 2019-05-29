package com.yhy.aop.aspectj;

import com.yhy.aop.AopHelper;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-03-24 23:20
 * version: 1.0.0
 * desc   : 处理重复跳转Activity
 */
@Aspect
public class ActivityAspect {
    private static final long INTERVAL_TIME = 1000L;
    private static long lastTime = 0L;

    @Around("call(* android.content.Context.startActivity(..))")
    public void start(ProceedingJoinPoint point) throws Throwable {
        if (!AopHelper.getInstance().isEnabled()) {
            point.proceed();
            return;
        }
        if (System.currentTimeMillis() - lastTime > INTERVAL_TIME) {
            // 已经超过禁用时间
            lastTime = System.currentTimeMillis();
            // 允许方法执行
            point.proceed();
        } else {
            AopHelper.getInstance().log("已阻止Activity重复跳转", AopHelper.Logger.Level.INFO);
        }
    }
}
