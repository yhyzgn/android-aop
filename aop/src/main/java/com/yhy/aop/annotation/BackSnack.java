package com.yhy.aop.annotation;

import com.yhy.aop.resolver.BackSnackResolver;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-09-08 0:58
 * version: 1.0.0
 * desc   : 主页面防退出提示
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface BackSnack {

    /**
     * 提示信息
     *
     * @return 提示信息
     */
    String value() default "再按一次退出应用";

    /**
     * 防退出时长，默认3s
     *
     * @return 防退出时长
     */
    long interval() default 3000;

    /**
     * 提示处理类
     *
     * @return 提示处理类
     */
    Class<? extends BackSnackResolver> resolver() default BackSnackResolver.class;
}
