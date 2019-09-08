package com.yhy.aop.handler;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.yhy.aop.AOP;
import com.yhy.aop.annotation.MainBackResolver;
import com.yhy.aop.callback.OnBackCallback;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-09-08 1:03
 * version: 1.0.0
 * desc   : 防退出主页处理器
 */
@Aspect
public class BackHandler {

    private volatile long last;

    /**
     * *Activity必须重写onBackPressed()方法
     */
    @Pointcut("execution(* *..*Activity+.onBackPressed())")
    public void back() {
    }

    /**
     * 切面处理
     *
     * @param point 切点
     */
    @Around("back()")
    public void handle(ProceedingJoinPoint point) {
        Class<? extends Activity> main = AOP.main();
        if (null != main && point.getTarget().getClass() == AOP.main()) {
            // 切点切到主页
            MainBackResolver resolver = main.getAnnotation(MainBackResolver.class);
            if (null != resolver) {
                long now = System.currentTimeMillis();
                if (now - last <= resolver.interval()) {
                    try {
                        point.proceed();
                        return;
                    } catch (Throwable ignored) {
                    }
                }
                // 不在间隔之内，不允许退出，提示再按一次退出
                Class<? extends OnBackCallback> callback = resolver.callback();
                OnBackCallback caller = null;
                if (callback == OnBackCallback.class) {
                    // 默认值，用动态代理
                    caller = proxyCaller();
                } else {
                    // 自定义了回调
                    try {
                        caller = callback.getConstructor().newInstance();
                    } catch (Exception e) {
                        // 还是得用动态代理
                        caller = proxyCaller();
                    }
                }
                caller.callback((Context) point.getTarget(), resolver.value());
                // 保存最新操作时间，退出
                last = now;
                return;
            }
        }
        try {
            point.proceed();
        } catch (Throwable ignored) {
        }
    }

    /**
     * 动态代理获取默认的提示处理器
     *
     * @return 代理对象
     */
    private OnBackCallback proxyCaller() {
        return (OnBackCallback) Proxy.newProxyInstance(OnBackCallback.class.getClassLoader(), new Class<?>[]{OnBackCallback.class}, new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] args) {
                Context context = (Context) args[0];
                String message = (String) args[1];
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
                return null;
            }
        });
    }
}
