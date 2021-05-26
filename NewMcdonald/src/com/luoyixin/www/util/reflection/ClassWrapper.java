package com.luoyixin.www.util.reflection;

import com.luoyixin.www.orm.exception.NormalException;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * class对象的解析：封装class的set方法以及属性名
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.util.reflection
 * @ClassName: ClassWrapper
 * @create 2021/5/9-16:17
 * @Version: 1.0
 */
public class ClassWrapper {
    /**
     * key:属性名 value： 对应set方法
     */
    private Map<String, Method> setterMethodMap = new HashMap<>();

    /**
     * 存储属性名和class类型
     * key：属性名
     * class：类型
     */
    private Map<String,Class<?>> propertiesMap = new HashMap<>();

    /**
     * 待解析的类（对象）
     */
    private Class<?> clazz;

    /**
     * 防止类重复加载浪费时间
     */
    private Map<String,ClassWrapper> clazzWrapperMap = new HashMap<>();

    public ClassWrapper(Class<?> aClass) {
        this.clazz = aClass;
        // 在缓冲中没有对象的,进行下述操作
        if(!clazzWrapperMap.containsKey(aClass.getName())) {
            Field[] fields = aClass.getDeclaredFields();
            for (Field field : fields) {
                // 添加对象的属性
                propertiesMap.put(field.getName(),field.getType());
            }
            Method[] methods = aClass.getDeclaredMethods();
            //添加对应的set方法
            for (Method method : methods) {
                String name = method.getName();
                if(name.startsWith("set")) {
                    name = name.substring(3);
                    name = name.substring(0,1).toLowerCase() + name.substring(1);
                    setterMethodMap.put(name,method);
                }
            }
            clazzWrapperMap.put(aClass.getName(),this);
        }
    }


    /**
     * 根据属性名获取对应的set方法
     * @param property
     * @return
     */
    public Method getSetterMethod(String property) {
        ClassWrapper classWrapper = clazzWrapperMap.get(clazz.getName());
        if (!classWrapper.setterMethodMap.containsKey(property)) {
            throw new NormalException(property + "属性没有set方法");
        }
        Method method = classWrapper.setterMethodMap.get(property);
        return method;
    }

    /**
     * 获取封装了 属性名与type的属性
     * @return
     */
    public Map<String, Class<?>> getPropertiesMap() {
        return propertiesMap;
    }

}
