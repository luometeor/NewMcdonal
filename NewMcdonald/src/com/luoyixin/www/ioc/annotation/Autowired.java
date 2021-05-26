package com.luoyixin.www.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.Annotation
 * @ClassName: Autowired
 * @create 2021/4/24-17:32
 * @Version: 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Autowired {
    //自动注入
    String value() default "";
}
