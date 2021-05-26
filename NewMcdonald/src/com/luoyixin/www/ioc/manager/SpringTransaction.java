package com.luoyixin.www.ioc.manager;

import com.luoyixin.www.orm.config.Config;
import com.luoyixin.www.orm.exectuor.SimpleExecutor;


/**
 *
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.ioc.ioc
 * @ClassName: SpringTransaction
 * @create 2021/5/10-16:01
 * @Version: 1.0
 */
public class SpringTransaction extends SimpleExecutor {

    private static SpringTransaction instance = null;

    /**
     * 私有化构造器，单例
     * @param config
     * @param openTransaction
     */
    private SpringTransaction(Config config, Boolean openTransaction) {
        super(config, openTransaction);
    }


    /**
     *  第一次在ioc调用
     * @param config
     * @param openTransaction
     * @return
     */
    public static SpringTransaction getInstance(Config config, Boolean openTransaction) {
        if(instance == null) {
            synchronized (SpringTransaction.class) {
                if(instance == null) {
                    instance =  new SpringTransaction(config,openTransaction);
                }
            }
        }
        return instance;
    }

    /**
     * 给第二次调用
     * @return
     */
    public static SpringTransaction getInstance() {
        return instance;
    }

}
