package com.luoyixin.www.mvc.innotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 写法： user.  item.  作为前端传数据的前缀
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.mvc.innotation
 * @ClassName: Entity
 * @create 2021/5/15-15:37
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Entity {
    String value();
}
