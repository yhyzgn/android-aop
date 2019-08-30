package com.yhy.aop.handler;

import android.app.Activity;

import com.yhy.aop.AopHelper;
import com.yhy.aop.annotation.AopExit;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.lang.reflect.Method;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-03-25 11:15
 * version: 1.0.0
 * desc   : 应用返回退出提示
 * <p>
 * 使用该方式的前提是，顶级父类Activity必须重写onBackPressed方法
 */
@Aspect
public class ExitHandler {
    private static long lastTime = 0L;

    @Around("execution(* android.app.Activity+.onBackPressed())")
    public void sticky(ProceedingJoinPoint point) throws Throwable {
        if (AopHelper.getInstance().isEnabled()) {
            Object target = point.getTarget();
            if (target instanceof Activity) {
                try {
                    Method callback = target.getClass().getDeclaredMethod("onBackPressed");
                } catch (NoSuchMethodException e) {
                    AopHelper.getInstance().log("@ExitHandler注解的Activity或其父类必须重写onBackPressed方法", AopHelper.Logger.Level.ERROR);
                }

                Activity activity = (Activity) target;
                AopExit aop = activity.getClass().getAnnotation(AopExit.class);
                if (aop != null && System.currentTimeMillis() - lastTime >= aop.interval()) {
                    Class<?> clazz = aop.callback();
                    if (clazz != Object.class) {
                        Method callback = clazz.getDeclaredMethod("callback", Activity.class, String.class);
                        callback.setAccessible(true);
                        callback.invoke(clazz.newInstance(), activity, aop.value());
                    }
                    lastTime = System.currentTimeMillis();
                    return;
                }
            }
        }
        // 允许退出
        point.proceed();
    }
}
