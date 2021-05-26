package com.luoyixin.www.mvc;

import com.luoyixin.www.exception.Constant;
import com.luoyixin.www.logger.JdkLogger;
import com.luoyixin.www.mvc.innotation.Entity;
import com.luoyixin.www.mvc.innotation.Param;
import com.luoyixin.www.util.WebUtils;
import com.luoyixin.www.util.reflection.ObjectWrapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.mvc
 * @ClassName: HandlerInvoker
 * @create 2021/5/15-13:29
 * @Version: 1.0
 */
public class HandlerInvoker {
    private Logger logger = JdkLogger.getLogger();
    /**
     * 调用actionHandler中的 那个对象 的 那个方法
     * 前提： 处理参数
     * @param request 获取参数
     * @param actionHandler 含有对象与方法
     * @return 返回视图
     */
    public Result invokerHandler(HttpServletRequest request, HttpServletResponse response,ActionHandler actionHandler) throws Exception {
        Object controllerObject = actionHandler.getRealObject();
        Method method = actionHandler.getMethod();
        //取出前端的参数 处理 成后台方法上对应的参数，传参
        List<Object> params = handlerParamFromRequestToBackGround(request,response,method);

        Result result = (Result) method.invoke(controllerObject,params.toArray());
        return result;
    }

    /**
     * 给方法传参
     * @param request 要传的参数
     * @param response
     * @param method 方法
     * @return
     * @throws Exception
     */
    private List<Object> handlerParamFromRequestToBackGround(HttpServletRequest request,HttpServletResponse response, Method method) throws Exception {
        List<Object> list = new ArrayList<>();

        Parameter[] parameters = method.getParameters();

        for (Parameter parameter : parameters) {
            Param param = parameter.getAnnotation(Param.class);
            //实体类注解 比如：这样的前缀user.
            Entity entity = parameter.getAnnotation(Entity.class);
            //没有注解
            if(param == null && entity == null) {
                //配置-parameters，不然获取的是 args0,args1......
                String name = parameter.getName();
                String toString = parameter.getType().toString();
                if(toString.contains(Constant.RESPONSE) ) {
                    list.add(response);
                } else if(toString.contains(Constant.REQUEST) ) {
                    list.add(request);
                } else {
                    list.add(castToRealParam(parameter.getType(), request.getParameter(name)));
                }
            } else if(entity == null) {
                //parameter.getType() 获取class java.lang.String
                list.add(castToRealParam(parameter.getType(),request.getParameter(param.value())));
            } else {
                list.add(beanUtils(WebUtils.getParams(request),entity.value(),parameter.getType().newInstance()));
            }
        }
        return list;
    }


    /**
     *  处理常见类型参数
     * 目前仅支持String类型，int类型，布尔类型，BigDecimal类型
     * @param paramType 要转成的目标类型
     * @param paramValue 待转的参数值
     * @return 返回前端的数据值 转成对应类型了
     */
    private Object castToRealParam(Class<?> paramType, String paramValue){
        Object obj = null;
        /*默认可以为null！也可以为 ""*/
        if(paramValue == null || "".equals(paramValue)) {
            return null;
        }
        try {
            if(paramType.equals(String.class)){
                obj = paramValue;
            }else if(paramType.equals(Integer.class) || paramType.equals(int.class)){
                obj = Integer.parseInt(paramValue);
            }else if(paramType.equals(Boolean.class) || paramType.equals(boolean.class)){
                obj = Boolean.parseBoolean(paramValue);
            }else if(paramType.equals(BigDecimal.class)) {
                obj = new BigDecimal(paramValue);
            }
        } catch (NumberFormatException e) {
            logger.warning(String.format("{%s}转换失败,要转换的值为{%s},错误信息：{%s}",paramType.toString(),paramValue,e.getMessage()));
        }
        return obj;
    }


    /**
     * 处理对象类型参数
     * @param values 方法参数的class对象 如user等等
     * @param prefix 前缀 比如：user.
     * @param bean 对应的bean对象
     * @param <T>
     * @throws Exception
     */
    private  <T>T beanUtils(Map values, String prefix, T bean)  {
        ObjectWrapper wrapper = new ObjectWrapper(bean);
        Map<String, Class<?>> propertiesMap = wrapper.getPropertiesMap();
       values.forEach((key,value)->{
           if(key.toString().startsWith(prefix)) {
               //获取属性名,去掉前缀 user.
               String fieldName = key.toString().substring(prefix.length());
               Object realValue = castToRealParam(propertiesMap.get(fieldName), (String) value);
               try {
                   wrapper.setValue(fieldName,realValue);
               } catch (Exception e) {
                  logger.warning(String.format("对象属性无法注入，属性名{%s}，属性值为{%s}",fieldName,realValue.toString()));
               }
           }
       });

        return bean;
    }
}
