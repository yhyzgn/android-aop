package com.yhy.aop.aspect;

import android.view.View;

import com.yhy.aop.AOP;
import com.yhy.aop.utils.Utils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-09-07 23:03
 * version: 1.0.0
 * desc   : 点击抖动处理器
 */
@Aspect
public class ClickAspect {

    /**
     * 普通的点击事件
     */
    @Pointcut("execution(* android.view.View.OnClickListener.onClick(..))")
    public void click() {
    }

    /**
     * Lambda点击事件
     */
    @Pointcut("execution(* *..lambda*(..))")
    public void clickLambda() {
    }

    /**
     * ButterKnife点击事件
     */
    @Pointcut("execution(* butterknife.internal.DebouncingOnClickListener.doClick(..))")
    public void butterKnife() {
    }

    /**
     * 切面处理
     *
     * @param point 切点
     */
    @Around("click() || clickLambda() || butterKnife()")
    public void handle(ProceedingJoinPoint point) {
        try {
            if (!Utils.clickResolverEnabled() || Utils.clickIgnored(point)) {
                // 未开启点击处理 或者 被忽略的按钮点击
                point.proceed();
                return;
            }

            Object[] args = point.getArgs();
            if (null != args && args.length > 0) {
                View view = null;
                for (Object arg : args) {
                    if (arg instanceof View) {
                        view = (View) arg;
                        break;
                    }
                }

                if (null != view && !Utils.isTooFastClick(view)) {
                    point.proceed();
                    return;
                }
            }
        } catch (Throwable ignored) {
        }
        AOP.logger().log("咦~你这也太快了吧...");
    }
}
