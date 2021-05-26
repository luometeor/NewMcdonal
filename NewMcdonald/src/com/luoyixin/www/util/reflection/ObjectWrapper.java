package com.luoyixin.www.util.reflection;


import java.lang.reflect.Method;
import java.util.Map;

/**
 * 包装object，封装了set方法
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.util.reflection
 * @ClassName: ObjectWrapper
 * @create 2021/5/9-16:21
 * @Version: 1.0
 */
public class ObjectWrapper {
    /**
     * 待封装的object
     */
    private Object realObject;

    private ClassWrapper classWrapper;

    public ObjectWrapper(Object realObject) {
        this.realObject = realObject;
        this.classWrapper = new ClassWrapper(realObject.getClass());
    }

    /**
     * set方法
     * @param property 设置的属性
     * @param value 值
     * @throws Exception
     */
    public void setValue(String property,Object value) throws Exception {
        Method setterMethod = classWrapper.getSetterMethod(property);
        setterMethod.invoke(realObject,value);
    }
    /**
     * 获取封装了 属性名与type的属性
     * @return
     */
    public Map<String, Class<?>> getPropertiesMap() {
        return classWrapper.getPropertiesMap();
    }

}
