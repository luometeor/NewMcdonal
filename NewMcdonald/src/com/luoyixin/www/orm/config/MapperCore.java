package com.luoyixin.www.orm.config;

import com.luoyixin.www.orm.annotation.*;
import com.luoyixin.www.orm.utils.PackageUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.config
 * @ClassName: MapperCore
 * @create 2021/4/30-11:30
 * @Version: 1.0
 */
public class MapperCore {
    /**
     * 执行类型
     */
    public static final Integer SELECT_TYPE = 1;
    public static final Integer UPDATE_TYPE = 2;
    public static final Integer DELETE_TYPE = 3;
    public static final Integer INSERT_TYPE = 4;
    /**
     * 配置文件
     */
    private Config config;
    /**
     * 传给ioc
     */
    private KnownMapper knownMapper = new KnownMapper() ;
    /**
     * key:类名+方法
     * 缓存对象
     * 可能会又多个用户同时进来
     */
    public Map<String,MethodDetails> map = new ConcurrentHashMap<>();

    public MapperCore(Config config) throws ClassNotFoundException {
        this.config = config;
        load(config.getDaoSource());

    }



    /**
     * 根据有dao注解的加载，遍历方法
     * @param daoSource
     * @throws ClassNotFoundException
     */
    private void load(String daoSource) throws ClassNotFoundException {
        Set<String> classNames = PackageUtils.getClassNameByPackage(daoSource);
        for (String className : classNames) {
            //比如：className :com.luoyixin.www.dao.StudentDao
            Class<?> clazz = Class.forName(className);
            Annotation[] annotations = clazz.getAnnotations();
            for (Annotation annotation : annotations) {
                // 有 @Dao注解
                if(annotation.annotationType().equals(Dao.class)) {
                    //给knownmapper赋值，给ioc赋值
                    KnownMapper.MapperStatement mapperStatement = knownMapper.new MapperStatement(className, clazz);
                    knownMapper.setMapperStatement(mapperStatement);

                    Method[] methods = clazz.getDeclaredMethods();
                    for (Method method : methods) {
                        MethodDetails methodDetail = annotationHandler(method);
                        returnTypeHandler(methodDetail,method);
                        map.put(generateStatementId(method),methodDetail);
                    }
                }
            }
        }
    }

    /**
     *  产生唯一标识 类名+方法
     * @param method
     * @return
     */
    private String generateStatementId(Method method) {
        return method.getDeclaringClass().getName() + method.getName();
    }

    /**
     * 根据方法获取返回值类型,决定是不是返回list
     * @param method
     */
    private void returnTypeHandler(MethodDetails methodDetail,Method method) {
        Type genericReturnType = method.getGenericReturnType();
        //是否是含有泛型
        if(genericReturnType instanceof ParameterizedType) {
            methodDetail.setReturnType((Class<?>) ((ParameterizedType) genericReturnType).getActualTypeArguments()[0]);
            methodDetail.setList(true);
        } else {
            methodDetail.setReturnType((Class<?>) genericReturnType);
            methodDetail.setList(false);
        }
    }

    /**
     * 处理注解，获取sql语句与执行类型
     * @param method
     */
    private MethodDetails annotationHandler(Method method) {
        MethodDetails methodDetail = new MethodDetails();
        Annotation[] annotations = method.getAnnotations();
        for (Annotation annotation : annotations) {
            if (annotation.annotationType().equals(Select.class)) {
                Select select = (Select) annotation;
                methodDetail.setExecuteType(SELECT_TYPE);
                String value = select.value();
                if(value != null ) {
                    methodDetail.setSql(value);
                }
            } else if (annotation.annotationType().equals(Update.class)) {
                Update update = (Update) annotation;
                methodDetail.setExecuteType(UPDATE_TYPE);
                String value = update.value();
                if(value != null) {
                    methodDetail.setSql(value);
                }
            } else if(annotation.annotationType().equals(Delete.class)) {
                Delete delete = (Delete) annotation;
                methodDetail.setExecuteType(DELETE_TYPE);
                String value = delete.value();
                if(value != null) {
                    methodDetail.setSql(value);
                }
            } else {
                Insert insert = (Insert) annotation;
                methodDetail.setExecuteType(INSERT_TYPE);
                String value = insert.value();
                if(value != null) {
                    methodDetail.setSql(value);
                }
            }
        }
        return methodDetail;
    }


    public String getSql(Method method) {
        return map.get(generateStatementId(method)).getSql();
    }


    public Integer getExecuteType(Method method) {
        return map.get(generateStatementId(method)).getExecuteType();
    }


    public Class<?> getReturnType(Method method) {
        return map.get(generateStatementId(method)).getReturnType();
    }


    public Boolean getList(Method method) {
        return map.get(generateStatementId(method)).getList();
    }
    /**
     * 内部类，用于存储方法对应信息
     */
    class MethodDetails{
        /**
         * sql语句
         */
        private String sql;
        /**
         * 执行类型 1->select 2-> update 3->delete 4->insert
         */
        private Integer executeType;
        /**
         * 返回类型
         */
        private Class<?> returnType;

        /**
         * 是否是返回类型list
         */
        private Boolean isList;

        public String getSql() {
            return sql;
        }

        public void setSql(String sql) {
            this.sql = sql;
        }

        public Integer getExecuteType() {
            return executeType;
        }

        public void setExecuteType(Integer executeType) {
            this.executeType = executeType;
        }

        public Class<?> getReturnType() {
            return returnType;
        }

        public void setReturnType(Class<?> returnType) {
            this.returnType = returnType;
        }

        public Boolean getList() {
            return isList;
        }

        public void setList(Boolean list) {
            isList = list;
        }

    }
}
