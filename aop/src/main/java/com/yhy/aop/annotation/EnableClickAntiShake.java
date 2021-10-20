package com.yhy.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-09-07 23:23
 * version: 1.0.0
 * desc   : 开启点击抖动处理器
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
public @interface EnableClickAntiShake {

    /**
     * 防抖动间隔时长，默认1s
     *
     * @return 防抖动间隔时长
     */
    long value() default 1000;
}
