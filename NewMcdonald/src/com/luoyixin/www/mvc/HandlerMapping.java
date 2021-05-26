package com.luoyixin.www.mvc;

import com.luoyixin.www.ioc.annotation.Controller;
import com.luoyixin.www.ioc.ioc.AnnotationConfigApplicationContext;
import com.luoyixin.www.mvc.innotation.RequestMapping;
import com.luoyixin.www.mvc.util.PackageUtils;
import com.luoyixin.www.orm.exception.NormalException;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 给后续方法执行准备
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.mvc
 * @ClassName: HandlerMapping
 * @create 2021/5/15-13:28
 * @Version: 1.0
 */
public class HandlerMapping {
    /**
     * controller路径
     */
    private String controllerPath;
    /**
     * key-> 前端请求的url地址
     * value-> 封装了待执行的对象以及方法
     */
    private Map<String,ActionHandler> mappingMap = new HashMap<>();
    /**
     * ioc容器
     */
    private AnnotationConfigApplicationContext annotationConfigApplicationContext;

    public HandlerMapping(String controllerPath,AnnotationConfigApplicationContext annotationConfigApplicationContext) {
        this.controllerPath = controllerPath;
        this.annotationConfigApplicationContext = annotationConfigApplicationContext;

        Set<String> classSet = PackageUtils.getClassNameByPackage(controllerPath);
        classSet.forEach(className -> {
            Class<?> clazz = null;

            try {
                clazz = Class.forName(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            //获取Controller对象
            Object object = annotationConfigApplicationContext.getBean(lowerFirst(clazz.getSimpleName()));

            String classPath = "";
            //有Controller注解
            if(clazz.isAnnotationPresent(Controller.class)) {
                //有RequestMapping注解
                if (clazz.isAnnotationPresent(RequestMapping.class)) {
                    classPath = clazz.getAnnotation(RequestMapping.class).value();
                } else {
                    classPath = "/" + clazz.getSimpleName();
                }
            }
            Method[] methods = clazz.getDeclaredMethods();
            for (Method method : methods) {
                String methodPath = "";
                if(method.isAnnotationPresent(RequestMapping.class)) {
                    methodPath = method.getAnnotation(RequestMapping.class).value();
                } else {
                    methodPath = "/" + method.getName();
                }
                mappingMap.put(classPath + methodPath,new ActionHandler(object,method));
            }
        });

    }

    /**
     * 获取ActionHandler，包含前端要执行的某个对象的某个方法《包括对象和方法》
     * @param request 获取url地址
     * @return 返回ActionHandler
     */
    public ActionHandler getActionHandler(HttpServletRequest request) {
        String requestUrl =  request.getServletPath() +  request.getPathInfo();
        if(!mappingMap.containsKey(requestUrl)) {
            throw new NormalException("找不到url：" + requestUrl);
        }
        return mappingMap.get(requestUrl);
    }


    /**
     * 把第一个字母小写
     * @param simpleName
     * @return
     */
    private String lowerFirst(String simpleName) {
        if(simpleName != null) {
            return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1);
        }
        return null;
    }
}
