package com.luoyixin.www.ioc.manager;

import com.luoyixin.www.ioc.annotation.Transaction;
import com.luoyixin.www.logger.JdkLogger;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.logging.Logger;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.ioc.manager
 * @ClassName: ServiceProxy
 * @create 2021/5/13-20:16
 * @Version: 1.0
 */

public class ServiceProxy implements InvocationHandler {

    private Logger logger = JdkLogger.getLogger();

    private Object target;

    public ServiceProxy(Object target) {
        this.target = target;
    }

    public Object getInstance() {
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),target.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (!method.isAnnotationPresent(Transaction.class)) {
            return method.invoke(target, args);
        }
        SpringTransaction springTransaction = SpringTransaction.getInstance();
        springTransaction.setOpenTransaction(true);
        try {
            Object result = method.invoke(target, args);
            logger.info(String.format("方法{%s},参数{%s}",method.toString(), Arrays.stream(args).toArray().toString()));
            //事务结束了,要提交了
            if(TransactionStatus.getStatus() == 1) {
                 springTransaction.commit();
                //重新设置为自动提交
            } else if(TransactionStatus.getStatus() == -1) {
                springTransaction.rollback();
            }
            return result;
        } catch (Exception e) {
            springTransaction.rollback();
            //重新设置为自动提交
            e.printStackTrace();
        } finally {
            springTransaction.close();
            springTransaction.setOpenTransaction(false);
            //设置为事务不传播
            TransactionStatus.setStatus(1);
        }

        return null;
    }
}




