package com.luoyixin.www.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.Annotation
 * @ClassName: Dao
 * @create 2021/4/23-20:30
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Dao {
    String value() default "";
}
