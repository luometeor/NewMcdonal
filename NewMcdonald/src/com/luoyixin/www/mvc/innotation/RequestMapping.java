package com.luoyixin.www.mvc.innotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *   前端发送的请求
 *   有类和方法的注解   根据  /类注解/方法注解 访问
 *   没有类注解        根据  /类名/方法注解访问
 *   没有类，方法注解   根据  /类名/方法名方法
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.mvc.innotation
 * @ClassName: RequestMapping
 * @create 2021/5/15-13:43
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD,ElementType.TYPE})
public @interface RequestMapping {
    String value();
}
