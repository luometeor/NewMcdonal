package com.luoyixin.www.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xin
 * @ProjectName: TIliTili
 * @Package: com.luoyixin.annotation
 * @ClassName: Entity
 * @create 2021/4/13-15:17
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Entity {
    String tableName() default "";
}
