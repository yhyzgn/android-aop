package com.yhy.powerexam.aop.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * author : 颜洪毅
 * e-mail : yhyzgn@gmail.com
 * time   : 2019-04-05 18:53
 * version: 1.0.0
 * desc   : 开启Aop功能，并配置 Aop相关的 packageName
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Documented
public @interface Aop {

    /**
     * packageName
     * <p>
     * 配置此项能提高Aop效率
     *
     * @return packageName
     */
    String value() default "";
}
