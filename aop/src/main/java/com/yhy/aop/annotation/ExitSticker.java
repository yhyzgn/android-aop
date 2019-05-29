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
 * time   : 2019-03-25 11:11
 * version: 1.0.0
 * desc   : 返回退出应用提示
 *          目标类或其父类需要重写onBackPressed()方法，否则注解可能无效
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Documented
public @interface ExitSticker {

    /**
     * 连续返回的有效时间间隔，默认3s
     *
     * @return 有效时间间隔
     */
    long value() default 3000;
}
