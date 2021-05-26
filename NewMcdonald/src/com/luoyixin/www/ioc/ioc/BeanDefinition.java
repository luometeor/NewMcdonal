package com.luoyixin.www.ioc.ioc;

import java.util.Objects;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.ioc
 * @ClassName: BeanDefinition
 * @create 2021/4/30-8:13
 * @Version: 1.0
 */
public class BeanDefinition {
    /**
     * bean的名称
     */

    private String beanName;
    /**
     *  bean的类名
     */
    private Class<?> beanClass;

    public BeanDefinition() {
    }

    public BeanDefinition(String beanName, Class<?> beanClass) {
        this.beanName = beanName;
        this.beanClass = beanClass;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public Class<?> getBeanClass() {
        return beanClass;
    }

    public void setBeanClass(Class<?> beanClass) {
        this.beanClass = beanClass;
    }

    @Override
    public String toString() {
        return "BeanDefinition{" +
                "beanName='" + beanName + '\'' +
                ", beanClass=" + beanClass +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        BeanDefinition that = (BeanDefinition) o;
        return Objects.equals(beanName, that.beanName) && Objects.equals(beanClass, that.beanClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(beanName, beanClass);
    }
}
