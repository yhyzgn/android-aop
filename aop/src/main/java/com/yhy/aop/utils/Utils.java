package com.yhy.aop.utils;

import android.app.Application;
import android.util.SparseArray;
import android.view.View;

import com.yhy.aop.AOP;
import com.yhy.aop.annotation.ClickIgnored;
import com.yhy.aop.annotation.EnableClickResolver;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-09-07 23:12
 * version: 1.0.0
 * desc   :
 */
public class Utils {

    private static final SparseArray<Long> VIEW_CLICK_TIME_HISTORY = new SparseArray<>();

    private Utils() {
        throw new UnsupportedOperationException("Utils class can not be instantiate.");
    }

    public static boolean isTooFastClick(View view) {
        Long last = VIEW_CLICK_TIME_HISTORY.get(view.getId());
        long now = System.currentTimeMillis();
        // 不是第一次点击
        if (null != last && last > 0) {
            Application app = AOP.app();
            EnableClickResolver resolver = app.getClass().getAnnotation(EnableClickResolver.class);
            if (null != resolver) {
                long interval = resolver.value();
                if (now - last <= interval) {
                    // 两次点击在规定的间隔时间之内
                    return true;
                }
            }
        }
        // 更新点击时间
        VIEW_CLICK_TIME_HISTORY.put(view.getId(), now);
        return false;
    }

    public static boolean clickIgnored(ProceedingJoinPoint point) {
        Method method = joinedMethod(point);
        return null != method && method.isAnnotationPresent(ClickIgnored.class);
    }

    public static boolean clickResolverEnabled() {
        Application app = AOP.app();
        return isAnnotationPresent(app.getClass(), EnableClickResolver.class);
    }

    public static boolean isAnnotationPresent(Class<?> clazz, Class<? extends Annotation> annotation) {
        return clazz.isAnnotationPresent(annotation);
    }

    public static Method joinedMethod(ProceedingJoinPoint point) {
        if (null != point) {
            Signature st = point.getSignature();
            if (st instanceof MethodSignature) {
                MethodSignature ms = (MethodSignature) st;
                return ms.getMethod();
            }
        }
        return null;
    }
}
