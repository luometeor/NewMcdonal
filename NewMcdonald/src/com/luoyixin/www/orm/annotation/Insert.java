package com.luoyixin.www.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xin
 * @ProjectName: TIliTili
 * @Package: com.luoyixin.annotation
 * @ClassName: Insert
 * @create 2021/4/13-18:49
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Insert {
    String value() default "";
}
