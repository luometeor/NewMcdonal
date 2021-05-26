package com.luoyixin.www.orm.utils;

import com.luoyixin.www.orm.annotation.Column;
import com.luoyixin.www.orm.annotation.Entity;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;

/**
 * @author xin
 * @ProjectName: TIliTili
 * @Package: com.luoyixin.utils
 * @ClassName: SqlUtils
 * @create 2021/4/13-15:10
 * @Version: 1.0
 */
public class SqlUtils {
    /**
     * 表示 `
     */
    private static final String DOT = "`";
    /**
     * 表示 空格
     */
    private static final String SPACE = " ";
    /**
     * 表示 等于号 =
     */
    private static final String EQUAL_TO = " = ";
    /**
     * 表示 字符串 id
     */
    private static final String ID = "id";
    /**
     *  自动生成查询的sql，注意：Object要有对应的数据
     * @param clazz 可以传入任意的Class对象
     * @param object 封装了对应数据的对象
     * @return 返回sql
     */
    public static String selectSql(Class<?> clazz,Object object){
        StringBuilder str = new StringBuilder();
        String tableName = getTableName(clazz);
        str.append("select ");
        String match = "";
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Column column = fields[i].getAnnotation(Column.class);
            if(ID.equals(column.alias())) {
                match = column.alias();
            }
            if(fields.length-1 != i) {
                str.append(column.columnName() +SPACE+ column.alias()).append(",");
            } else {
                str.append(column.columnName() + SPACE + column.alias()).append(" from ").append(tableName);
            }
        }
        String value = getValue(match, object);
        if(value != null) {
            str.append(" where ").append(match).append(EQUAL_TO).append(value);
        }
        return str.toString();
    }

    /**
     * 自动生成删除的sql
     * @param clazz 不是表示泛型方法，仅仅是通配符的使用，可以传入任意的Class对象
     * @param object 封装了对应数据的对象
     * @return  返回sql语句
     */
    public static String deleteSql(Class<?> clazz,Object object) {
        StringBuilder str = new StringBuilder();
        String tableName = getTableName(clazz);
        str.append("delete from ").append(tableName).append(" where 1=1");
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Column column = fields[i].getAnnotation(Column.class);
            if (column.match()) {
                    str.append(" and ").append(column.columnName()).append(EQUAL_TO).append(getValue(column.alias(),object));
            }
        }
        return str.toString();
    }

    /**
     *  自动生成插入的sql
     * @param clazz 任意的Class对象
     * @param object 封装了对应数据的对象
     * @return 返回sql语句
     */
    public static String insertSql(Class<?> clazz,Object object) {
        StringBuilder str = new StringBuilder();
        String tableName = getTableName(clazz);
        str.append("insert into ").append(tableName).append("(");
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Column column = fields[i].getAnnotation(Column.class);
            if(column!= null && !ID.equals(column.columnName())) {
                if (fields.length - 1 != i) {
                    str.append(DOT).append(column.columnName()).append(DOT).append(",");
                } else {
                    str.append(DOT).append(column.columnName()).append(DOT).append(") values(");
                }
            }
        }
        for (int i = 0; i < fields.length; i++) {
            Column column = fields[i].getAnnotation(Column.class);
            if(column!= null && !ID.equals(column.columnName())) {
                if (fields.length - 1 != i) {
                    str.append(getValue(column.alias(), object)).append(",");
                } else {
                    str.append(getValue(column.alias(), object)).append(")");
                }
            }
        }
        return str.toString();
    }

    /**
     *   自动生成更新的sql
     * @param clazz 任意的Class对象，主要是对应的实体类的class对象
     * @param object 封装了对应数据的对象
     * @return 返回sql语句
     */
    public static String updateSql(Class<?> clazz,Object object) {
        StringBuilder str = new StringBuilder();
        String tableName = getTableName(clazz);
        str.append("update ").append(tableName).append(" set ");
        String match = "";
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0; i < fields.length-1; i++) {
            Column column = fields[i].getAnnotation(Column.class);
            if(column != null) {
                if (column.match()) {
                    match = column.alias();
                } else {
                    str.append(DOT).append(column.columnName()).append(DOT).append(EQUAL_TO).append(getValue(column.alias(), object)).append(",");
                }
            }
        }
        Column columns = fields[fields.length - 1].getAnnotation(Column.class);
        str.append(DOT).append(columns.columnName()).append(DOT).append(EQUAL_TO).append(getValue(columns.alias(),object))
                .append(" where ").append(match).append(EQUAL_TO).append(getValue(match,object));
        return str.toString();
    }


    /**
     *  得到表名
     * @param clazz  任意的Class对象，主要是对应的实体类的class对象
     * @return 返回注释的表名信息
     */
    private static String getTableName(Class<?> clazz) {
        //判断是否为Table注释类型是方法返回true，否则返回false
        if(clazz.isAnnotationPresent(Entity.class)) {
            Entity table = clazz.getAnnotation(Entity.class);
            if(!"".equals(table.tableName())) {
                return table.tableName();
            }
        }
        return null;
    }

    /**
     * 获取实体类中某个 属性的值
     * @param alias 实体类中的别名，不是表中的名字
     * @param object 对应实体类,不能通过 class对象直接newInstance，因为要有对应的数据
     * @return 返回属性的值，反之为null
     */
    private static String getValue(String alias,Object object) {
        String getMethod = alias.substring(0,1).toUpperCase() + alias.substring(1);
        try {
            Method method = object.getClass().getMethod("get" + getMethod);
            Object invoke = method.invoke(object);
            //如果是 字符串 或者 日期，加上 单引号
            if(invoke instanceof String || invoke instanceof Date) {
                String result = method.invoke(object).toString();
                result = "'"+result+"'";
                return result;
            }
            // 对于 Integer等不用加上 单引号
            if(invoke != null) {
                return invoke.toString();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
