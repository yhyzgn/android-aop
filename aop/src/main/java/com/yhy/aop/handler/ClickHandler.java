package com.yhy.aop.handler;

import android.util.Log;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-08-30 15:26
 * version: 1.0.0
 * desc   :
 */
@Aspect
public class ClickHandler {

    private volatile boolean isIgnored = false;

    @Before("@execution(@com.yhy.aop.annotation.AopClickIgnore void **..*.onClick(..))")
    public void ignore() {
        isIgnored = true;
    }

    @Pointcut("execution(void **..lambda*(android.view.View))")
    public void lambda() {
    }

    @Pointcut("execution(void android.view.View.OnClickListener.onClick(..))")
    public void normal() {
    }

    //    @Around("lambda()||normal()")
    @Around("execution(void android.view.View.OnClickListener.onClick(..))")
    public void handler(ProceedingJoinPoint point) throws Throwable {
        Log.i("ClickHandler", "哈哈哈哈");
        point.proceed();
    }
}
