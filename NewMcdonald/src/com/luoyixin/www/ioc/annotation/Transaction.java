package com.luoyixin.www.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 开启事务，需要手动提交，只能给mapper对象设置噢
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.ioc.annotation
 * @ClassName: Transaction
 * @create 2021/5/10-16:30
 * @Version: 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Transaction {
    String value() default "";
}
