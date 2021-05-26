package com.luoyixin.www.ioc.manager;

/**
 * @author xin
 * @ProjectName: McDonald
 * @Package: com.luoyixin.www.ioc.manager
 * @ClassName: TransactionStatus
 * @create 2021/5/13-20:23
 * @Version: 1.0
 */
public class TransactionStatus {
    /**
     * 当状态为 等于1时，表示成功，该提交了
     * 当状态 等于0时，正在事务中，别提交
     * 当状态 等于-1时，发生异常，该回滚了
     * 默认执行后自动提交
     */
    private static Integer status = 1;

    private static TransactionStatus instance;

    private TransactionStatus() {
    }

    public static TransactionStatus getInstance() {
        if(instance == null) {
            synchronized (TransactionStatus.class) {
                if(instance == null) {
                    instance = new TransactionStatus();
                }
            }
        }
        return instance;
    }

    public static Integer getStatus() {
        return status;
    }

    public static void setStatus(Integer status) {
        TransactionStatus.status = status;
    }
}
