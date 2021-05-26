package com.luoyixin.www.util;

import com.luoyixin.www.logger.JdkLogger;
import com.luoyixin.www.util.reflection.ObjectWrapper;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.util
 * @ClassName: WebUtils
 * @create 2021/5/5-22:37
 * @Version: 1.0
 */
public class WebUtils {
    private static Logger logger = JdkLogger.getLogger();
    /**
     * 把map里的值赋值到 bean中
     * @param value map
     * @param bean 待赋值的对象
     * @param <T>
     * @return 返回传进来的对象(注入属性了)
     * @throws Exception
     */
    public static <T> T copyParamToBean(Map value, T bean) {
        beanUtils(value,bean);
        return bean;
    }

    /**
     * 获取所有参数封装到map中
     * key->name value:值
     * @param req
     * @return 返回map
     */
    public static Map<String,String> getParams(HttpServletRequest req) {
        HashMap<String, String> map = new HashMap<>(100);
        //获取所有参数
        Enumeration<String> paramNames=req.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            String value = req.getParameter(name);
            map.put(name,value);
        }
        return map;
    }

    /**
     * String转成int
     * @param str 要转的string类型
     * @param defaultNumber 默认返回值
     * @return 返回强转的类型，否则返回默认值
     */
    public static Integer parseInt(String str,int defaultNumber) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            logger.warning(String.format("用户输入的{%s}无法转为int型",str));
        }
        return defaultNumber;
    }


    /**
     *  注入值
     * @param values 封装了对象信息的map，key->属性名，value->值
     * @param bean 要注入的对象
     * @param <T>
     * @throws Exception
     */
    private static <T>void beanUtils(Map values,T bean) {
        ObjectWrapper wrapper = new ObjectWrapper(bean);
        //获取属性的名字于 class对象
        Map<String, Class<?>> propertiesMap = wrapper.getPropertiesMap();
        values.forEach((key,value) -> {
            if(propertiesMap.containsKey(key)) {
                Class<?> fieldClass = propertiesMap.get(key);
                //目前值查String类型，int类型，布尔类型，BigDecimal类型
                try {
                    if (fieldClass.equals(String.class)) {
                        wrapper.setValue((String) key, value);
                    } else if (fieldClass.equals(Integer.class) || fieldClass.equals(int.class)) {
                        Integer result = parseInt((String) value, 0);
                        wrapper.setValue((String) key, result);
                    } else if (fieldClass.equals(Boolean.class) || fieldClass.equals(boolean.class)) {
                        Boolean result = Boolean.valueOf((String)value);
                        wrapper.setValue((String) key, result);
                    } else if (fieldClass.equals(BigDecimal.class)) {
                        BigDecimal result =new BigDecimal((String)value);
                        wrapper.setValue((String) key, result);
                    }
                } catch (Exception e) {
                    logger.warning(String.format("对象属性无法注入，属性名{%s}，属性值为{%s}",key,value.toString()));
                }
            }
        });

    }



}
