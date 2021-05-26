package com.luoyixin.www.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.Annotation
 * @ClassName: Service
 * @create 2021/4/22-17:39
 * @Version: 1.0
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Service {
    // 需要手动配置 serviceImpl的相对路径
    String value();
}
