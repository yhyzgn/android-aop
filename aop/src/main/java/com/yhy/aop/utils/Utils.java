package com.yhy.aop.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import java.util.ArrayList;
import java.util.List;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-09-07 23:12
 * version: 1.0.0
 * desc   : 工具类
 */
@SuppressWarnings("unchecked")
public class Utils {
    // 记录每个被点击控件的最后点击时间
    private static final SparseArray<Long> VIEW_CLICK_TIME_HISTORY = new SparseArray<>();

    private Utils() {
        throw new UnsupportedOperationException("Utils class can not be instantiate.");
    }

    /**
     * 是否太快点击
     *
     * @param view 被点击的view
     * @return 是否太快点击
     */
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

    /**
     * 是否被忽略
     *
     * @param point 切点
     * @return 是否被忽略
     */
    public static boolean clickIgnored(ProceedingJoinPoint point) {
        Method method = joinedMethod(point);
        return null != method && method.isAnnotationPresent(ClickIgnored.class);
    }

    /**
     * 是否启用抖动处理器
     *
     * @return 是否启用
     */
    public static boolean clickResolverEnabled() {
        Application app = AOP.app();
        return isAnnotationPresent(app.getClass(), EnableClickResolver.class);
    }

    /**
     * 某个类是否被注解
     *
     * @param clazz      类
     * @param annotation 注解
     * @return 是否被注解
     */
    public static boolean isAnnotationPresent(Class<?> clazz, Class<? extends Annotation> annotation) {
        return clazz.isAnnotationPresent(annotation);
    }

    /**
     * 获取被切面切到的（当前）方法对象
     *
     * @param point 切点
     * @return 方法
     */
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

    /**
     * 获取被注解的Activity
     *
     * @param context    上下文
     * @param annotation 注解
     * @return Activity类
     */
    public static Class<? extends Activity> annotatedActivity(Context context, Class<? extends Annotation> annotation) {
        try {
            PackageInfo info = context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (null != info.activities && info.activities.length > 0) {
                Class<?> clazz;
                for (ActivityInfo ai : info.activities) {
                    clazz = Class.forName(ai.name);
                    if (Activity.class.isAssignableFrom(clazz) && isAnnotationPresent(clazz, annotation)) {
                        return (Class<? extends Activity>) clazz;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取已注册的Activity列表
     *
     * @param context 上下文
     * @return activity列表
     */
    public static List<Class<? extends Activity>> registeredActivities(Context context) {
        List<Class<? extends Activity>> result = new ArrayList<>();
        try {
            PackageInfo info = context.getApplicationContext().getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (null != info.activities && info.activities.length > 0) {
                Class<?> clazz;
                for (ActivityInfo ai : info.activities) {
                    clazz = Class.forName(ai.name);
                    if (Activity.class.isAssignableFrom(clazz)) {
                        result.add((Class<? extends Activity>) clazz);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 获取当前应用包名
     *
     * @param context 上下文
     * @return 包名
     */
    public static String packageName(Context context) {
        try {
            PackageManager packageManager = context.getPackageManager();
            PackageInfo packageInfo = packageManager.getPackageInfo(context.getPackageName(), 0);
            return packageInfo.packageName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
