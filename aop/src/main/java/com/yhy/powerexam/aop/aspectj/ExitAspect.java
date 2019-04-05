package com.yhy.powerexam.aop.aspectj;

import android.app.Activity;

import com.yhy.powerexam.aop.AopHelper;
import com.yhy.powerexam.aop.annotation.ExitSticker;
import com.yhy.powerexam.aop.listener.OnAopExitListener;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import java.util.List;

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
public class ExitAspect {
    private static long lastTime = 0L;

    @Around("execution(* android.app.Activity+.onBackPressed())")
    public void sticky(ProceedingJoinPoint point) throws Throwable {
        if (AopHelper.getInstance().isEnabled()) {
            Object target = point.getTarget();
            if (target instanceof Activity) {
                Activity activity = (Activity) target;
                ExitSticker prevent = activity.getClass().getAnnotation(ExitSticker.class);
                if (prevent != null && System.currentTimeMillis() - lastTime >= prevent.value()) {
                    List<OnAopExitListener> listenerList = AopHelper.getInstance().getExitListenerList();
                    if (null != listenerList && !listenerList.isEmpty()) {
                        for (OnAopExitListener listener : listenerList) {
                            listener.onAopExit(activity, "再按一次退出应用");
                        }
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
