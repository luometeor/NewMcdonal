package com.luoyixin.www.orm.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author xin
 * @ProjectName: TIliTili
 * @Package: com.luoyixin.annotation
 * @ClassName: Colum
 * @create 2021/4/13-15:14
 * @Version: 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Column {
    /**
     * 列名
     */
    String columnName();
    /**
     * 是否是主键，匹配 条件查询
     */
    boolean match() default false;

    /**
     * 别名
     */
    String alias();
}
