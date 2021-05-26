package com.luoyixin.www.orm.mapper;

import com.luoyixin.www.orm.config.MapperCore;
import com.luoyixin.www.orm.exectuor.SimpleExecutor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.mapper
 * @ClassName: ProxyFactory
 * @create 2021/4/22-21:16
 * @Version: 1.0
 */
public class ProxyFactory<T> implements InvocationHandler {

    private Class<T> mapper;

    /**
     * 内聚在类中，交给调用者去new
     */
    private SimpleExecutor executor;

    public ProxyFactory(Class<T> mapper, SimpleExecutor executor) {
        this.mapper = mapper;
        this.executor = executor;
    }

    @SuppressWarnings("unchecked")
    public T newInstance() {
        return (T) Proxy.newProxyInstance(mapper.getClassLoader(), new Class[]{mapper}, this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //object方法直接代理
        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        } else {
            //获取执行的类型
            Integer executeType = executor.mapperCore.getExecuteType(method);
            Object result = null;
            if (executeType.equals(MapperCore.SELECT_TYPE)) {
                Class<?> returnType = executor.mapperCore.getReturnType(method);
                //暂时只使用与查询的特殊值为 int型
                if ((int.class.equals(returnType) || Integer.class.equals(returnType))  ) {
                    result = executor.getValue(method, args);
                } else {
                    List<Object> select = executor.select(method, args);
                    //不是list类型，获取第一个就行
                    if (!executor.mapperCore.getList(method)) {
                        if( select != null && select.size() != 0 ) {
                            result = select.get(0);
                        }
                    } else {
                        result = select;
                    }
                }
            } else {
                result = executor.update(method, args);
            }
            return result;
        }
    }

}
