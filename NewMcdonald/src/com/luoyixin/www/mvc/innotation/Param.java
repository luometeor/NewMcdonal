package com.luoyixin.www.mvc.innotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 给参数的注解
 *
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.mvc.innotation
 * @ClassName: Param
 * @create 2021/5/15-15:08
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Param {
    String value();
}
