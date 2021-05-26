package com.luoyixin.www.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.annotation
 * @ClassName: controller
 * @create 2021/4/30-8:04
 * @Version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Controller {
    String value() default "";
}
