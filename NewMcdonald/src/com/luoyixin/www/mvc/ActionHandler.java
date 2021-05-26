package com.luoyixin.www.mvc;

import java.lang.reflect.Method;

/**
 * 封装了某个对象以及某个对象的方法
 * 给HandlerInvoker调用
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.mvc
 * @ClassName: ActionHandler
 * @create 2021/5/15-14:27
 * @Version: 1.0
 */
public class ActionHandler {
    /**
     * 要执行方法的对象
     */
    private Object realObject;
    /**
     * 要执行的方法
     */
    private Method method;

    public ActionHandler() {
    }

    public ActionHandler(Object realObject, Method method) {
        this.realObject = realObject;
        this.method = method;
    }

    public Object getRealObject() {
        return realObject;
    }

    public void setRealObject(Object realObject) {
        this.realObject = realObject;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    @Override
    public String toString() {
        return "ActionHandler{" +
                "realObject=" + realObject +
                ", method=" + method +
                '}';
    }
}
