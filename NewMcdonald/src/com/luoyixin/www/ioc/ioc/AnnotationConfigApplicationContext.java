package com.luoyixin.www.ioc.ioc;

import com.luoyixin.www.exception.Constant;
import com.luoyixin.www.ioc.annotation.Autowired;
import com.luoyixin.www.ioc.annotation.Controller;
import com.luoyixin.www.ioc.annotation.Service;
import com.luoyixin.www.ioc.manager.ServiceProxy;
import com.luoyixin.www.ioc.manager.SpringTransaction;
import com.luoyixin.www.ioc.util.PackageUtils;
import com.luoyixin.www.logger.JdkLogger;
import com.luoyixin.www.orm.config.Config;
import com.luoyixin.www.orm.config.KnownMapper;
import com.luoyixin.www.orm.exception.NormalException;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.ioc
 * @ClassName: AnnotationConfigApplicationContext
 * @create 2021/4/30-8:12
 * @Version: 1.0
 */
public class AnnotationConfigApplicationContext {
    private Logger logger = JdkLogger.getLogger();
    /**
     * orm的全局配置
     */
    private Config config;
    /**
     * 实现类先注入，在往ioc塞代理类，在注入实现类
     */
    private static Map<String, Object> ioc = new HashMap<String, Object>();
    /**
     * 存储beanDefinition
     */
    private static Set<BeanDefinition> beanDefinitions = new HashSet<>();
    /**
     * 从orm中找到的mapper对象
     */
    private KnownMapper knownMapper = new KnownMapper();

    private SpringTransaction springTransaction;


    public AnnotationConfigApplicationContext(Config config, String...paths) throws Exception {
        this.config =config;
        springTransaction = SpringTransaction.getInstance(config,false);
        //得到service,controller对象的 类名与对应class对象信息
        for (int i = 0; i < paths.length; i++) {
            beanDefinitions = findBeanDefinitions(paths[i]);
        }
        //得到mapper代理对象塞进ioc
        createMapper();
        //造dao注入service实现类，把service代理对象塞进ioc，
        createObject(AnnotationConfigApplicationContext.beanDefinitions);
        // 注入service代理对象注入controller
        autowiredObject(AnnotationConfigApplicationContext.beanDefinitions);
    }

    /**
     * 查找orm已知的mapper塞进ioc
     */
    private void createMapper() {
        Set<KnownMapper.MapperStatement> mapperStatements = knownMapper.getMapperStatements();
        mapperStatements.forEach(mapperStatement -> {
            //获取含 com.luoyixin.www.xxx的类名
            String mapperName = mapperStatement.getMapperName();
            // 获取mapper对象
            Object mapper =springTransaction.getMapper(mapperStatement.getMapperClass());
            //得到 不含 com.luoyixin.www的类名
            String beanRealName = getBeanRealName(mapperName);
            if(!ioc.containsKey(beanRealName)) {
                ioc.put(beanRealName,mapper);
            }
        });

    }


    /**
     * 造service，controller对象放进ioc容器
     * @param beanDefinitions
     * @throws Exception
     */
    private void createObject(Set<BeanDefinition> beanDefinitions) throws Exception {

        for (BeanDefinition beanDefinition : beanDefinitions) {
            String beanName = beanDefinition.getBeanName();
            if(!ioc.containsKey(beanName)) {

                Object instance = beanDefinition.getBeanClass().newInstance();

                String simpleName = instance.getClass().getSimpleName();
                if(simpleName.endsWith(Constant.IMPL)) {
                    Field[] fields = instance.getClass().getDeclaredFields();
                    for (Field field : fields) {
                        //注入dao对象
                        if(field.getAnnotation(Autowired.class) != null) {
                           //field.getType().getName()->com.luoyixin.www.dao.StoreItemDao
                            String beanRealName = getBeanRealName(field.getType().getName());
                            field.setAccessible(true);
                            Object bean = getBean(beanRealName);
                            //注意不要搞反了,第一个参数是待设置的对象，第二个是 要注入的对象
                            /*代理对象注入了！*/
                            field.set(instance, bean);
                            logger.info(String.format("对象{%s}已经注入到{%s}",instance.toString(),bean.toString()));
                        }
                    }
                    //产生代理对象
                    ServiceProxy serviceProxy = new ServiceProxy(instance);

                    instance = serviceProxy.getInstance();
                }
                ioc.put(beanName,instance);
            }
        }
    }

    /**
     * 自动填充属性
     * @param beanDefinitions
     * @throws IllegalAccessException
     */
    private void autowiredObject(Set<BeanDefinition> beanDefinitions) throws IllegalAccessException {
        for (BeanDefinition beanDefinition : beanDefinitions) {
            Class<?> beanClass = beanDefinition.getBeanClass();
            if(beanClass.isAnnotationPresent(Controller.class)) {
                Field[] fields = beanClass.getDeclaredFields();
                for (Field field : fields) {
                    Autowired autowired = field.getAnnotation(Autowired.class);
                    if (autowired != null) {
                        //field.getType().getName()->com.luoyixin.www.dao.UserRoleDao
                        String beanRealName = getBeanRealName(field.getType().getName());
                        field.setAccessible(true);
                        //比如：获取到 Service的实例
                        Object object = getBean(beanDefinition.getBeanName());
                        Object bean = getBean(beanRealName);
                        //注意不要搞反了,第一个参数是待设置的对象，第二个是 要注入的对象
                        // 注入serviceImpl的代理对象
                        field.set(object, bean);
                        logger.info(String.format("对象{%s}已经注入到{%s}",object.toString(),bean.toString()));
                    }
                }
            }
        }
    }

    /**
     *
     * @param beanName 类名小写！
     * @return
     */
    public <E> E getBean(String beanName) {
        if(beanName != null) {
            return (E) ioc.get(beanName);
        }
        return null;
    }


    /**
     * 获取 bean对象的 类名与class对象封装成的beanDefinition
     * @param path
     * @return
     */
    private Set<BeanDefinition> findBeanDefinitions(String path) throws Exception {
        Set<String> classNames = PackageUtils.getClassNameByPackage(path);
        for (String className : classNames) {
            try {
                Class<?> aClass = Class.forName(className);
                String beanName = null;
                if (aClass.isAnnotationPresent(Service.class)) {
                    Service service = aClass.getAnnotation(Service.class);
                    //找到service配置的 实现类路径
                    String value = service.value();
                    aClass = Class.forName(value);
                    beanName = getBeanRealName(className);
                } else if(aClass.isAnnotationPresent(Controller.class)) {
                   beanName = getBeanRealName(className);
                }
                beanDefinitions.add(new BeanDefinition(beanName,aClass));
            } catch (ClassNotFoundException e) {
                logger.severe(String.format("ioc无法找到{%s}的class对象",className));
                throw new NormalException(Constant.ClASS_NO_FIND);
            }
        }
        return beanDefinitions;
    }


    /**
     * 第一个字母小写的类名
     * @param className 一开始的className
     * @return
     */
    private String getBeanRealName(String className) {
        String realClassName = className.substring(className.lastIndexOf('.') + 1);
        return realClassName.substring(0,1).toLowerCase() + realClassName.substring(1);
    }


}
